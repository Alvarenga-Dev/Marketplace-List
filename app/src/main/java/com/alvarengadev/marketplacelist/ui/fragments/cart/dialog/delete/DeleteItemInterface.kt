package com.alvarengadev.marketplacelist.ui.fragments.cart.dialog.delete

import com.alvarengadev.marketplacelist.data.models.Item

interface DeleteItemInterface {
    fun notifyItemDelete(item: Item)
}