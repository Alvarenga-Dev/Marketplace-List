package com.alvarengadev.marketplacelist.ui.fragments.details

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.repository.IItemRepository
import com.alvarengadev.marketplacelist.utils.TextFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: IItemRepository,
    application: Application,
) : AndroidViewModel(application) {

    private val context: Context
        get() = getApplication<Application>().applicationContext

    sealed class DetailsState {
        object CollectItem : DetailsState()
        class SuccessGetItem(val id: Int, val name: String, val value: String, val quantity: String) : DetailsState()
    }

    private val _registrationStateEvent = MutableLiveData<DetailsState>(DetailsState.CollectItem)
    val registrationStateEvent: LiveData<DetailsState> get() = _registrationStateEvent

    fun getItemFromDatabase(
        itemId: Int
    ) = viewModelScope.launch {
        val item = repository.getItem(itemId)
        if (item != null) {
            with(item) {
                _registrationStateEvent.postValue(id?.let { id ->
                    DetailsState.SuccessGetItem(
                        id,
                        name,
                        TextFormatter.setCurrency(context, value),
                        quantity.toString()
                    )
                })
            }
        }
    }

}
