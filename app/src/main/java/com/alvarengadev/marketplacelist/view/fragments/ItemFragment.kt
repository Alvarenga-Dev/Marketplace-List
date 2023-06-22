package com.alvarengadev.marketplacelist.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.FragmentItemBinding
import com.alvarengadev.marketplacelist.utils.constants.Constants
import com.alvarengadev.marketplacelist.utils.CurrencyAppUtils
import com.alvarengadev.marketplacelist.utils.MoneyTextWatcher
import com.alvarengadev.marketplacelist.utils.extensions.createSnack
import com.alvarengadev.marketplacelist.utils.extensions.dismiss
import com.alvarengadev.marketplacelist.viewmodel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemFragment : Fragment(R.layout.fragment_item) {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    private val itemViewModel: ItemViewModel by viewModels()
    private val args: ItemFragmentArgs? by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyEditItem(args?.itemId)
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

    private fun verifyEditItem(itemId: Int?) {
        if (itemId != null) {
            itemViewModel.getItemFromDatabase(itemId)
        }
    }

    private fun initComponents() = binding.apply {
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

        ibMinus.setOnClickListener {
            itemViewModel.minusQuantity()
        }
        ibPlus.setOnClickListener {
            itemViewModel.plusQuantity()
        }

        tfNameItem.editText?.addTextChangedListener {
            tfNameItem.dismiss()
        }

        tfValueItem.editText?.apply {
            hint = if (CurrencyAppUtils.getCurrency(context) == Constants.LOCALE_BRAZIL) HINT_CURRENCY_BRAZIL else HINT_CURRENCY_DOLLAR
            addTextChangedListener(MoneyTextWatcher(context, tfValueItem.editText))
            addTextChangedListener { editableText ->
                itemViewModel.setValue(editableText.toString())
                tfValueItem.dismiss()
            }
        }
    }

    private fun listenToRegistrationViewModelEvents() = binding.apply {
        with(itemViewModel) {
            registrationStateEvent.observe(viewLifecycleOwner) { registrationState ->
                if (registrationState is ItemViewModel.AddingState.CollectItem) {
                    footerAddItem.setActionButton {
                        itemViewModel.addItem(tfNameItem.editText?.text.toString())
                    }
                }
                if (registrationState is ItemViewModel.AddingState.CollectItemInformation) {
                    footerAddItem.setCartValue(registrationState.total)
                    tvValueQuantity.text = registrationState.quantity.toString()
                }
                if (registrationState is ItemViewModel.AddingState.InvalidAddData) {
                    registrationState.messageError.forEach { fieldError ->
                        binding.root.createSnack(fieldError.second)
                    }
                }
                if (registrationState is ItemViewModel.AddingState.CollectEditItem) {
                    tvTitleAddItem.text = getString(R.string.fragment_title_edit_item)
                    with(registrationState) {
                        tfNameItem.editText?.setText(name)
                        tfValueItem.editText?.setText(value)
                        tvValueQuantity.text = quantity

                        footerAddItem.apply {
                            setTextButton(getString(R.string.button_text_edit_finish))
                            setActionButton {
                                itemViewModel.editItem(
                                    itemId,
                                    tfNameItem.editText?.text.toString()
                                )
                            }
                        }
                    }
                }
                if (registrationState is ItemViewModel.AddingState.SuccessfulAddOrEdit) {
                    findNavController().popBackStack()
                    clearFragment()
                }
            }
        }
    }

    private fun clearFragment() {
        _binding = null
    }

    private companion object {
        const val HINT_CURRENCY_BRAZIL = "R$ 0,00"
        const val HINT_CURRENCY_DOLLAR = "$0.00"
    }
}

