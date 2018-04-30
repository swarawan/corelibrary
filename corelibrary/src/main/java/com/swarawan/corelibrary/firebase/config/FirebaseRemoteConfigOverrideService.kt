@file:JvmName("FirebaseRemoteConfigOverrideService")

package com.swarawan.corelibrary.firebase.config

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.swarawan.corelibrary.sharedprefs.CorePreferences
import com.swarawan.corelibrary.utils.DeviceUtils

/**
 * Created by rioswarawan on 3/4/18.
 */
class FirebaseRemoteConfigOverrideService : Service() {

    companion object {
        val STAGING_PACKAGE_NAME = "staging"
    }

    private val corePreference = CorePreferences.getInstance(this)

    private fun setData(intent: Intent) {
        if (DeviceUtils.getPackageName(this).contains(STAGING_PACKAGE_NAME, true)) {
            return
        }

        intent.data?.let {
            try {
                val parameterNames = it.queryParameterNames
                val iterator = parameterNames.iterator()
                while (iterator.hasNext()) {
                    val key = iterator.next()
                    val value = it.getQueryParameter(key)
                    corePreference.setString(key, value)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        intent?.let {
            setData(it)
        }
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.let { setData(it) }
        return START_NOT_STICKY
    }
}