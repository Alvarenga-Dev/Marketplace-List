package com.alvarengadev.marketplacelist.ui.fragments.cart.dialog

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
    val isDelete = MutableLiveData<Boolean>()

    fun deleteItem(item: Item) =
        viewModelScope.launch {
            isDelete.value = repository.delete(item)
        }
}