package com.alvarengadev.marketplacelist.utils.extensions

import android.view.View

fun View.goneWithoutAnimation() {
    this.visibility = View.GONE
}

fun View.visibilityWithoutAnimation() {
    this.visibility = View.VISIBLE
}