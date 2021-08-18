package com.alvarengadev.marketplacelist.utils

import java.util.*

object CurrencyAppUtils {
    fun getCurrency(): String {
        val getLocaleBR = Locale("pt", "BR")

        return if (Locale.getDefault().displayName == getLocaleBR.displayName) "Real - BR (R$)" else "Dólar - US ($)"
    }
}