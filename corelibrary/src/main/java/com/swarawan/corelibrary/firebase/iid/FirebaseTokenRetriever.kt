@file:JvmName("FirebaseTokenRetriever")

package com.swarawan.corelibrary.firebase.iid

import com.swarawan.corelibrary.firebase.FirebaseConstant
import com.swarawan.corelibrary.sharedprefs.CorePreferences

/**
 * Created by rioswarawan on 2/23/18.
 */
class FirebaseTokenRetriever(private val corePreferences: CorePreferences) {

    fun getAuthToken(): String {
        return corePreferences.getString(FirebaseConstant.FIREBASE_ID_TOKEN)
    }
}