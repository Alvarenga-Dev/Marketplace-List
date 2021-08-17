package com.alvarengadev.marketplacelist.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.databinding.FragmentCartBinding
import com.alvarengadev.marketplacelist.ui.fragments.cart.adapter.CartAdapter
import com.alvarengadev.marketplacelist.ui.fragments.cart.adapter.ObserverListEmpty
import com.alvarengadev.marketplacelist.ui.fragments.cart.adapter.OnClickItemListener
import com.alvarengadev.marketplacelist.utils.Parses
import com.alvarengadev.marketplacelist.utils.extensions.goneWithoutAnimation
import com.alvarengadev.marketplacelist.utils.extensions.visibilityWithoutAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), ObserverListEmpty {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModels()

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
        cartViewModel.getListItems()
        listenToRegistrationViewModelEvents()
        initComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initComponents() = binding.apply {
        footerCart
            .setTextButton(getString(R.string.footer_button_text))
            .setActionButton {
                findNavController().navigate(R.id.action_cartFragment_to_addFragment)
            }

        ibSettings.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_settings_graph)
        }
    }

    private fun listenToRegistrationViewModelEvents() = binding.apply {
        with(cartViewModel) {
            registrationStateEvent.observeForever { registrationState ->
                if (registrationState is CartViewModel.CartListState.LoadingList) {
                    pbLoadingList.visibilityWithoutAnimation()
                }

                if (registrationState is CartViewModel.CartListState.ListEmpty) {
                    showList(false)
                    footerCart.setCartValue()
                }

                if (registrationState is CartViewModel.CartListState.SuccessList) {
                    val adapterListCart = CartAdapter(registrationState.listItems, parentFragmentManager)
                    adapterListCart.observerListEmpty(this@CartFragment)
                    adapterListCart.setOnClickItemListener(object : OnClickItemListener {
                        override fun setOnClickItemListener(itemId: Int) {
                            val directions = CartFragmentDirections.actionCartFragmentToDetailsFragment(itemId)
                            findNavController().navigate(directions)
                        }
                    })

                    rcyCartItem.adapter = adapterListCart

                    showList(true)
                    footerCart.setCartValue(registrationState.total)
                }
            }
        }
    }


    private fun showList(isShow: Boolean) = binding.apply {
        pbLoadingList.goneWithoutAnimation()
        if (isShow) {
            rcyCartItem.visibilityWithoutAnimation()
            containerListEmpty.goneWithoutAnimation()
        } else {
            rcyCartItem.goneWithoutAnimation()
            containerListEmpty.visibilityWithoutAnimation()
        }
    }

    override fun observer(listItems: ArrayList<Item>) {
        binding.footerCart.apply {
            if (listItems.isNotEmpty()) {
                setCartValue(Parses.parseValueTotal(listItems))
            } else {
                showList(false)
                setCartValue()
            }
        }
    }
}