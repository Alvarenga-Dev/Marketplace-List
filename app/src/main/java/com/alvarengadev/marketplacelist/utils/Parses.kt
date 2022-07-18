package com.alvarengadev.marketplacelist.utils

import com.alvarengadev.marketplacelist.data.models.Item
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class Parses {
    companion object {
        fun parseToCurrency(value: String): String {
            return try {
                val replaceable = String.format(
                    "[%s,.\\s]",
                    NumberFormat.getCurrencyInstance(CurrencyAppUtils.getCurrency()).currency?.symbol
                )
                var cleanString = value.replace(replaceable.toRegex(), "").replace("$", "")
                if (cleanString.isEmpty()) cleanString = "0"

                val parsed = BigDecimal(cleanString).setScale(
                    2, BigDecimal.ROUND_FLOOR
                ).divide(
                    BigDecimal(100), BigDecimal.ROUND_FLOOR
                )

                NumberFormat
                    .getCurrencyInstance(CurrencyAppUtils.getCurrency())
                    .format(parsed)
            } catch (ex: Exception) {
                ""
            }
        }

        fun parseToDouble(value: String): Double {
            return if (value.isNotEmpty()) {
                try {
                    if (isLocaleBrazil()) {
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

        fun isLocaleBrazil() = CurrencyAppUtils.getCurrency().displayName == Constants.LOCALE_BRAZIL.displayName
    }
}