package com.alvarengadev.marketplacelist.utils

import java.util.*

object CurrencyAppUtils {
    fun getCurrency(): Locale {
        val currency = PreferencesManager.instance?.getCurrency()
        val getLocaleBR = Constants.LOCALE_BRAZIL
        val getLocalEN = Locale.US

        return when (currency) {
            Constants.CURRENCY_BR -> {
                getLocaleBR
            }
            Constants.CURRENCY_EN -> {
                getLocalEN
            }
            else -> {
                if (Locale.getDefault().displayName == getLocaleBR.displayName) getLocaleBR else getLocalEN
            }
        }
    }
}