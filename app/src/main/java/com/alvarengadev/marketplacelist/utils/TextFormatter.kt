package com.alvarengadev.marketplacelist.utils

import java.text.NumberFormat
import java.util.*

object TextFormatter {
    fun setCurrency(currencyValue: Double): String =
        NumberFormat
            .getCurrencyInstance(Locale("pt", "br"))
            .format(currencyValue)
}