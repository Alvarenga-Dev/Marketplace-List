package com.alvarengadev.marketplacelist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alvarengadev.marketplacelist.data.database.dao.ItemDao
import com.alvarengadev.marketplacelist.data.database.entities.ItemEntity

@Database(
    entities = [ItemEntity::class],
    version = 1
)
abstract class MarketplaceDatabase : RoomDatabase() {
    abstract fun itemDao() : ItemDao
}