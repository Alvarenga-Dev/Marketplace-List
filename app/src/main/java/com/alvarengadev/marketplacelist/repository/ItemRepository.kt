package com.alvarengadev.marketplacelist.repository

import com.alvarengadev.marketplacelist.data.database.dao.ItemDao
import com.alvarengadev.marketplacelist.data.database.entities.toArrayListItem
import com.alvarengadev.marketplacelist.data.database.entities.toEntity
import com.alvarengadev.marketplacelist.data.database.entities.toItem
import com.alvarengadev.marketplacelist.data.models.Item
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val itemDao: ItemDao
) {
    suspend fun createItemFromDatabase(
        item: Item
    ): Boolean = try {
        itemDao.insert(toEntity(item))
        true
    } catch (ex: Exception) {
        false
    }

    suspend fun deleteItemFromDatabase(
        itemId: Int
    ): Boolean = try {
        itemDao.delete(itemDao.getItem(itemId))
        true
    } catch (ex: Exception) {
        false
    }

    suspend fun updateItemFromDatabase(
        itemId: Int,
        name: String,
        value: Double,
        quantity: Int
    ): Boolean = try {
        itemDao.update(
            toEntity(
                Item(
                    name,
                    value,
                    quantity,
                    itemId
                )
            )
        )
        true
    } catch (ex: Exception) {
        false
    }

    suspend fun deleteAllItemsFromDatabase(): Boolean {
        return if (getAllItemsFromDatabase().isNotEmpty()) {
            try {
                itemDao.deleteAll()
                true
            } catch (ex: Exception) {
                false
            }
        } else {
            false
        }
    }

    suspend fun getAllItemsFromDatabase(): ArrayList<Item> = try {
        toArrayListItem(itemDao.getAllItems())
    } catch (ex: Exception) {
        arrayListOf()
    }

    suspend fun getItemFromDatabase(
        itemId: Int
    ): Item? = try {
        toItem(
            itemDao.getItem(itemId)
        )
    } catch (ex: Exception) {
        null
    }

}