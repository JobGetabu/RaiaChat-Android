package com.raiachat.app;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class RaiaChat extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this);
        Crashlytics.getInstance().crash();
    }
}
