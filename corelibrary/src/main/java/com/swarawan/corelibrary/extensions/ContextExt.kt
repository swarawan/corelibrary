package com.swarawan.corelibrary.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.database.Cursor
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.content.res.AppCompatResources
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.swarawan.corelibrary.R
import com.swarawan.corelibrary.log.CoreLog
import com.swarawan.corelibrary.utils.TextUtils
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by rioswarawan on 2/26/18.
 */

@Suppress("DEPRECATION")
fun Context.getColorCompat(@ColorRes colorId: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.getColor(this, colorId)
        } else this.resources.getColor(colorId)


fun Context.getDrawableCompat(@DrawableRes drawableId: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.resources.getDrawable(drawableId, null)
        } else AppCompatResources.getDrawable(this, drawableId)

fun Context.hideKeyboard(view: View) {
    val inputMethodManager: InputMethodManager by lazy {
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    view.postDelayed({
        val imm: InputMethodManager by lazy {
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }, 100)
}

fun Context.showKeyboardForce(view: View) {
    val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_FORCED, 0)
}

fun Context.getVersionCode(): String {
    return try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName
    } catch (ex: PackageManager.NameNotFoundException) {
        TextUtils.BLANK
    }
}

fun Context.getCurrentActivity(): Activity? {
    var curCtx = this
    while (curCtx is ContextWrapper) {
        if (curCtx is Activity) {
            return curCtx
        }
        curCtx = curCtx.baseContext
    }
    return null
}

fun Context.isLocationServiceEnabled(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        try {
            val locationMode = Settings.Secure.getInt(this.contentResolver, Settings.Secure.LOCATION_MODE)
            return locationMode > Settings.Secure.LOCATION_MODE_OFF
        } catch (e: Throwable) {
            CoreLog.e("isLocationServiceEnabled", e)
        }
    } else {
        val locationMode = Settings.Secure.getString(this.contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
        return locationMode.isNotEmpty()
    }
    return false
}

fun Context.getProviderEnabled(): String {
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return when {
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
        else -> TextUtils.BLANK
    }
}

fun Activity.showDialog(title: String, message: String, cancelable: Boolean = false,
                        positiveButton: String, action: () -> Unit = {}) {

    val dialogBuilder = AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setCancelable(cancelable)
        setPositiveButton(positiveButton) { dialog, _ ->
            action()
            dialog.dismiss()
        }
    }
    dialogBuilder.create().show()
}

fun Activity.showDialog(message: String, cancelable: Boolean = false,
                        positiveButton: String, action: () -> Unit = {}) {

    val dialogBuilder = AlertDialog.Builder(this).apply {
        setMessage(message)
        setCancelable(cancelable)
        setPositiveButton(positiveButton) { dialog, _ ->
            action()
            dialog.dismiss()
        }
    }
    dialogBuilder.create().show()
}

fun Activity.showDialog(message: String, cancelable: Boolean = false,
                        positiveButton: String, positiveAction: () -> Unit = {},
                        negativeButton: String, negativeAction: () -> Unit = {}) {

    val dialogBuilder = AlertDialog.Builder(this).apply {
        setMessage(message)
        setCancelable(cancelable)
        setPositiveButton(positiveButton) { dialog, _ ->
            positiveAction()
            dialog.dismiss()
        }
        setNegativeButton(negativeButton) { dialog, _ ->
            negativeAction()
            dialog.dismiss()
        }
    }
    dialogBuilder.create().show()
}

fun Uri.getReadPath(context: Context): String {
    val realPath: String
    val cursor: Cursor = context.contentResolver.query(this, null, null, null, null)
    when (cursor) {
        null -> realPath = this.path
        else -> {
            cursor.moveToFirst()
            val id: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            realPath = cursor.getString(id)
            cursor.close()
        }
    }
    return realPath
}

@SuppressLint("SimpleDateFormat")
fun Date.getCurrentDateString(context: Context, defaultFormat: String = "dd-MM-yyyy"): String {
    val dateFormat = SimpleDateFormat(defaultFormat)
    return dateFormat.format(this)
}

fun String.fromHtml(): String = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
    else ->
        Html.fromHtml(this).toString()
}

fun Context.isStorageReadWriteable(): Boolean =
        Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED, true)

fun Context.readFileFromAsset(path: String): String {
    val stringBuilder = StringBuilder()
    try {
        val reader = BufferedReader(
                InputStreamReader(this.assets.open(path, AssetManager.ACCESS_BUFFER), "UTF-8"))
        var line = reader.readLine()
        while (null != line) {
            stringBuilder.append(line)
            line = reader.readLine()
        }
        reader.close()
    } catch (ex: Exception) {
        return TextUtils.BLANK
    }
    return stringBuilder.toString()
}