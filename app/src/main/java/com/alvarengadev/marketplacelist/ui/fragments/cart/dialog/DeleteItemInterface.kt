package com.alvarengadev.marketplacelist.ui.fragments.cart.dialog

import com.alvarengadev.marketplacelist.data.models.Item

interface DeleteItemInterface {
    fun notifyItemDelete(item: Item)
}