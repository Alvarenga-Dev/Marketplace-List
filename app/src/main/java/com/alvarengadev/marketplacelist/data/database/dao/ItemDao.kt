package com.alvarengadev.marketplacelist.data.database.dao

import androidx.room.*
import com.alvarengadev.marketplacelist.data.database.entities.ItemEntity

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)

    @Update
    suspend fun update(item: ItemEntity)

    @Query("DELETE FROM marketplace_item")
    suspend fun deleteAll()

    @Query("SELECT * FROM marketplace_item WHERE id =:id")
    suspend fun getItem(id: Int): ItemEntity

    @Query("SELECT * FROM marketplace_item")
    suspend fun getAllItems(): List<ItemEntity>
}
