package com.alvarengadev.marketplacelist.repository

import com.alvarengadev.marketplacelist.data.database.dao.ItemDao
import com.alvarengadev.marketplacelist.data.model.ItemModel
import com.alvarengadev.marketplacelist.data.model.mapper.IItemMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val mapper: IItemMapper
): IItemRepository {

    override suspend fun insertItem(item: ItemModel): Boolean = try {
        itemDao.insert(mapper.itemModelToEntity(item))
        true
    } catch (ex: Exception) {
        false
    }

    override suspend fun deleteItemWithId(id: Int): Boolean = try {
        itemDao.delete(itemDao.getItem(id))
        true
    } catch (ex: Exception) {
        false
    }

    override suspend fun updateItem(
        id: Int,
        name: String,
        value: Double,
        quantity: Int
    ): Boolean = try {
        itemDao.update(
            mapper.itemModelToEntity(
                ItemModel(
                    name,
                    value,
                    quantity,
                    id
                )
            )
        )
        true
    } catch (ex: Exception) {
        false
    }

    override suspend fun deleteAllItems(): Boolean {
        return if (itemDao.getAllItems().isNotEmpty()) {
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

    override suspend fun getAllItems(): List<ItemModel> = try {
        itemDao.getAllItems().map {
            mapper.itemEntityToModel(it)
        }
    } catch (ex: Exception) {
        emptyList()
    }

    override suspend fun getItem(id: Int): ItemModel? = try {
        mapper.itemEntityToModel(
            itemDao.getItem(id)
        )
    } catch (ex: Exception) {
        null
    }

}
