package com.alvarengadev.marketplacelist.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class Parses {
    companion object {
        fun parseToCurrency(value: String): String {
            val replaceable = String.format(
                "[%s,.\\s]",
                NumberFormat.getCurrencyInstance(parseLocale()).currency?.symbol
            )
            var cleanString = value.replace(replaceable.toRegex(), "")
            if (cleanString.isEmpty()) cleanString = "0"

            val parsed = BigDecimal(cleanString).setScale(
                2, BigDecimal.ROUND_FLOOR
            ).divide(
                BigDecimal(100), BigDecimal.ROUND_FLOOR
            )

            return NumberFormat
                .getCurrencyInstance(parseLocale())
                .format(parsed)
        }

        fun parseToDouble(value: String): Double {
            return if (value.isNotEmpty()) {
                if (Locale.getDefault().displayName == Locale("pt", "BR").displayName) {
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

        fun parseLocale(): Locale {
            val getLocaleBR = Locale("pt", "BR")
            val getLocalEN = Locale.US

            return if (Locale.getDefault().displayName == getLocaleBR.displayName) getLocaleBR else getLocalEN
        }
    }
}