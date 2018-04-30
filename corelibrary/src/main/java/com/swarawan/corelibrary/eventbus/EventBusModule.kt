@file:JvmName("EventBusModule")

package com.swarawan.corelibrary.eventbus

import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

/**
 * Created by rioswarawan on 2/11/18.
 */

@Module
class EventBusModule {

    @Provides
    @Singleton
    fun providesEventBus(): EventBus = EventBus.getDefault()
}