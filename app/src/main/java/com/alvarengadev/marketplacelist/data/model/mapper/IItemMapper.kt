package com.alvarengadev.marketplacelist.data.model.mapper

import com.alvarengadev.marketplacelist.data.database.entities.ItemEntity
import com.alvarengadev.marketplacelist.data.model.ItemModel

interface IItemMapper {

    fun itemEntityToModel(entity: ItemEntity): ItemModel

    fun itemModelToEntity(model: ItemModel): ItemEntity
}
