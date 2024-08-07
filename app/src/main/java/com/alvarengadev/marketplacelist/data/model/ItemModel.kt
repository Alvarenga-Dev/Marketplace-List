package com.alvarengadev.marketplacelist.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemModel(
    val name: String,
    val value: Double,
    val quantity: Int,
    val id: Int? = null
) : Parcelable
