package com.alvarengadev.marketplacelist.utils.settings

import android.content.Context
import com.alvarengadev.marketplacelist.BuildConfig
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.utils.Parses
import com.alvarengadev.marketplacelist.utils.enums.TypeOptionSettings

class SettingsUtils {

    private var context: Context? = null

    fun getInstance(context: Context?) {
        this.context = context
    }

    data class Options(
        val title: String?,
        var description: String?,
        val typeOptionSettings: TypeOptionSettings? = null
    )

    fun getListSettings(): ArrayList<Options> {
        val listOptions = ArrayList<Options>()
        listOptions.add(
            Options(
                context?.getString(R.string.item_settings_option_title_general),
                context?.getString(R.string.item_settings_option_description_general),
                TypeOptionSettings.SETTINGS_GENERAL
            )
        )
        listOptions.add(
            Options(
                context?.getString(R.string.item_settings_option_title_about),
                null,
                TypeOptionSettings.SETTINGS_ABOUT
            )
        )
        return listOptions
    }

    fun getListSettingsGeneral(): ArrayList<Options> {
        val listOptions = ArrayList<Options>()
        listOptions.add(
            Options(
                context?.getString(R.string.item_general_option_title_currency),
                if (Parses.isLocaleBrazil()) context?.getString(R.string.item_general_option_description_real) else context?.getString(R.string.item_general_option_description_dollar),
                TypeOptionSettings.GENERAL_CURRENCY
            )
        )
        return listOptions
    }

    fun getListSettingsAbout(): ArrayList<Options> {
        val listOptions = ArrayList<Options>()
        listOptions.add(
            Options(
                context?.getString(R.string.item_about_option_title_privacy_policy),
                context?.getString(R.string.item_about_option_description_privacy_policy),
                TypeOptionSettings.ABOUT_PRIVACY_POLICY
            )
        )
        listOptions.add(
            Options(
                context?.getString(R.string.item_about_option_title_version),
                BuildConfig.VERSION_NAME
            )
        )
        return listOptions
    }
}