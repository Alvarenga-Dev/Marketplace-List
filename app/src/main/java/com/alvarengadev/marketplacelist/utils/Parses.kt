package com.alvarengadev.marketplacelist.utils

import android.content.Context
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.utils.constants.Constants
import java.math.BigDecimal
import java.text.NumberFormat

class Parses {
    companion object {
        fun parseToCurrency(context: Context, value: String): String {
            return try {
                val replaceable = String.format(
                    "[%s,.\\s]",
                    NumberFormat.getCurrencyInstance(CurrencyAppUtils.getCurrency(context)).currency?.symbol
                )
                var cleanString = value.replace(replaceable.toRegex(), "").replace("$", "")
                if (cleanString.isEmpty()) cleanString = "0"

                val parsed = BigDecimal(cleanString).setScale(
                    2, BigDecimal.ROUND_FLOOR
                ).divide(
                    BigDecimal(100), BigDecimal.ROUND_FLOOR
                )

                NumberFormat
                    .getCurrencyInstance(CurrencyAppUtils.getCurrency(context))
                    .format(parsed)
            } catch (ex: Exception) {
                ""
            }
        }

        fun parseToDouble(context: Context, value: String): Double {
            return if (value.isNotEmpty()) {
                try {
                    val isLocaleBrazil = CurrencyAppUtils.getCurrency(context).displayName == Constants.LOCALE_BRAZIL.displayName

                    if (isLocaleBrazil) {
                        value.replace("R$", "")
                            .replace(".", "")
                            .replace(",", ".")
                    } else {
                        value.replace("$", "")
                            .replace(",", "")
                    }.trim().toDouble()
                } catch (ex: Exception) {
                    0.0
                }
            } else {
                0.0
            }
        }

        fun parseValueTotal(listItems: ArrayList<Item>): Double {
            var totalValue = 0.0
            listItems.map {
                totalValue += (it.value * it.quantity)
            }
            return totalValue
        }
    }
}
