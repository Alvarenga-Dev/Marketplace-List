package com.alvarengadev.marketplacelist.data.model.mapper

import com.alvarengadev.marketplacelist.data.database.entities.ItemEntity
import com.alvarengadev.marketplacelist.data.model.ItemModel
import javax.inject.Inject

class ItemMapper @Inject constructor(): IItemMapper {

    override fun itemEntityToModel(entity: ItemEntity): ItemModel = ItemModel(
        entity.name,
        entity.value,
        entity.quantity,
        entity.id
    )

    override fun itemModelToEntity(model: ItemModel): ItemEntity = ItemEntity(
        model.name,
        model.value,
        model.quantity,
        model.id
    )
}
