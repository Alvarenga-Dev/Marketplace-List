package com.alvarengadev.marketplacelist.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class Parses {
    companion object {
        fun parseToCurrency(value: String): String {
            val replaceable = String.format(
                "[%s,.\\s]",
                NumberFormat.getCurrencyInstance(Locale("pt", "br")).currency?.symbol
            )
            var cleanString = value.replace(replaceable.toRegex(), "")
            if (cleanString.isEmpty()) cleanString = "0"

            val parsed = BigDecimal(cleanString).setScale(
                2, BigDecimal.ROUND_FLOOR
            ).divide(
                BigDecimal(100), BigDecimal.ROUND_FLOOR
            )

            return NumberFormat
                .getCurrencyInstance(Locale("pt", "br"))
                .format(parsed)
        }
        fun parseToDouble(value: String): Double {
            return if (value.isNotEmpty()) value.replace("R$", "").replace(".", "").replace(",", ".").trim().toDouble() else 0.0
        }
    }
}