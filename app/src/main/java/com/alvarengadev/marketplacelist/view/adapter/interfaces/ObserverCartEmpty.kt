package com.alvarengadev.marketplacelist.view.adapter.interfaces

interface ObserverCartEmpty {
    fun observer(
        total: Double,
        isEmpty: Boolean
    )
}
