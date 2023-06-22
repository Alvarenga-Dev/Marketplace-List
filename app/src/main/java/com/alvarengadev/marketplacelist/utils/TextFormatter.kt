package com.alvarengadev.marketplacelist.utils

import android.content.Context
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.data.model.ItemModel
import java.text.NumberFormat

object TextFormatter {
    fun setCurrency(context: Context, currencyValue: Double): String =
        NumberFormat
            .getCurrencyInstance(CurrencyAppUtils.getCurrency(context))
            .format(currencyValue)

    fun messageSharedList(context: Context, listItemModels: List<ItemModel>): String {
        var message = context.getString(R.string.message_shared_title)
        var total = 0.0

        listItemModels.forEach {
            message += context.getString(R.string.message_shared_description, it.name, it.quantity, setCurrency(context, it.value))
            total += (it.value * it.quantity)
        }

        message += context.getString(R.string.message_shared_footer, setCurrency(context, total))

        return message
    }
}
