@file:JvmName("OptimizedAppCompatActivity")

package com.swarawan.corelibrary.base

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.LayoutRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView

/**
 * Created by rioswarawan on 2/12/18.
 */
abstract class OptimizedAppCompatActivity : AppCompatActivity() {

    private var rootView: View? = null
    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        rootView = view
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        rootView = LayoutInflater.from(this).inflate(layoutResID, null)
        setContentView(rootView)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        rootView = view
    }

    fun postDelayed(runnable: Runnable, delayMillis: Long): Boolean {
        return handler?.postDelayed(runnable, delayMillis) ?: false
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacksAndMessages(null)
        unbindDrawables(rootView)
    }

    companion object {

        fun unbindDrawables(view: View?) {
            view?.let {
                it.background?.let { bg ->
                    {
                        bg.callback = null
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            val parent = it.parent as View?
                            if (null == parent || parent !is SwipeRefreshLayout) {
                                it.background = null
                            }
                        }
                    }
                }
                when (it) {
                    is ImageView -> {
                        it.drawable?.let { _ ->
                            with(it) {
                                setImageDrawable(null)
                                setImageBitmap(null)
                            }
                        }
                    }
                    is RecyclerView -> {
                        it.adapter?.let { rvAdapter ->
                            for (i in 0 until rvAdapter.itemCount) {
                                val holder = it.findViewHolderForAdapterPosition(i)
                                holder?.let { curHolder ->
                                    unbindDrawables(curHolder.itemView)
                                }
                            }
                        }
                    }
                    is ViewGroup -> {
                        for (i in 0 until it.childCount) {
                            unbindDrawables(it.getChildAt(i))
                        }
                        if (it !is AdapterView<*>) it.removeAllViews() else {
                        }

                    }
                    else -> {
                    }
                }
            }
        }
    }
}