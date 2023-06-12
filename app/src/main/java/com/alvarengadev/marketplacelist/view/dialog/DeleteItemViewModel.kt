package com.alvarengadev.marketplacelist.view.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.repository.IItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteItemViewModel @Inject constructor(
    private val repository: IItemRepository
) : ViewModel() {

    sealed class DeleteState {
        class Result(val isSuccessful: Boolean) : DeleteState()
    }

    private val _registrationStateEvent = MutableLiveData<DeleteState>()
    val registrationStateEvent: LiveData<DeleteState> get() = _registrationStateEvent

    fun deleteItemWithId(
        id: Int
    ) = viewModelScope.launch {
        _registrationStateEvent.postValue(
            DeleteState.Result(
                repository.deleteItemWithId(id)
            )
        )
    }
}
