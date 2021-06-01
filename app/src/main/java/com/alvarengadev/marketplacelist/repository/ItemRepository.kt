package com.alvarengadev.marketplacelist.repository

import com.alvarengadev.marketplacelist.data.database.dao.ItemDao
import com.alvarengadev.marketplacelist.data.database.entities.toEntity
import com.alvarengadev.marketplacelist.data.models.Item
import java.lang.Exception
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val itemDao: ItemDao
) {
    suspend fun insert(item: Item): Boolean =
        try {
            itemDao.insert(toEntity(item))
            true
        } catch (ex: Exception) {
            false
        }

}