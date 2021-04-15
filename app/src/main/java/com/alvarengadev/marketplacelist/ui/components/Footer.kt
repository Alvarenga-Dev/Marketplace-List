package com.alvarengadev.marketplacelist.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alvarengadev.marketplacelist.R

class Footer(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(
    context,
    attrs
) {
    private var view: View? = null
    private var btnActionFooter: Button? = null
    private var tvCartValue: TextView? = null

    init {
        view = LayoutInflater.from(context)
            .inflate(
                R.layout.component_footer,
                this,
                true
            )

        btnActionFooter = view?.findViewById(R.id.btnActionFooter)
        tvCartValue = view?.findViewById(R.id.tvCartValue)
    }

    fun setTextButton(titleButton: String): Footer {
        btnActionFooter?.text = titleButton
        return this
    }

    fun setActionButton(viewOnClickListener: OnClickListener) {
        btnActionFooter?.setOnClickListener(viewOnClickListener)
    }

    fun setCartValue() {

    }
}