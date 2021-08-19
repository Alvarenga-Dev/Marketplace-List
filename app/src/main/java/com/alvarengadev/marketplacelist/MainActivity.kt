package com.alvarengadev.marketplacelist

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvarengadev.marketplacelist.utils.Constants
import com.alvarengadev.marketplacelist.utils.PreferencesManager
import com.alvarengadev.marketplacelist.utils.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MarketplaceList)
        setContentView(R.layout.activity_main)
        PreferencesManager.initializeInstance(applicationContext)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                ThemeHelper.applyTheme(Constants.LIGHT_MODE)
                this.recreate()
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                ThemeHelper.applyTheme(Constants.DARK_MODE)
                this.recreate()
            }
        }
    }
}