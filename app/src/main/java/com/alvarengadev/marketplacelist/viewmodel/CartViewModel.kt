package com.alvarengadev.marketplacelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.data.model.ItemModel
import com.alvarengadev.marketplacelist.repository.IItemRepository
import com.alvarengadev.marketplacelist.utils.Parses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: IItemRepository
) : ViewModel() {

    sealed class CartListState {
        object None : CartListState()
        object LoadingList : CartListState()
        object ListEmpty : CartListState()
        object BottomSheet : CartListState()
        class SuccessList(val listItemModels: List<ItemModel>, val total: Double) : CartListState()

        class Result(val isSuccessful: Boolean) : CartListState()
    }

    private val _registrationStateEvent = MutableLiveData<CartListState>(CartListState.LoadingList)
    val registrationStateEvent: LiveData<CartListState> get() = _registrationStateEvent

    fun getAllItemsFromDatabase() = viewModelScope.launch {
        _registrationStateEvent.postValue(CartListState.LoadingList)
        val listItems = repository.getAllItems()
        if (listItems.isNotEmpty()) {
            _registrationStateEvent.postValue(
                CartListState.SuccessList(
                    listItems,
                    Parses.parseValueTotal(listItems)
                )
            )
        } else {
            _registrationStateEvent.postValue(CartListState.ListEmpty)
        }
    }

    fun deleteAllItemsFromDatabase() = viewModelScope.launch {
        _registrationStateEvent.postValue(
            CartListState.Result(
                repository.deleteAllItems()
            )
        )
    }

    fun resetClearCart() = _registrationStateEvent.postValue(CartListState.None)

    fun viewBottomSheetNewsFunctions() = viewModelScope.launch {
        if (repository.getAllItems().isNotEmpty()) {
            _registrationStateEvent.postValue(CartListState.BottomSheet)
        }
    }
}
