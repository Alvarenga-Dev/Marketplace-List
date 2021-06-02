package com.alvarengadev.marketplacelist.utils.extensions

import android.content.Context
import android.content.SharedPreferences

val Context.myAppPreferences: SharedPreferences
    get() = getSharedPreferences(
        "OPA",
        Context.MODE_PRIVATE
    )

@Suppress("UNCHECKED_CAST")
inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    when (T::class) {
        Boolean::class -> return this.getBoolean(key, defaultValue as Boolean) as T
        Float::class -> return this.getFloat(key, defaultValue as Float) as T
        Int::class -> return this.getInt(key, defaultValue as Int) as T
        Long::class -> return this.getLong(key, defaultValue as Long) as T
        String::class -> return this.getString(key, defaultValue as String) as T
    }
    return defaultValue
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> SharedPreferences.put(key: String, defaultValue: T) {
    val editor = this.edit()
    when (T::class) {
        Boolean::class -> editor.putBoolean(key, defaultValue as Boolean) as T
        Float::class -> editor.putFloat(key, defaultValue as Float) as T
        Int::class -> editor.putInt(key, defaultValue as Int) as T
        Long::class -> editor.putLong(key, defaultValue as Long) as T
        String::class -> editor.putString(key, defaultValue as String) as T
    }
    editor.apply()
}