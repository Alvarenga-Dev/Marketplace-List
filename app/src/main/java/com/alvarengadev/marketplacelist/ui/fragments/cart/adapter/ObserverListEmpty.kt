package com.alvarengadev.marketplacelist.ui.fragments.cart.adapter

interface ObserverListEmpty {
    fun observer(
        total: Double,
        isEmpty: Boolean
    )
}