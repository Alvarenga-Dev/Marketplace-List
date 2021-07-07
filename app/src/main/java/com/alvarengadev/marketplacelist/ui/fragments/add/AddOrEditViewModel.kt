package com.alvarengadev.marketplacelist.ui.fragments.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.repository.ItemRepository
import com.alvarengadev.marketplacelist.utils.Constants
import com.alvarengadev.marketplacelist.utils.Parses
import com.alvarengadev.marketplacelist.utils.TextFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrEditViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    sealed class AddingState {
        object CollectItem : AddingState()
        object SuccessfulAddOrEdit : AddingState()
        class InvalidAddData(val messageError: List<Pair<String, Int>>) : AddingState()
        class CollectItemInformation(
            val total: Double,
            val quantity: Int
        ) : AddingState()

        class CollectEditItem(
            val id: Int,
            val name: String,
            val value: String,
            val quantity: String
        ) : AddingState()
    }

    private val _registrationStateEvent = MutableLiveData<AddingState>(AddingState.CollectItem)
    val registrationStateEvent: LiveData<AddingState> get() = _registrationStateEvent

    private var quantity = 1
    private var value = 0.0

    private fun setCollectTotal() {
        _registrationStateEvent.postValue(
            AddingState.CollectItemInformation(
                quantity * value,
                quantity
            )
        )
    }

    fun plusQuantity() {
        quantity += 1
        setCollectTotal()
    }

    fun minusQuantity() {
        if (quantity > 1) {
            quantity -= 1
            setCollectTotal()
        }
    }

    fun setValue(value: String) {
        this.value = Parses.parseToDouble(value)
        setCollectTotal()
    }

    fun addItem(name: String) {
        if (isValidItem(name, value)) {
            _registrationStateEvent.postValue(AddingState.CollectItem)
            createItem(name, value, quantity)
        }
    }

    fun editItem(
        itemId: Int,
        name: String
    ) {
        if (isValidItem(name, value)) {
            _registrationStateEvent.postValue(AddingState.CollectItem)
            updateItem(itemId, name)
        }
    }

    fun getDetailsItem(itemId: Int) = viewModelScope.launch {
        val item = repository.getItem(itemId)
        if (item != null) {
            with(item) {
                this@AddOrEditViewModel.value = value
                this@AddOrEditViewModel.quantity = quantity

                _registrationStateEvent.postValue(id?.let { id ->
                    AddingState.CollectEditItem(
                        id,
                        name,
                        TextFormatter.setCurrency(value),
                        quantity.toString()
                    )
                })
            }
        }
    }

    private fun createItem(
        name: String,
        value: Double,
        quantity: Int
    ) = viewModelScope.launch {
        val isAdd = repository.insert(Item(name, value, quantity))
        if (isAdd) {
            _registrationStateEvent.postValue(AddingState.SuccessfulAddOrEdit)
        }
    }

    private fun updateItem(
        itemId: Int,
        name: String
    ) = viewModelScope.launch {
        val isEdit = repository.update(itemId, name, value, quantity)
        if (isEdit) {
            _registrationStateEvent.postValue(AddingState.SuccessfulAddOrEdit)
        }
    }

    private fun isValidItem(
        name: String,
        value: Double
    ): Boolean {
        val messageForError = arrayListOf<Pair<String, Int>>()
        if (name.isEmpty()) {
            messageForError.add(INPUT_NAME_ITEM)
        }

        if (value == 0.0) {
            messageForError.add(INPUT_VALUE_ITEM)
        }

        if (messageForError.isNotEmpty()) {
            _registrationStateEvent.postValue(AddingState.InvalidAddData(messageForError))
            return false
        }

        return true
    }

    companion object {
        val INPUT_NAME_ITEM = Constants.INPUT_NAME_ITEM to R.string.alert_text_name_empty
        val INPUT_VALUE_ITEM = Constants.INPUT_VALUE_ITEM to R.string.alert_text_value_empty
    }
}