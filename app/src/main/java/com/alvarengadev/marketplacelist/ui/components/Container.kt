package com.alvarengadev.marketplacelist.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.alvarengadev.marketplacelist.R

class Container(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(
    context,
    attrs
) {
    private var view: View? = null

    init {
        view = LayoutInflater.from(context)
                .inflate(
                    R.layout.component_container,
                    this,
                    true
                )
    }
}