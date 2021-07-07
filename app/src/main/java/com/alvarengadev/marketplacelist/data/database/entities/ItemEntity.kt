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

fun toArrayListItem(listItemsEntity: List<ItemEntity>): ArrayList<Item> {
    val listItems = ArrayList<Item>()
    for (item in listItemsEntity) {
        val itemConverter = Item(
            item.name,
            item.value,
            item.quantity,
            item.id
        )
        listItems.add(itemConverter)
    }
    return listItems
}

fun toEntity(item: Item): ItemEntity = ItemEntity(
    item.name,
    item.value,
    item.quantity,
    item.id
)

fun toItem(itemEntity: ItemEntity): Item = Item(
    itemEntity.name,
    itemEntity.value,
    itemEntity.quantity,
    itemEntity.id
)