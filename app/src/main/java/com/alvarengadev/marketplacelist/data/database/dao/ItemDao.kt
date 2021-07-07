package com.alvarengadev.marketplacelist.data.database.dao

import androidx.room.*
import com.alvarengadev.marketplacelist.data.database.entities.ItemEntity

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: ItemEntity)

    @Delete
    suspend fun delete(itemEntity: ItemEntity)

    @Update
    suspend fun update(itemEntity: ItemEntity)

    @Query("SELECT * FROM marketplace_item WHERE id =:id")
    suspend fun getItem(id: Int): ItemEntity

    @Query("SELECT * FROM marketplace_item")
    suspend fun getAllItems(): List<ItemEntity>
}