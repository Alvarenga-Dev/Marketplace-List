package com.alvarengadev.marketplacelist.repository

import com.alvarengadev.marketplacelist.data.database.dao.ItemDao
import com.alvarengadev.marketplacelist.data.database.entities.toArrayListItem
import com.alvarengadev.marketplacelist.data.database.entities.toEntity
import com.alvarengadev.marketplacelist.data.database.entities.toItem
import com.alvarengadev.marketplacelist.data.models.Item
import java.lang.Exception
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val itemDao: ItemDao
) {
    suspend fun insert(item: Item): Boolean = try {
        itemDao.insert(toEntity(item))
        true
    } catch (ex: Exception) {
        false
    }

    suspend fun delete(item: Item): Boolean = try {
        itemDao.delete(toEntity(item))
        true
    } catch (ex: Exception) {
        false
    }

    suspend fun update(itemId: Int, name: String, value: Double, quantity: Int): Boolean = try {
        itemDao.update(
            toEntity(Item(
                name,
                value,
                quantity,
                itemId
            ))
        )
        true
    } catch (ex: Exception) {
        false
    }

    suspend fun getAll(): ArrayList<Item> = try {
        toArrayListItem(itemDao.getAllItems())
    } catch (ex: Exception) {
        arrayListOf()
    }

    suspend fun getItem(itemId: Int): Item? = try {
        toItem(itemDao.getItem(itemId))
    }  catch (ex: Exception) {
        null
    }

}