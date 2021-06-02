package com.alvarengadev.marketplacelist.ui.fragments.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {
    val valueItem = MutableLiveData<Double>()
    val quantity = MutableLiveData<Int>()
    val isAdd = MutableLiveData<Boolean>()

    fun addNewItem(item: Item) {
        viewModelScope.launch {
            isAdd.value = repository.insert(item)
        }
    }
}