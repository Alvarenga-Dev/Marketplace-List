package com.alvarengadev.marketplacelist.ui.fragments.cart.dialog.delete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteItemViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    sealed class DeleteState {
        class Result(val isSuccessful: Boolean) : DeleteState()
    }

    private val _registrationStateEvent = MutableLiveData<DeleteState>()
    val registrationStateEvent: LiveData<DeleteState> get() = _registrationStateEvent

    fun deleteItem(item: Item) = viewModelScope.launch {
        _registrationStateEvent.postValue(DeleteState.Result(repository.delete(item)))
    }
}