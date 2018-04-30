@file:JvmName("CorePreferences")

package com.swarawan.corelibrary.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.swarawan.corelibrary.utils.TextUtils

/**
 * Created by rioswarawan on 2/11/18.
 */
class CorePreferences(context: Context) {

    companion object {
        private var corePreferences: CorePreferences? = null
        private var sharedPrefs: SharedPreferences? = null

        fun getInstance(context: Context): CorePreferences {
            if (corePreferences == null) {
                corePreferences = CorePreferences(context)
            }
            return corePreferences as CorePreferences
        }
    }

    init {
        sharedPrefs = context.getSharedPreferences("Core-Prefs", Context.MODE_PRIVATE)
    }

    fun setString(key: String, value: String) {
        sharedPrefs?.let {
            it.edit().putString(key, value).apply()
        }
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPrefs?.let {
            it.edit().putBoolean(key, value).apply()
        }
    }

    fun setInt(key: String, value: Int) {
        sharedPrefs?.let {
            it.edit().putInt(key, value).apply()
        }
    }

    fun setLong(key: String, value: Long) {
        sharedPrefs?.let {
            it.edit().putLong(key, value).apply()
        }
    }

    fun setFloat(key: String, value: Float) {
        sharedPrefs?.let {
            it.edit().putFloat(key, value).apply()
        }
    }

    fun getString(key: String, defValue: String = TextUtils.BLANK): String {
        return when (sharedPrefs) {
            null -> defValue
            else -> (sharedPrefs as SharedPreferences).getString(key, defValue)
        }
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return when (sharedPrefs) {
            null -> defValue
            else -> (sharedPrefs as SharedPreferences).getBoolean(key, defValue)
        }
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        return when (sharedPrefs) {
            null -> defValue
            else -> (sharedPrefs as SharedPreferences).getInt(key, defValue)
        }
    }

    fun getLong(key: String, defValue: Long): Long {
        return when (sharedPrefs) {
            null -> defValue
            else -> (sharedPrefs as SharedPreferences).getLong(key, defValue)
        }
    }

    fun getFloat(key: String, defValue: Float = 0f): Float {
        return when (sharedPrefs) {
            null -> defValue
            else -> (sharedPrefs as SharedPreferences).getFloat(key, defValue)
        }
    }

    fun remove(key: String) {
        sharedPrefs?.let {
            it.edit().remove(key).apply()
        }
    }

    fun clear() {
        sharedPrefs?.let {
            it.edit().clear().apply()
        }
    }

}