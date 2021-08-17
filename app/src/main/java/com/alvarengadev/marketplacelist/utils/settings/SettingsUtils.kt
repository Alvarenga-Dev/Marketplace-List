package com.alvarengadev.marketplacelist.utils.settings

import android.content.Context
import com.alvarengadev.marketplacelist.BuildConfig
import com.alvarengadev.marketplacelist.R
import kotlin.collections.ArrayList

class SettingsUtils(private val context: Context) {

    data class Options(
        val title: String,
        val description: String?
    )

    fun getListSettings(): ArrayList<Options> {
        val listOptions = ArrayList<Options>()
        listOptions.add(
            Options(
                context.getString(R.string.item_settings_option_title_general),
                context.getString(R.string.item_settings_option_description_general)
            )
        )
        listOptions.add(
            Options(
                context.getString(R.string.item_settings_option_title_about),
                null
            )
        )
        return listOptions
    }

    fun getListSettingsGeneral(
        theme: String,
        currency: String
    ): ArrayList<Options> {
        val listOptions = ArrayList<Options>()
        listOptions.add(
            Options(
                context.getString(R.string.item_general_option_title_theme),
                context.getString(R.string.item_general_option_description, theme)
            )
        )
        listOptions.add(
            Options(
                context.getString(R.string.item_general_option_title_currency),
                context.getString(R.string.item_general_option_description, currency)
            )
        )
        return listOptions
    }

    fun getListSettingsAbout(): ArrayList<Options> {
        val listOptions = ArrayList<Options>()
        listOptions.add(
            Options(
                context.getString(R.string.item_about_option_title_privacy_policy),
                context.getString(R.string.item_about_option_description_privacy_policy)
            )
        )
        listOptions.add(
            Options(
                context.getString(R.string.item_about_option_title_version),
                BuildConfig.VERSION_NAME
            )
        )
        return listOptions
    }
}