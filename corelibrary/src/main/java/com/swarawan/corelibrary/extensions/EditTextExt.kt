package com.swarawan.corelibrary.extensions

import android.view.MotionEvent
import android.widget.EditText

/**
 * Created by rioswarawan on 3/2/18.
 */

inline fun EditText.rightCompoundListener(crossinline func: () -> Unit) {
    this.setOnTouchListener { _, event ->
        return@setOnTouchListener when {
            event.action == MotionEvent.ACTION_UP -> {
                this.compoundDrawables[2]?.let {
                    if (event.rawX >= (this.right - it.bounds.width())) {
                        func()
                        true
                    } else false
                }
                false
            }
            else -> false
        }
    }
}

fun EditText.isEmpty(): Boolean {
    return this.length() == 0
}

fun EditText.isNotEmpty(): Boolean {
    return this.length() > 0
}