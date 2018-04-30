@file:JvmName("CorePreferencesModule")

package com.swarawan.corelibrary.sharedprefs

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rioswarawan on 2/11/18.
 */

@Module
class CorePreferencesModule {

    @Provides
    @Singleton
    fun providesCorePrefs(context: Context) = CorePreferences.getInstance(context)
}