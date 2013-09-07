package com.westudio.wecampus.ui.base;

import android.app.Application;
import android.content.res.Configuration;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.westudio.wecampus.data.DBHelper;

/**
 * Created by nankonami on 13-9-7.
 */
public class BaseApplication extends Application {

    //Singleton
    private static  BaseApplication application = null;

    private DBHelper dbHelper;

    //static constants
    public static String ACCOUNT_TYPE = "com.westudio.wecampus";

    public static BaseApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }
}
