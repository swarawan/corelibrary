@file:JvmName("CoreActivityLifecyclesCallback")

package com.swarawan.corelibrary.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.swarawan.corelibrary.log.CoreLog

/**
 * Created by rioswarawan on 2/12/18.
 */
class CoreActivityLifecyclesCallback(private val activity: Activity) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (this.activity == activity) {
            CoreLog.i(activity.localClassName, "onCreate(${savedInstanceState.toString()})")
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (this.activity == activity) {
            CoreLog.i(activity.localClassName, "onStart()")
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (this.activity == activity) {
            CoreLog.i(activity.localClassName, "onResume()")
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
        if (this.activity == activity) {
            CoreLog.i(activity.localClassName, "onSaveInstanceState(${outState.toString()})")
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (this.activity == activity) {
            CoreLog.i(activity.localClassName, "onPause()")
        }
    }

    override fun onActivityStopped(activity: Activity) {
        if (this.activity == activity) {
            CoreLog.i(activity.localClassName, "onPause()")
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (this.activity == activity) {
            CoreLog.i(activity.localClassName, "onDestroy()")
            activity.application.unregisterActivityLifecycleCallbacks(this)
        }
    }
}