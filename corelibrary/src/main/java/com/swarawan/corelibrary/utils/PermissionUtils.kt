package com.swarawan.corelibrary.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.swarawan.corelibrary.extensions.showDialog

class PermissionUtils {

    companion object {

        const val REQUEST_STORAGE = 1
        const val REQUEST_CAMERA = 2
        const val REQUEST_CONTACT = 3
        const val REQUEST_RECEIVE_MESSAGE = 4
        const val REQUEST_LOCATION = 5

        val storagePermissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        val cameraPermissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
        val contactPermissions = arrayOf(Manifest.permission.READ_CONTACTS)
        val messagePermissions = arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS)
        val locationPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        fun isPermissionGranted(activity: Activity, permissions: Array<String>,
                                requestCode: Int, message: String) {
            if (checkPermissions(activity, permissions)) {
                requestPermisions(activity, permissions, requestCode, message)
            }
        }

        fun verifyPermissions(grantsResult: IntArray): Boolean {
            if (grantsResult.isEmpty()) {
                return false
            }
            grantsResult.forEach {
                if (it != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }

        fun checkPermissions(activity: Activity, grantsResult: Array<String>): Boolean {
            if (grantsResult.isEmpty()) {
                return false
            }
            grantsResult.forEach {
                if (ActivityCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }

        fun requestPermisions(activity: Activity,
                              permissions: Array<String>, requestCode: Int,
                              message: String, positiveButton: String = "Yes") {
            when {
                shouldShowRequestPermissions(activity, permissions) ->
                    ActivityCompat.requestPermissions(activity, permissions, requestCode)
                else ->
                    activity.showDialog(message, false, positiveButton) {
                        ActivityCompat.requestPermissions(activity, permissions, requestCode)
                    }
            }
        }

        private fun shouldShowRequestPermissions(activity: Activity, grantsResult: Array<String>): Boolean {
            if (grantsResult.isEmpty()) {
                return false
            }

            grantsResult.forEach {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) {
                    return false
                }
            }
            return true
        }
    }
}