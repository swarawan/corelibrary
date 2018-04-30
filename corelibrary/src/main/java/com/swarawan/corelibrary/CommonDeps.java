package com.swarawan.corelibrary;

import com.swarawan.corelibrary.base.CoreActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;

/**
 * Created by rioswarawan on 3/28/18.
 */

@Singleton
public interface CommonDeps {

    @Named("glideNonCacheOkHttpClient")
    OkHttpClient glideNonCacheOkHttpClient();

    EventBus getEventBus();

    void inject(CoreActivity coreActivity);
}
