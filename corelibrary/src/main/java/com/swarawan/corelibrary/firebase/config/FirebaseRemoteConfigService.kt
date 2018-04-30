@file:JvmName("FirebaseRemoteConfigService")

package com.swarawan.corelibrary.firebase.config

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS
import com.swarawan.corelibrary.log.CoreLog

/**
 * Created by rioswarawan on 2/25/18.
 */

class FirebaseRemoteConfigService(val context: Context,
                                  val firebaseRemoteConfig: FirebaseRemoteConfig,
                                  val cacheTime: Int) {

    companion object {
        const val TAG = "FirebaseRemoteConfig"
    }

    private var isInitialized = false

    init {
        val cacheTime = when (firebaseRemoteConfig.info.lastFetchStatus) {
            LAST_FETCH_STATUS_SUCCESS -> {
                CoreLog.d(TAG, "remote config fetch was SUCCESS")
                cacheTime
            }
            else -> {
                CoreLog.d(TAG, "remote config fetch was FAILURE")
                0
            }
        }
        fetchFirebaseRemoteConfiguration(cacheTime)
    }

    private fun fetchFirebaseRemoteConfiguration(cacheTime: Int) {
        firebaseRemoteConfig.fetch(cacheTime.toLong())
                .addOnSuccessListener {
                    CoreLog.d(TAG, "remote config fetched success")
                    isInitialized = true
                    val result = firebaseRemoteConfig.activateFetched()
                    CoreLog.d(TAG, if (result) "remote config activate fetched: SUCCESS"
                    else "remote config activate fetched: FAILURE")
                }
                .addOnFailureListener {
                    isInitialized = true
                    CoreLog.d(TAG, "remote config fetched failed")
                }
    }

    fun getString(key: String, defaultValue: String = ""): String {
        val value = firebaseRemoteConfig.getValue(key)
        return if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) defaultValue else value.asString()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        val value = firebaseRemoteConfig.getValue(key)
        return if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) defaultValue else value.asBoolean()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        val value = firebaseRemoteConfig.getValue(key)
        return if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) defaultValue else value.asLong().toInt()
    }

    fun getLong(key: String, defaultValue: Long = 0): Long {
        val value = firebaseRemoteConfig.getValue(key)
        return if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) defaultValue else value.asLong()
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        val value = firebaseRemoteConfig.getValue(key)
        return if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) defaultValue else value.asDouble()
    }

    fun hasConfig(key: String): Boolean {
        val value = firebaseRemoteConfig.getValue(key)
        return value.source != FirebaseRemoteConfig.VALUE_SOURCE_STATIC
    }
}