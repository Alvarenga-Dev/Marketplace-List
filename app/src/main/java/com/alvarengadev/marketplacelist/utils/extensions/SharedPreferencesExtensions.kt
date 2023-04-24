package com.alvarengadev.marketplacelist.utils.extensions

import android.content.SharedPreferences
import androidx.core.content.edit
import com.alvarengadev.marketplacelist.utils.SharedPreferenceLiveData

@Suppress("UNCHECKED_CAST")
inline fun <reified T> SharedPreferences.save(key: String, value: T) {
    edit {
        remove(key)
        when (value) {
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            is Int -> putInt(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            is Set<*> -> putStringSet(key, value as Set<String>)
        }
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    return when (defaultValue) {
        is String -> getString(key, defaultValue) as T
        is Long -> getLong(key, defaultValue) as T
        is Int -> getInt(key, defaultValue) as T
        is Float -> getFloat(key, defaultValue) as T
        is Boolean -> getBoolean(key, defaultValue) as T
        is Set<*> -> getStringSet(key, defaultValue as Set<String>) as T
        else -> throw IllegalArgumentException("generic type not handled")
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> SharedPreferences.liveData(key: String, default: T): SharedPreferenceLiveData<T> =
    object : SharedPreferenceLiveData<T>(this, key, default) {
        override fun getValueFromPreferences(key: String, defValue: T): T =
            when (default) {
                is String -> getString(key, default) as T
                is Int -> getInt(key, default) as T
                is Long -> getLong(key, default) as T
                is Boolean -> getBoolean(key, default) as T
                is Float -> getFloat(key, default) as T
                is Set<*> -> getStringSet(key, default as Set<String>) as T
                else -> throw IllegalArgumentException("generic type not handled")
            }
    }

fun SharedPreferences.remove(key: String) {
    edit { remove(key) }
}

fun SharedPreferences.removePath() {
    edit { clear() }
}
