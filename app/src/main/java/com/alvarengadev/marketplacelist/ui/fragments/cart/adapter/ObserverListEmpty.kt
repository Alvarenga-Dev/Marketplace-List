package com.alvarengadev.marketplacelist.ui.fragments.cart.adapter

import com.alvarengadev.marketplacelist.data.models.Item

interface ObserverListEmpty {
    fun observer(listItems: ArrayList<Item>)
}