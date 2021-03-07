package com.rutvik.apps.skreamsos.utils

import android.content.Context
import android.content.SharedPreferences
import com.rutvik.apps.skreamsos.utils.constants.PrefConstants
import javax.inject.Inject

class SharedPreferenceHelper @Inject constructor() {

    companion object {
        const val TAG = "SharedPreferencesHelper"
    }

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences =
                context.getSharedPreferences(PrefConstants.PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun readString(key: String): String? {
        return sharedPreferences?.getString(key, "")
    }

    fun readBoolean(key: String): Boolean? {
        return sharedPreferences!!.getBoolean(key, false)
    }

    fun write(key: String, value: Boolean) {
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun write(key: String, value: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

}