package com.swarawan.corelibrary.extensions

import android.util.Patterns
import com.swarawan.corelibrary.utils.TextUtils
import java.text.NumberFormat

/**
 * Created by rioswarawan on 2/11/18.
 */

inline fun <reified T: Any> clazz() = T::class.java

fun String.isNumeric(): Boolean = matches("^[0-9]+\$".toRegex())

fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.convertPhoneFormat(countryCode: String): String {
    if (this.isNotEmpty()) {
        var result = this.trim().replace("\\s".toRegex(), TextUtils.BLANK)
        if (result.startsWith("0", true)) {
            result = "$countryCode${result.substring(1)}"
        }
        return result
    }
    return TextUtils.BLANK
}

fun Long.convertToCurrency(countryCurrency: String = TextUtils.BLANK): String {
    val value = NumberFormat.getNumberInstance().format(this).replace(",", ".")
    return "$countryCurrency $value"
}

fun Int.convertToCurrency(countryCurrency: String = TextUtils.BLANK): String {
    val value = NumberFormat.getNumberInstance().format(this).replace(",", ".")
    return "$countryCurrency $value"
}