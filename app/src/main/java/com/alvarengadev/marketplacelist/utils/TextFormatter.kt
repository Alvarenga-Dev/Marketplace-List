package com.alvarengadev.marketplacelist.utils

import java.text.NumberFormat

object TextFormatter {
    fun setCurrency(currencyValue: Double): String =
        NumberFormat
            .getCurrencyInstance(CurrencyAppUtils.getCurrency())
            .format(currencyValue)
}