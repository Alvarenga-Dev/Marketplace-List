package com.alvarengadev.marketplacelist.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alvarengadev.marketplacelist.data.model.ItemModel

@Entity(tableName = "marketplace_item")
data class ItemEntity(
    val name: String,
    val value: Double,
    val quantity: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
