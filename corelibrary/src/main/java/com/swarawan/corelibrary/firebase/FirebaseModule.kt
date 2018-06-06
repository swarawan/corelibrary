@file:JvmName("FirebaseModule")

package com.swarawan.corelibrary.firebase

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.swarawan.corelibrary.R
import com.swarawan.corelibrary.firebase.auth.FirebaseAuthConfigService
import com.swarawan.corelibrary.firebase.iid.FirebaseTokenRetriever
import com.swarawan.corelibrary.firebase.config.FirebaseRemoteConfigService
import com.swarawan.corelibrary.firebase.messaging.NotificationHelper
import com.swarawan.corelibrary.sharedprefs.CorePreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rioswarawan on 2/28/18.
 */
@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun providesGoogleSignInOptions(tokenRetriever: FirebaseTokenRetriever): GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(tokenRetriever.getAuthToken())
                    .requestEmail()
                    .requestId()
                    .requestProfile()
                    .build()

    @Provides
    @Singleton
    fun providesGoogleSignInClient(context: Context, options: GoogleSignInOptions): GoogleSignInClient =
            GoogleSignIn.getClient(context, options)

    @Provides
    @Singleton
    fun provideGoogleAccount(context: Context): GoogleSignInAccount? =
            GoogleSignIn.getLastSignedInAccount(context)

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth =
            FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseConfigService(): FirebaseAuthConfigService =
            FirebaseAuthConfigService()

    @Provides
    @Singleton
    fun providesFirebaseTokenRetriever(corePreferences: CorePreferences): FirebaseTokenRetriever =
            FirebaseTokenRetriever(corePreferences)

    @Provides
    @Singleton
    fun providesFirebaseRemoteConfig(): FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance().apply {
        setDefaults(R.xml.remote_config_default)
    }

    @Provides
    @Singleton
    fun providesFirebaseRemoteConfigService(context: Context, firebaseRemoteConfig: FirebaseRemoteConfig): FirebaseRemoteConfigService {
        return FirebaseRemoteConfigService(context, firebaseRemoteConfig, context.resources.getInteger(R.integer.remote_config_cache_expiry_time))
    }

    @Provides
    @Singleton
    fun providesFirebaseRealtimeDatabase(): FirebaseDatabase =
            FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseRealtimeDatabaseReference(firebaseDatabase: FirebaseDatabase): DatabaseReference =
            firebaseDatabase.reference

    @Provides
    @Singleton
    fun providesNotificationHelper(context: Context): NotificationHelper =
            NotificationHelper(context)
}