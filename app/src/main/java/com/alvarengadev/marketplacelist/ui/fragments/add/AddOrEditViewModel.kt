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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrEditViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    sealed class AddingState {
        object CollectItem : AddingState()
        class CollectItemInformation(val total: Double, val quantity: Int) : AddingState()
        object SuccessfulAdd : AddingState()
        class InvalidAddData(val messageError: List<Pair<String, Int>>) : AddingState()
    }

    private val _registrationStateEvent = MutableLiveData<AddingState>(AddingState.CollectItem)
    val registrationStateEvent: LiveData<AddingState> get() = _registrationStateEvent

    private var quantityA = 1
    private var valueA = 0.0

    private fun setCollectTotal() {
        _registrationStateEvent.postValue(AddingState.CollectItemInformation(quantityA * valueA, quantityA))
    }

    fun plusQuantity() {
        quantityA += 1
        setCollectTotal()
    }

    fun minusQuantity() {
        if (quantityA > 1) {
            quantityA -= 1
            setCollectTotal()
        }
    }

    fun setValue(value: String) {
        valueA = Parses.parseToDouble(value)
        setCollectTotal()
    }

    fun addItem(name: String) {
        if (isValidItem(name, valueA)) {
            _registrationStateEvent.postValue(AddingState.CollectItem)
            createItem(name, valueA, quantityA)
        }
    }

    fun editItem(item: Item) {
        if (isValidItem(item.name, item.value)) {
            _registrationStateEvent.postValue(AddingState.CollectItem)
            updateItem(item)
        }
    }

    private fun createItem(
        name: String,
        value: Double,
        quantity: Int
    ) = viewModelScope.launch {
        val isAdd = repository.insert(Item(name, value, quantity))
        if (isAdd) {
            _registrationStateEvent.postValue(AddingState.SuccessfulAdd)
        }
    }

    private fun updateItem(item: Item) = viewModelScope.launch {
        val isEdit = repository.update(item)
        if (isEdit) {
            _registrationStateEvent.postValue(AddingState.SuccessfulAdd)
        }
    }

    private fun isValidItem(name: String, value: Double): Boolean {
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