package com.alvarengadev.marketplacelist.utils

import com.alvarengadev.marketplacelist.data.models.Item
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class Parses {
    companion object {
        fun parseToCurrency(value: String): String {
            val replaceable = String.format(
                "[%s,.\\s]",
                NumberFormat.getCurrencyInstance(CurrencyAppUtils.getCurrency()).currency?.symbol
            )
            var cleanString = value.replace(replaceable.toRegex(), "")
            if (cleanString.isEmpty()) cleanString = "0"

            val parsed = BigDecimal(cleanString).setScale(
                2, BigDecimal.ROUND_FLOOR
            ).divide(
                BigDecimal(100), BigDecimal.ROUND_FLOOR
            )

            return NumberFormat
                .getCurrencyInstance(CurrencyAppUtils.getCurrency())
                .format(parsed)
        }

        fun parseToDouble(value: String): Double {
            return if (value.isNotEmpty()) {
                if (isLocaleBrazil()) {
                    value.replace("R$", "")
                        .replace(".", "")
                        .replace(",", ".")
                } else {
                    value.replace("$", "")
                        .replace(",", "")
                }.trim().toDouble()
            } else {
                0.0
            }
        }

        fun parseValueTotal(listItems: ArrayList<Item>): Double {
            var totalValue = 0.0

            for (item in listItems) {
                totalValue += (item.value * item.quantity)
            }

            return totalValue
        }

        fun isLocaleBrazil() = CurrencyAppUtils.getCurrency().displayName == Constants.LOCALE_BRAZIL.displayName
    }
}