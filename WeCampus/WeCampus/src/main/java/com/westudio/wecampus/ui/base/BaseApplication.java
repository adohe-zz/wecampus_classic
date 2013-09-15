package com.westudio.wecampus.ui.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.westudio.wecampus.data.DBHelper;

/**
 * Created by nankonami on 13-9-7.
 * The singleton application
 */
public class BaseApplication extends Application {

    private static Context mContext;

    //static constants
    public static final String ACCOUNT_TYPE = "com.westudio.wecampus";
    public static final String AUTHTOKEN_TYPE = "com.westudio.wecampus";

    public boolean hasAccount;

    @Override
    public void onCreate() {
        super.onCreate();

        RequestQueue queue = Volley.newRequestQueue(this);
        hasAccount = false;
        mContext = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        OpenHelperManager.releaseHelper();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getContext() {
        return mContext;
    }
}
