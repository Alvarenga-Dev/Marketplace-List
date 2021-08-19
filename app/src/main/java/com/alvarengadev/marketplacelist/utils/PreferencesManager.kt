package com.alvarengadev.marketplacelist.utils

import android.content.Context

class PreferencesManager private constructor(context: Context) {

    private val mPref = context.getSharedPreferences(PREFERENCES_MARKETPLACE_LIST, Context.MODE_PRIVATE)

    fun setCurrency(currency: String) {
        mPref.edit()
            .putString(KEY_CURRENCY, currency)
            .apply()
    }

    fun getCurrency(): String? = mPref.getString(KEY_CURRENCY, null)

    companion object {
        private const val PREFERENCES_MARKETPLACE_LIST = "com.alvarengadev.marketplacelist.PREFERENCES_MARKETPLACE_LIST"
        private const val KEY_CURRENCY = "com.alvarengadev.marketplacelist.KEY_CURRENCY"

        private var sInstance: PreferencesManager? = null

        @Synchronized
        fun initializeInstance(context: Context) {
            if (sInstance == null) {
                sInstance = PreferencesManager(context)
            }
        }

        @get:Synchronized
        val instance: PreferencesManager?
            get() {
                checkNotNull(sInstance) {
                    PreferencesManager::class.java.simpleName + " is not initialized, call initializeInstance(..) method first."
                }
                return sInstance
            }
    }

}