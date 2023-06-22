package com.alvarengadev.marketplacelist.repository

import com.alvarengadev.marketplacelist.data.model.ItemModel

interface IItemRepository {

    suspend fun insertItem(item: ItemModel): Boolean

    suspend fun deleteItemWithId(id: Int): Boolean

    suspend fun updateItem(
        id: Int,
        name: String,
        value: Double,
        quantity: Int
    ): Boolean

    suspend fun deleteAllItems(): Boolean

    suspend fun getAllItems(): List<ItemModel>

    suspend fun getItem(id: Int): ItemModel?
}
