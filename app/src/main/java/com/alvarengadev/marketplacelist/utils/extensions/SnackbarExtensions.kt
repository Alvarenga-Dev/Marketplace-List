package com.alvarengadev.marketplacelist.utils.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.createSnack(
    messageResId: Int,
    root: View? = null,
    length: Int = Snackbar.LENGTH_SHORT
) = Snackbar.make(
    root ?: this,
    this.context.getString(messageResId),
    length
).show()

