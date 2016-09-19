package com.xdkj.campus.menu.base;

import android.app.Application;

/**
 * Created by xdkj on 2016/9/18.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}