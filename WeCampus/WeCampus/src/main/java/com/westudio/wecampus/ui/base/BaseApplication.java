package com.westudio.wecampus.ui.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;

import com.westudio.wecampus.util.AccountManager;

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

    public DisplayMetrics displayMetrics;

    private AccountManager accountMgr;

    //The singleton application instance
    private static BaseApplication application = null;

    public static BaseApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        hasAccount = false;
        mContext = getApplicationContext();

        accountMgr = new AccountManager(mContext);
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

    public static Context getContext() {
        return mContext;
    }

    public DisplayMetrics getDisplayMetrics(Activity activity) {
        if(displayMetrics != null) {
            return displayMetrics;
        } else {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            this.displayMetrics = dm;
            return displayMetrics;
        }
    }

    public AccountManager getAccountMgr() {
        return accountMgr;
    }
}
