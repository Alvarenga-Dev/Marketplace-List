package com.alvarengadev.marketplacelist.ui.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.repository.ItemRepository
import com.alvarengadev.marketplacelist.utils.TextFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    sealed class DetailsState {
        object CollectItem : DetailsState()
        class SuccessGetItem(val id: Int, val name: String, val value: String, val quantity: String) : DetailsState()
    }

    private val _registrationStateEvent = MutableLiveData<DetailsState>(DetailsState.CollectItem)
    val registrationStateEvent: LiveData<DetailsState> get() = _registrationStateEvent

    fun getItemFromDatabase(
        itemId: Int
    ) = viewModelScope.launch {
        val item = repository.getItemFromDatabase(itemId)
        if (item != null) {
            with(item) {
                _registrationStateEvent.postValue(id?.let { id ->
                    DetailsState.SuccessGetItem(
                        id,
                        name,
                        TextFormatter.setCurrency(value),
                        quantity.toString()
                    )
                })
            }
        }
    }

}