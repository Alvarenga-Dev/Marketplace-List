package com.alvarengadev.marketplacelist.view.fragments

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.FragmentCartBinding
import com.alvarengadev.marketplacelist.view.bottomsheet.BottomSheetNewsFunctions
import com.alvarengadev.marketplacelist.view.bottomsheet.BottomSheetOnboarding
import com.alvarengadev.marketplacelist.view.bottomsheet.`interface`.ButtonGotItClickListener
import com.alvarengadev.marketplacelist.view.adapter.CartAdapter
import com.alvarengadev.marketplacelist.view.adapter.interfaces.ObserverCartEmpty
import com.alvarengadev.marketplacelist.view.adapter.interfaces.CartOnClickItemListener
import com.alvarengadev.marketplacelist.view.dialog.PermissionsDialog
import com.alvarengadev.marketplacelist.utils.TextFormatter
import com.alvarengadev.marketplacelist.utils.constants.Constants
import com.alvarengadev.marketplacelist.utils.constants.KEY_NEVER_ASK_PERMISSION_NOTIFICATIONS
import com.alvarengadev.marketplacelist.utils.constants.KEY_ONBOARDING_NEWS_FUNCTIONS
import com.alvarengadev.marketplacelist.utils.constants.KEY_ONBOARDING_WELCOME
import com.alvarengadev.marketplacelist.utils.extensions.createSnack
import com.alvarengadev.marketplacelist.utils.extensions.preferences
import com.alvarengadev.marketplacelist.utils.extensions.save
import com.alvarengadev.marketplacelist.utils.extensions.shortToast
import com.alvarengadev.marketplacelist.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), ObserverCartEmpty {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModels()

    private var intentSharedCart: Intent? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            context?.shortToast(R.string.toast_cart_permissions)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setupComponents()
        listenToRegistrationViewModelEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupComponents() = binding.apply {
        footerCart
            .setTextButton(getString(R.string.footer_button_text))
            .setActionButton {
                findNavController().navigate(R.id.action_cartFragment_to_addFragment)
            }

        ibSettings.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_settings_graph)
        }

        ibMore.setOnClickListener {
            val popupMenu = context?.let { context -> PopupMenu(context, it) }
            popupMenu?.menuInflater?.inflate(R.menu.menu_popup_cart, popupMenu.menu)
            popupMenu?.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.sharedListMenu -> {
                        intentSharedCart?.let { intent ->
                            intent.type = TYPE_TEXT
                            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                                startActivity(
                                    Intent.createChooser(
                                        intent,
                                        getString(R.string.intent_title_shared)
                                    )
                                )
                            } else {
                                root.createSnack(R.string.toast_cart_empty)
                            }
                        }
                    }
                    R.id.clearCartMenu -> {
                        intentSharedCart?.removeExtra(Intent.EXTRA_TEXT)
                        cartViewModel.deleteAllItemsFromDatabase()
                    }
                }
                false
            }
            popupMenu?.show()
        }

        if (context?.preferences?.getBoolean(KEY_ONBOARDING_WELCOME, false) == false) {
            val bottomSheet = BottomSheetOnboarding()
            bottomSheet.setButtonGotItClickListener(object : ButtonGotItClickListener {
                override fun onClick() {
                    context?.preferences?.save(KEY_ONBOARDING_WELCOME, true)
                    bottomSheet.dismiss()
                }
            }).show(childFragmentManager, Constants.BOTTOM_SHEET_WELCOME)
        }
    }

    private fun listenToRegistrationViewModelEvents() = binding.apply {
        with(cartViewModel) {
            registrationStateEvent.observe(viewLifecycleOwner) { registrationState ->
                if (registrationState is CartViewModel.CartListState.LoadingList) {
                    binding.cartViewFlipper.displayedChild = VIEW_FLIPPER_LOADING
                }

                if (registrationState is CartViewModel.CartListState.ListEmpty) {
                    binding.cartViewFlipper.displayedChild = VIEW_FLIPPER_EMPTY
                    footerCart.setCartValue()
                }

                if (registrationState is CartViewModel.CartListState.SuccessList) {
                    val adapterListCart = CartAdapter()
                    adapterListCart.apply {
                        submitList(registrationState.listItemModels)
                        setSupportFragmentManager(parentFragmentManager)
                        observerListEmpty(this@CartFragment)
                        setOnClickItemListener(object : CartOnClickItemListener {
                            override fun setOnClickItemListener(itemId: Int) {
                                val directions =
                                    CartFragmentDirections.actionCartFragmentToDetailsFragment(
                                        itemId
                                    )

                                findNavController()
                                    .navigate(directions)
                            }
                        })
                    }

                    rcyCartItem.adapter = adapterListCart
                    binding.cartViewFlipper.displayedChild = VIEW_FLIPPER_LIST
                    viewBottomSheetNewsFunctions()
                    footerCart.setCartValue(registrationState.total)
                    intentSharedCart?.putExtra(
                        Intent.EXTRA_TEXT,
                        context?.let {
                            TextFormatter.messageSharedList(
                                it,
                                registrationState.listItemModels
                            )
                        }
                    )
                }

                if (registrationState is CartViewModel.CartListState.Result) {
                    if (registrationState.isSuccessful) {
                        cartViewModel.getAllItemsFromDatabase()
                    } else {
                        root.createSnack(R.string.alert_list_empty)
                    }
                    cartViewModel.resetClearCart()
                }

                if (registrationState is CartViewModel.CartListState.BottomSheet) {
                    showBottomSheetNewsFunctions()
                    cartViewModel.resetClearCart()
                }
            }
        }
    }

    private fun setupEvents() {
        cartViewModel.getAllItemsFromDatabase()
        intentSharedCart = Intent(Intent.ACTION_SEND)

        // remove 'if' and move conditional for viewModel
        val isNeverAcceptNotifications = context?.preferences?.getBoolean(KEY_NEVER_ASK_PERMISSION_NOTIFICATIONS, false)
        if (isNeverAcceptNotifications == false) {
            showAskNotificationPermission()
        }
    }

    // remove 'if' and move conditional for viewModel
    private fun showBottomSheetNewsFunctions() {
        val isViewedOnboardingWelcome = context?.preferences?.getBoolean(KEY_ONBOARDING_WELCOME, false)
        val isViewedOnboardingNewFunctions = context?.preferences?.getBoolean(KEY_ONBOARDING_NEWS_FUNCTIONS, false)

        if (isViewedOnboardingNewFunctions == false && isViewedOnboardingWelcome == true) {
            val bottomSheet = BottomSheetNewsFunctions()
            bottomSheet.show(childFragmentManager, Constants.BOTTOM_SHEET_NEWS_FUNCTIONS)
        }
    }

    private fun showAskNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                val permissionsDialog = PermissionsDialog(
                    acceptNotification = {
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    },
                    recuseNotification = {
                        context?.preferences?.save(KEY_NEVER_ASK_PERMISSION_NOTIFICATIONS, true)
                    }
                )
                permissionsDialog.show(parentFragmentManager, "")
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun observer(total: Double, isEmpty: Boolean) {
        binding.footerCart.apply {
            if (total > 0.0) {
                setCartValue(total)
            }

            if (isEmpty) {
                intentSharedCart?.removeExtra(Intent.EXTRA_TEXT)
                binding.cartViewFlipper.displayedChild = VIEW_FLIPPER_EMPTY
                setCartValue()
            }
        }
    }

    private companion object {
        const val VIEW_FLIPPER_EMPTY = 0
        const val VIEW_FLIPPER_LIST = 1
        const val VIEW_FLIPPER_LOADING = 2

        const val TYPE_TEXT = "text/html"
    }
}

