@file:JvmName("CoreFragment")

package com.swarawan.corelibrary.base

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * Created by rioswarawan on 2/12/18.
 */
open class CoreFragment : Fragment() {

    private var rootView: View? = null
    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        OptimizedAppCompatActivity.unbindDrawables(rootView)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacksAndMessages(null)
    }

    fun postDelayed(runnable: Runnable, delayMillis: Long): Boolean {
        return handler?.postDelayed(runnable, delayMillis) ?: false
    }

    protected open fun getTransparentBackgroundDialog(): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }
}