package com.swarawan.corelibrary.extensions

import android.content.Context
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.graphics.drawable.VectorDrawableCompat
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import org.jetbrains.anko.contentView
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Collections
import java.util.Locale

/**
 * Created by rioswarawan on 2/23/18.
 */

/**
 * Extension to simplify getString without accessing resources
 */
fun View.getString(@StringRes stringResId: Int): String = resources.getString(stringResId)

/**
 * Extension method to provide show keyboard for [View].
 */
fun View.showKeyboard() {
    val imm: InputMethodManager by lazy { context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

/**
 * Extension method to provide hide keyboard for [View].
 */
fun View.hideKeyboard() {
    val imm: InputMethodManager by lazy { context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    imm.hideSoftInputFromWindow(windowToken, 0)
}

inline fun View.showSnackbar(snackbarText: String,
                             timeLength: Int = Snackbar.LENGTH_LONG,
                             listener: Snackbar.() -> Unit = {}) =
        Snackbar.make(this, snackbarText, timeLength).apply {
            listener()
            showCompat(this.context)
        }


inline fun View.showSnackbar(@StringRes snackbarTextRes: Int,
                             timeLength: Int = Snackbar.LENGTH_LONG,
                             listener: Snackbar.() -> Unit = {}) =
        Snackbar.make(this, snackbarTextRes, timeLength).apply {
            listener()
            showCompat(this.context)
        }

fun Snackbar.showCompat(context: Context) {
    val curActivity = context.getCurrentActivity()
    if (!this.isShownOrQueued) {
        curActivity?.contentView?.viewTreeObserver?.let {
            it.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    this@showCompat.show()
                    it.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }
}

fun View.goneIf(condition: Boolean) {
    if (condition) this.visibility = View.GONE else this.visibility = View.VISIBLE
}

fun View.invisibleIf(condition: Boolean) {
    if (condition) {
        this.visibility = View.INVISIBLE
        this.isEnabled = false
    } else {
        this.visibility = View.VISIBLE
        this.isEnabled = true
    }
}

fun Window.blockTouchScreen() {
    this.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Window.unblockTouchScreen() {
    this.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun View.isViewVisible(): Boolean = this.visibility == View.VISIBLE

fun ImageView.setDrawableVectorCompat(@DrawableRes drawableId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        this.setImageDrawable(
                VectorDrawableCompat.create(this.context.resources, drawableId, this.context.theme)
        )
    } else {
        this.setImageResource(drawableId)
    }
}

fun BigDecimal.getCurrencyFormat(): String {
    return try {
        val display: BigDecimal = this.setScale(2, RoundingMode.HALF_EVEN)
        val numberFormat = NumberFormat.getInstance(Locale("id", "ID")).apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 2
        }
        "Rp ${numberFormat.format(display.toDouble())}"
    } catch (ex: Exception) {
        "Rp 0"
    }
}

fun Int.getCurrencyFormat(): String {
    return try {
        val display: BigDecimal = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN)
        val numberFormat = NumberFormat.getInstance(Locale("id", "ID")).apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 2
        }
        "Rp ${numberFormat.format(display.toDouble())}"
    } catch (ex: Exception) {
        "Rp 0"
    }
}

fun String.getCurrencyFormat(): String {
    return try {
        val display: BigDecimal = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN)
        val numberFormat = NumberFormat.getInstance(Locale("id", "ID")).apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 2
        }
        "Rp ${numberFormat.format(display.toDouble())}"
    } catch (ex: Exception) {
        "Rp 0"
    }
}