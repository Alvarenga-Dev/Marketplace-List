package com.alvarengadev.marketplacelist.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alvarengadev.marketplacelist.data.models.Item

@Entity(tableName = "marketplace_item")
data class ItemEntity(
    val name: String,
    val value: Double,
    val quantity: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)

fun toItem(listItemsEntity: List<ItemEntity>): ArrayList<Item> {
    val listItems = ArrayList<Item>()
    for (item in listItemsEntity) {
        val itemConverter = Item(
            item.name,
            item.value,
            item.quantity
        )
        listItems.add(itemConverter)
    }
    return listItems
}