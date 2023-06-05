package com.alvarengadev.marketplacelist

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alvarengadev.marketplacelist.databinding.ActivityMainBinding
import com.alvarengadev.marketplacelist.utils.ThemeHelper
import com.alvarengadev.marketplacelist.utils.constants.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MarketplaceList)
        setContentView(binding.root)
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
