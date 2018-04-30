@file:JvmName("CoreFragment")

package com.swarawan.corelibrary.base

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.View

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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
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
}