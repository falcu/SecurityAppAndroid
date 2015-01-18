package com.example.guido.securityapp.activities;

import android.app.Application;
import android.content.Context;

/**
 * Created by guido on 1/17/15.
 */
public class MyApplication extends Application{

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
