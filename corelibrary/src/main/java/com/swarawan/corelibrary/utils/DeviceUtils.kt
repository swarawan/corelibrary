@file:JvmName("DeviceUtil")

package com.swarawan.corelibrary.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.BatteryManager
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.telephony.TelephonyManager
import android.util.Log
import com.swarawan.corelibrary.log.CoreLog
import java.net.NetworkInterface
import java.util.*

/**
 * Created by rioswarawan on 3/4/18.
 */
class DeviceUtils {

    companion object {

        @Suppress("deprecation")
        fun getLanguageCodeFromDevice(): String {
            return Resources.getSystem().configuration.locale.language
        }

        fun getIpAddress(): String {
            try {
                val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
                interfaces
                        .map { Collections.list(it.inetAddresses) }
                        .flatMap { it }
                        .filterNot { it.isLoopbackAddress }
                        .forEach { return it.hostAddress }
            } catch (ex: Exception) {
                CoreLog.e("Core Lib", ex)
            }

            return TextUtils.BLANK
        }

        fun getAppVersionName(context: Context): String {
            return try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                CoreLog.e("Core Lib", e)
                TextUtils.BLANK
            }

        }

        fun getPackageName(context: Context): String {
            return try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.packageName
            } catch (e: PackageManager.NameNotFoundException) {
                CoreLog.e("Core Lib", e)
                TextUtils.BLANK
            }

        }

        @SuppressLint("HardwareIds")
        @Suppress("deprecation")
        fun getDeviceIMEI(context: Context): String {
            try {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    return tm.deviceId
                }
            } catch (e: Exception) {
                CoreLog.e("Core Lib", e)
                TextUtils.BLANK
            }

            return TextUtils.BLANK
        }

        @SuppressLint("HardwareIds")
        fun getUniqueId(context: Context): String {
            return try {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            } catch (e: Exception) {
                CoreLog.e("Core Lib", e)
                TextUtils.NA
            }

        }

        fun getPlayServicesVersion(context: Context): String {
            return try {
                val versionCode = context.packageManager.getPackageInfo("com.google.android.gms", 0).versionCode
                Log.d("Core Lib", "Google Play Services version $versionCode installed")
                Integer.toString(versionCode)
            } catch (e: PackageManager.NameNotFoundException) {
                CoreLog.e("Core Lib", e)
                "-1"
            }

        }

        fun getBatteryPercentage(context: Context): Int {
            val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = context.registerReceiver(null, intentFilter)
            val level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            return (level / scale.toFloat() * 100).toInt()
        }
    }
}
