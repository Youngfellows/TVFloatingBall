package com.speex.floating.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.speex.floating.service.FloatWindowService;

public class App extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        startService();
    }
    private void startService() {
        Intent intent = new Intent();
        intent.setClass(this, FloatWindowService.class);
        startService(intent);
    }
}
