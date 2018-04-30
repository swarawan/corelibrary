@file:JvmName("CoreLog")

package com.swarawan.corelibrary.log

import android.util.Log
import com.swarawan.corelibrary.utils.TextUtils

/**
 * Created by rioswarawan on 2/11/18.
 */
object CoreLog {

    fun e(tag: String = "corelib", t: Throwable?) {
        Log.d(tag, if (t != null) t.message else "exception is null")
    }

    fun d(tag: String = "corelib", message: String) {
        Log.d(tag, message)
    }

    fun v(tag: String = "corelib", message: String) {
        Log.d(tag, message)
    }

    fun i(tag: String = "corelib", message: String) {
        Log.d(tag, message)
    }
}