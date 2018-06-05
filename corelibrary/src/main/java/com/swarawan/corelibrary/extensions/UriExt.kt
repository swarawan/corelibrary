package com.swarawan.corelibrary.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import com.swarawan.corelibrary.extensions.getReadPath

/**
 * Created by Rio Swarawn on 6/1/18.
 */
fun Uri.resize(context: Context?): Bitmap? {
    val contentResolver = context?.contentResolver
    try {
        var inputStream = contentResolver?.openInputStream(this)
        val bitmapOption = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeStream(inputStream, null, bitmapOption)
        inputStream?.close()

        var scale = 1
        if (bitmapOption.outHeight > 1024 || bitmapOption.outWidth > 1024) {
            scale = Math.pow(
                    2.toDouble(),
                    Math.round(Math.log(1024 / Math.max(bitmapOption.outHeight, bitmapOption.outWidth).toDouble()))
                            / Math.log(0.5)).toInt()
        }
        val bitmapOptionFinal = BitmapFactory.Options().apply {
            inSampleSize = scale
        }
        inputStream = contentResolver?.openInputStream(this)
        val bitmapFinal = BitmapFactory.decodeStream(inputStream, null, bitmapOptionFinal)
        inputStream?.close()

        return bitmapFinal
    } catch (ex: Exception) {
        return null
    }
}

fun Uri.rotateIfRequired(context: Context, path: String): Bitmap? {
    val bitmap: Bitmap? = this.resize(context)

    val exifInterface = ExifInterface(path)
    val rotation: Int = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    val rotationInDegree: Int = when (rotation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }

    return when {
        rotation != 0 -> {
            bitmap?.let {
                val matrix = Matrix()
                matrix.postRotate(rotationInDegree.toFloat())
                val bitmapFinal = Bitmap.createBitmap(bitmap, 0, 0, it.width, it.height, matrix, true)
                it.recycle()
                bitmapFinal
            }
        }
        else -> bitmap
    }
}