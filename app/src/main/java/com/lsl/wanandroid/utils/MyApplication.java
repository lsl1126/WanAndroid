package com.lsl.wanandroid.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by lsl on 2020/7/15/015.
 */
public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
