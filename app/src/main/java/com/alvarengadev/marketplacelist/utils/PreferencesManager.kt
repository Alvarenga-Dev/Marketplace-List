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

    fun setViewOnboardingWelcome() {
        mPref.edit()
            .putBoolean(KEY_ONBOARDING_WELCOME, true)
            .apply()
    }

    fun getViewOnboardingWelcome(): Boolean = mPref.getBoolean(KEY_ONBOARDING_WELCOME, false)

    fun setViewFeatureClearCart() {
        mPref.edit()
            .putBoolean(KEY_FEATURE_CLEAR_CART, true)
            .apply()
    }

    fun getViewFeatureClearCart(): Boolean = mPref.getBoolean(KEY_FEATURE_CLEAR_CART, false)

    companion object {
        private const val PREFERENCES_MARKETPLACE_LIST = "com.alvarengadev.marketplacelist.PREFERENCES_MARKETPLACE_LIST"
        private const val KEY_CURRENCY = "com.alvarengadev.marketplacelist.KEY_CURRENCY"
        private const val KEY_ONBOARDING_WELCOME = "com.alvarengadev.marketplacelist.KEY_ONBOARDING_WELCOME"
        private const val KEY_FEATURE_CLEAR_CART = "com.alvarengadev.marketplacelist.KEY_FEATURE_CLEAR_CART"

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