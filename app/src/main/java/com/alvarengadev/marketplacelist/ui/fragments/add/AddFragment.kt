package com.alvarengadev.marketplacelist.ui.fragments.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.databinding.FragmentAddBinding
import com.alvarengadev.marketplacelist.utils.Parses
import com.alvarengadev.marketplacelist.utils.MoneyTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val addViewModel: AddViewModel by viewModels()

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
                    val valueItem = addViewModel.valueItem.value
                    val quantity = addViewModel.quantity.value
                    if (valueItem != null && quantity != null) {
                       addNewItemInDatabase(Item(
                           tfNameItem.editText?.text.toString(),
                           valueItem,
                           quantity
                       ))
                    } else {
                        Toast.makeText(context, "Value or Quantity is empty!", Toast.LENGTH_SHORT).show()
                    }
                }

            initQuantity()

            tfValueItem.editText?.addTextChangedListener(MoneyTextWatcher(tfValueItem.editText))
            tfValueItem.editText?.addTextChangedListener { editableText ->
                addViewModel.valueItem.value = Parses.parseToDouble(editableText.toString())
            }

            addViewModel.apply {
                quantity.observeForever { quantity ->
                    tvValueQuantity.text = quantity.toString()
                    val valueItem = valueItem.value
                    if (valueItem != null) {
                        footerAddItem.setCartValue(valueItem * quantity)
                    }
                }
                valueItem.observeForever { valueItem ->
                    val quantity = quantity.value
                    footerAddItem.setCartValue(
                        if (quantity != null) {
                            valueItem * quantity
                        } else {
                            valueItem
                        }
                    )
                }
            }

        }
    }

    private fun addNewItemInDatabase(item: Item) {
        addViewModel.addNewItem(item)
        addViewModel.isAdd.observeForever { isAdded ->
         Toast.makeText(context, if (isAdded) "Add :D" else "Erro :(", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initQuantity() {
        binding.apply {
            addViewModel.quantity.apply {
                value = value ?: 1

                ibMinus.setOnClickListener {
                    if (value!! > 1) {
                        value = value?.minus(1)
                    }
                }
                ibPlus.setOnClickListener {
                    value = value?.plus(1)
                }
            }
        }
    }

    private fun clearFragment() = addViewModel.apply {
        _binding = null
        valueItem.postValue(0.0)
        quantity.postValue(1)
    }

}