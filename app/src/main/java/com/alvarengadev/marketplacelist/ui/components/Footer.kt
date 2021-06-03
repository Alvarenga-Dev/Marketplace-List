package com.alvarengadev.marketplacelist.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.utils.TextFormatter
import com.google.android.material.button.MaterialButton

class Footer(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(
    context,
    attrs
) {
    private var view: View? = null
    private var btnActionFooter: MaterialButton? = null
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

    fun setIconButton(recourse: Int): Footer {
        btnActionFooter?.setIconResource(recourse)
        return this
    }

    fun setActionButton(viewOnClickListener: OnClickListener) {
        btnActionFooter?.setOnClickListener(viewOnClickListener)
    }

    fun setCartValue(value: Double): Footer {
        tvCartValue?.text = context.getString(R.string.text_total_cart, TextFormatter.setCurrency(value))
        return this
    }
}