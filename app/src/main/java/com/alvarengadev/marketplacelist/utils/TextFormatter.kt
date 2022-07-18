package com.alvarengadev.marketplacelist.utils

import android.content.Context
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.data.models.Item
import java.text.NumberFormat

object TextFormatter {
    fun setCurrency(currencyValue: Double): String =
        NumberFormat
            .getCurrencyInstance(CurrencyAppUtils.getCurrency())
            .format(currencyValue)

    fun messageSharedList(context: Context, listItems: ArrayList<Item>): String {
        var message = context.getString(R.string.message_shared_title)
        var total = 0.0

        listItems.forEach {
            message += context.getString(R.string.message_shared_description, it.name, it.quantity, setCurrency(it.value))
            total += (it.value * it.quantity)
        }

        message += context.getString(R.string.message_shared_footer, setCurrency(total))

        return message
    }
}