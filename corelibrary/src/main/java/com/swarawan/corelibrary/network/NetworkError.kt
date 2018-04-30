@file:JvmName("NetworkError")

package com.swarawan.corelibrary.network

import com.swarawan.corelibrary.utils.TextUtils
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

/**
 * Created by rioswarawan on 2/12/18.
 */
open class NetworkError(private val error: Throwable) : Throwable() {

    val errorMessage: String?
        get() = error.message

    val isNetworkError: Boolean
        get() = error is IOException

    val isHttpError: Boolean
        get() = error is HttpException

    val isAuthFailure: Boolean
        get() = isHttpError && (error as HttpException).code() == HTTP_UNAUTHORIZED

    val isAuthForbidden: Boolean
        get() = isHttpError && (error as HttpException).code() == HTTP_FORBIDDEN

    val httpErrorCode: String
        get() = if (isHttpError) (error as HttpException).code().toString() else TextUtils.BLANK
}