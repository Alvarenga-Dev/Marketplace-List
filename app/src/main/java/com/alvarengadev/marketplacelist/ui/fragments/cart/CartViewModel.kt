package com.alvarengadev.marketplacelist.ui.fragments.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.repository.ItemRepository
import com.alvarengadev.marketplacelist.utils.Parses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    sealed class CartListState {
        object None : CartListState()
        object LoadingList : CartListState()
        object ListEmpty : CartListState()
        class SuccessList(val listItems: ArrayList<Item>, val total: Double) : CartListState()
        class Result(val isSuccessful: Boolean) : CartListState()
    }

    sealed class ClearCart {
    }

    private val _registrationStateEvent = MutableLiveData<CartListState>(CartListState.LoadingList)
    val registrationStateEvent: LiveData<CartListState> get() = _registrationStateEvent

    fun getListItems() = viewModelScope.launch {
        val listItems = repository.getAll()
        if (listItems.isNotEmpty()) {
            _registrationStateEvent.postValue(CartListState.SuccessList(listItems, Parses.parseValueTotal(listItems)))
        } else {
            _registrationStateEvent.postValue(CartListState.ListEmpty)
        }
    }

    fun clearCart() = viewModelScope.launch {
        _registrationStateEvent.postValue(CartListState.Result(repository.deleteAll()))
    }

    fun resetClearCart() = _registrationStateEvent.postValue(CartListState.None)
}