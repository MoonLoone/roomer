package com.example.roomer.utils

import android.content.Context

class SpManager {
    fun setSharedPreference(context: Context, key: Sp, value: String?) {
        val sharedPref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.putString(key.toString(), value)
        edit.apply()
    }

    fun getSharedPreference(context: Context, key: Sp, defaultValue: String?): String? {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            .getString(key.toString(), defaultValue)
    }

    fun clearSharedPreference(context: Context) {
        val sharedPref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.clear()
        edit.apply()
    }

    fun removeSharedPreference(context: Context, key: Sp) {
        val sharedPref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.remove(key.toString())
        edit.apply()
    }
    enum class Sp {
        USERNAME,
        EMAIL,
        PASSWORD,
        TOKEN,
        ISSIGNUP,
    }
}
