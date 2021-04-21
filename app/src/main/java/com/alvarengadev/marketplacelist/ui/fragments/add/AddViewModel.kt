package com.alvarengadev.marketplacelist.ui.fragments.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel : ViewModel() {
    val valueItem = MutableLiveData<Double>()
    val quantity = MutableLiveData<Int>()
}