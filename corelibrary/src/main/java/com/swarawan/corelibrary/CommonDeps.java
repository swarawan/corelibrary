package com.swarawan.corelibrary;

import com.swarawan.corelibrary.base.CoreActivity;
import com.swarawan.corelibrary.firebase.iid.FirebaseInstanceIdTokenService;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

/**
 * Created by rioswarawan on 3/28/18.
 */

@Singleton
public interface CommonDeps {

    EventBus getEventBus();

    void inject(CoreActivity coreActivity);

    void inject(FirebaseInstanceIdTokenService firebaseInstanceIdTokenService);
}
