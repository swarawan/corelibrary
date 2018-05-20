package com.swarawan.corelibrary.extensions

import android.view.MotionEvent
import android.widget.EditText
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

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

fun EditText.observeTextNotEmpty(): Observable<Boolean> =
        RxTextView.textChanges(this)
                .map { !this.text.isEmpty() }

fun EditText.observeTextEquals(with: String): Observable<Boolean> =
        RxTextView.textChanges(this)
                .map { with.equals(this.text.toString(), true) }

fun EditText.observeTextNotEquals(with: String): Observable<Boolean> =
        RxTextView.textChanges(this)
                .map { !with.equals(this.text.toString(), true) }

fun Int.isResponseSuccess(): Boolean = (200 == this)
fun Int.isResponseCreated(): Boolean = (201 == this)
fun Int.isResponseTokenExpired(): Boolean = (403 == this)