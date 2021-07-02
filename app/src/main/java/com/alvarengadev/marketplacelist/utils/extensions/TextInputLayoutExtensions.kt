package com.alvarengadev.marketplacelist.utils.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.dismiss() {
    this.error = null
    this.isErrorEnabled = false
}