package com.alvarengadev.marketplacelist.ui.fragments.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {
    val listItems = MutableLiveData<ArrayList<Item>>()

    fun getListItems() =
        viewModelScope.launch {
            listItems.value = repository.getAll()
        }
}