package com.alvarengadev.marketplacelist.ui.fragments.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    sealed class AddingState {
        object CollectItem : AddingState()
        class InvalidAddData(val fields: List<Pair<String, Int>>) : AddingState()
    }

    private val _registrationStateEvent = MutableLiveData<AddingState>(AddingState.CollectItem)
    val registrationStateEvent: LiveData<AddingState> get() = _registrationStateEvent

    val valueItem = MutableLiveData<Double>()
    val quantity = MutableLiveData<Int>()
    val isAdd = MutableLiveData<Boolean>()

    fun addNewItem(item: Item) =
        viewModelScope.launch {
            isAdd.postValue(if (item.name.isEmpty()) false else repository.insert(item))
        }

    fun addItem(item: Item) {
        if (isValidItem(item.name, item.value)) {
            _registrationStateEvent.postValue(AddingState.CollectItem)
        }
    }

    private fun isValidItem(name: String, value: Double): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if (name.isEmpty()) {
            invalidFields.add(INPUT_NAME_ITEM)
        }

        if (value != 0.0) {
            invalidFields.add(INPUT_VALUE_ITEM)
        }

        if (invalidFields.isNotEmpty()) {
            isAdd.postValue(false)
            _registrationStateEvent.postValue(AddingState.InvalidAddData(invalidFields))
            return false
        }

        return true
    }

    companion object {
        val INPUT_NAME_ITEM = "INPUT_NAME_ITEM" to R.string.alert_text_name_empty
        val INPUT_VALUE_ITEM = "INPUT_VALUE_ITEM" to R.string.alert_text_value_empty
    }
}