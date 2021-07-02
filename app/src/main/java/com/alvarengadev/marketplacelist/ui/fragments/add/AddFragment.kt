package com.alvarengadev.marketplacelist.ui.fragments.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.FragmentAddBinding
import com.alvarengadev.marketplacelist.utils.Parses
import com.alvarengadev.marketplacelist.utils.MoneyTextWatcher
import com.alvarengadev.marketplacelist.utils.extensions.createSnack
import com.alvarengadev.marketplacelist.utils.extensions.dismiss
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val addViewModel: AddViewModel by viewModels()
    private var quantity: Int = 1
    private var value: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        listenToRegistrationViewModelEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getExitTransition(): Any? {
        clearFragment()
        return super.getExitTransition()
    }

    private fun initComponents() {
        binding.apply {
            root.setOnClickListener {
                this.tfNameItem.editText?.clearFocus()
                this.tfValueItem.editText?.clearFocus()
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view?.windowToken, 0)
            }

            ibBack.setOnClickListener {
                findNavController().popBackStack()
                clearFragment()
            }

            footerAddItem
                .setCartValue(0.0)
                .setTextButton(getString(R.string.button_text_add))
                .setIconButton(R.drawable.ic_shopping_cart)
                .setActionButton {
                    addViewModel.addItem(
                        tfNameItem.editText?.text.toString(),
                        value,
                        quantity
                    )
                }

            ibMinus.setOnClickListenerWithObserver {
                if (quantity > 1) {
                    quantity -= 1
                }
            }
            ibPlus.setOnClickListenerWithObserver {
                quantity += 1
            }

            tfNameItem.editText?.addTextChangedListener {
                tfNameItem.dismiss()
            }

            tfValueItem.editText?.apply {
                addTextChangedListener(MoneyTextWatcher(tfValueItem.editText))
                addTextChangedListener { editableText ->
                    tfValueItem.dismiss()
                    value = Parses.parseToDouble(editableText.toString())
                    addViewModel.setCollectTotal()
                }
            }
        }
    }

    private fun ImageButton.setOnClickListenerWithObserver(onClickListener: View.OnClickListener) {
        this.apply {
            setOnClickListener {
                onClickListener.onClick(this)
                addViewModel.setCollectTotal()
            }
        }
    }

    private fun listenToRegistrationViewModelEvents() = binding.apply {
        with(addViewModel) {
            registrationStateEvent.observeForever { registrationState ->
                if (registrationState is AddViewModel.AddingState.CollectTotal) {
                    footerAddItem.setCartValue(quantity * value)
                    tvValueQuantity.text = quantity.toString()
                }
                if (registrationState is AddViewModel.AddingState.InvalidAddData) {
                    registrationState.messageError.forEach { fieldError ->
                        binding.root.createSnack(fieldError.second)
                    }
                }
                if (registrationState is AddViewModel.AddingState.SuccessfulAdd) {
                    findNavController().popBackStack()
                    clearFragment()
                }
            }
        }
    }

    private fun clearFragment() {
        _binding = null
        quantity = 0
        value = 0.0
    }

}