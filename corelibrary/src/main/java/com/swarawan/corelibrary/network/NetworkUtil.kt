@file:JvmName("NetworkUtil")

package com.swarawan.corelibrary.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by rioswarawan on 2/12/18.
 */
class NetworkUtil(private val context: Context) {

    private val connectivityStatus: Int
        get() {
            val cm = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            activeNetwork?.let {
                return when (it.type) {
                    ConnectivityManager.TYPE_WIFI -> TYPE_WIFI
                    ConnectivityManager.TYPE_MOBILE -> TYPE_MOBILE_DATA
                    else -> TYPE_NOT_CONNECTED
                }
            }
            return TYPE_NOT_CONNECTED
        }

    val isConnected: Boolean
        get() = connectivityStatus != TYPE_NOT_CONNECTED

    companion object {
        val TYPE_WIFI = 1
        val TYPE_MOBILE_DATA = 2
        val TYPE_NOT_CONNECTED = 0
    }
}