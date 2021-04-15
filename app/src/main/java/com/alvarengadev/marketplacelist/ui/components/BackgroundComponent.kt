package com.alvarengadev.marketplacelist.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.alvarengadev.marketplacelist.R

class BackgroundComponent(
        context: Context,
        attrs: AttributeSet
) : LinearLayout(
        context,
        attrs
) {
    private var view: View? = null

    init {
        view = LayoutInflater.from(context)
                .inflate(
                    R.layout.component_background,
                    this,
                    true
                )
    }
}