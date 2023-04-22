package com.alvarengadev.marketplacelist.utils.extensions

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.alvarengadev.marketplacelist.utils.constants.PREFERENCES_MARKETPLACE_LIST

val Context.preferences
    get() = getSharedPreferences()

fun Context.getSharedPreferences(pathSharedPreference: String? = null): SharedPreferences =
    getSharedPreferences(pathSharedPreference ?: PREFERENCES_MARKETPLACE_LIST, Context.MODE_PRIVATE)

fun Context.resColor(@ColorRes id: Int) =
    ContextCompat.getColor(this, id)

fun Context.resDrawable(@ColorRes id: Int) =
    ContextCompat.getDrawable(this, id)

fun Context.longToast(@StringRes id: Int) =
    Toast.makeText(this, getString(id), Toast.LENGTH_LONG).show()

fun Context.shortToast(@StringRes id: Int) =
    Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show()

fun Context.longToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.shortToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
