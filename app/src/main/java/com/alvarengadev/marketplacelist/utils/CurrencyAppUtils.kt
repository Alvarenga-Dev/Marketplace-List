package com.alvarengadev.marketplacelist.utils

import android.content.Context
import com.alvarengadev.marketplacelist.utils.constants.Constants
import com.alvarengadev.marketplacelist.utils.constants.Constants.Companion.CURRENCY_BR
import com.alvarengadev.marketplacelist.utils.constants.Constants.Companion.CURRENCY_EN
import com.alvarengadev.marketplacelist.utils.constants.KEY_CURRENCY
import com.alvarengadev.marketplacelist.utils.extensions.preferences
import java.util.Locale

object CurrencyAppUtils {
    fun getCurrency(context: Context): Locale {
        val getLocaleBR = Constants.LOCALE_BRAZIL
        val getLocalEN = Locale.US

        return when (context.preferences.getString(KEY_CURRENCY, null)) {
            CURRENCY_BR -> {
                getLocaleBR
            }
            CURRENCY_EN -> {
                getLocalEN
            }
            else -> {
                if (Locale.getDefault().displayName == getLocaleBR.displayName) getLocaleBR else getLocalEN
            }
        }
    }
}
