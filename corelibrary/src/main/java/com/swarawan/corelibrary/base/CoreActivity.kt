@file:JvmName("CoreActivity")

package com.swarawan.corelibrary.base

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.swarawan.corelibrary.extensions.clazz
import com.swarawan.corelibrary.CommonDepsProvider
import com.swarawan.corelibrary.utils.TextUtils

/**
 * Created by rioswarawan on 2/12/18.
 */
abstract class CoreActivity : OptimizedAppCompatActivity() {

    private val TAG = clazz<CoreActivity>()

    companion object {
        val UNUSED_ID = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        application.registerActivityLifecycleCallbacks(CoreActivityLifecyclesCallback(this@CoreActivity))
        super.onCreate(savedInstanceState)
        (application as CommonDepsProvider).getCommonDeps().inject(this@CoreActivity)

        setLayout()
        initInjection()
        onViewReady(savedInstanceState)
    }

    protected abstract fun setLayout()

    protected abstract fun initInjection()

    protected abstract fun onViewReady(savedInstanceState: Bundle?)

    protected open fun getTransparentBackgroundDialog(): Dialog {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    protected open fun setupStatusBar(statusView: View) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight())
            statusView.layoutParams = params
            statusView.visibility = View.VISIBLE
        } else {
            statusView.visibility = View.GONE
        }
    }

    private fun getStatusBarHeight(): Int {
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) resources.getDimensionPixelOffset(resId) else 0
    }

    protected open fun hideSystemBarVisibility() {
        window?.let {
            it.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                it.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }
}