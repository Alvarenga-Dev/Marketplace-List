package com.alvarengadev.marketplacelist.ui.fragments.add

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
import com.alvarengadev.marketplacelist.databinding.FragmentAddBinding
import com.alvarengadev.marketplacelist.utils.MoneyTextWatcher
import com.alvarengadev.marketplacelist.utils.extensions.createSnack
import com.alvarengadev.marketplacelist.utils.extensions.dismiss
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOrEditFragment : Fragment(R.layout.fragment_add) {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val addOrEditViewModel: AddOrEditViewModel by viewModels()
    private val args: AddOrEditFragmentArgs? by navArgs()

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
            addOrEditViewModel.getDetailsItem(itemId)
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
            addOrEditViewModel.minusQuantity()
        }
        ibPlus.setOnClickListener {
            addOrEditViewModel.plusQuantity()
        }

        tfNameItem.editText?.addTextChangedListener {
            tfNameItem.dismiss()
        }

        tfValueItem.editText?.apply {
            addTextChangedListener(MoneyTextWatcher(tfValueItem.editText))
            addTextChangedListener { editableText ->
                tfValueItem.dismiss()
                addOrEditViewModel.setValue(editableText.toString())
            }
        }
    }

    private fun listenToRegistrationViewModelEvents() = binding.apply {
        with(addOrEditViewModel) {
            registrationStateEvent.observe(viewLifecycleOwner) { registrationState ->
                if (registrationState is AddOrEditViewModel.AddingState.CollectItem) {
                    footerAddItem.setActionButton {
                        addOrEditViewModel.addItem(tfNameItem.editText?.text.toString())
                    }
                }
                if (registrationState is AddOrEditViewModel.AddingState.CollectItemInformation) {
                    footerAddItem.setCartValue(registrationState.total)
                    tvValueQuantity.text = registrationState.quantity.toString()
                }
                if (registrationState is AddOrEditViewModel.AddingState.InvalidAddData) {
                    registrationState.messageError.forEach { fieldError ->
                        binding.root.createSnack(fieldError.second)
                    }
                }
                if (registrationState is AddOrEditViewModel.AddingState.CollectEditItem) {
                    tvTitleAddItem.text = getString(R.string.fragment_title_edit_item)
                    with(registrationState) {
                        tfNameItem.editText?.setText(name)
                        tfValueItem.editText?.setText(value)
                        tvValueQuantity.text = quantity

                        footerAddItem.apply {
                            setTextButton(getString(R.string.button_text_edit_finish))
                            setActionButton {
                                addOrEditViewModel.editItem(
                                    itemId,
                                    tfNameItem.editText?.text.toString()
                                )
                            }
                        }
                    }
                }
                if (registrationState is AddOrEditViewModel.AddingState.SuccessfulAddOrEdit) {
                    findNavController().popBackStack()
                    clearFragment()
                }
            }
        }
    }

    private fun clearFragment() {
        _binding = null
    }
}