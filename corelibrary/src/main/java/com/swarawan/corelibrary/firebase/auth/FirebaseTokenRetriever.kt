@file:JvmName("FirebaseTokenRetriever")

package com.swarawan.corelibrary.firebase.auth

import com.swarawan.corelibrary.sharedprefs.CorePreferences

/**
 * Created by rioswarawan on 2/23/18.
 */
class FirebaseTokenRetriever(private val corePreferences: CorePreferences) {

    fun getAuthToken(): String {
        return corePreferences.getString(FirebaseAuthConstant.FIREBASE_ID_TOKEN)
    }
}