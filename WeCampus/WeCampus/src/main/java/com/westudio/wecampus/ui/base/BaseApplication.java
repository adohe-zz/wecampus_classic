package com.westudio.wecampus.ui.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;

import com.westudio.wecampus.util.AccountManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
        hasAccount = accountMgr.getUserId() != 0;
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

    public HashMap<String, String> getCategoryColors() {
        HashMap<String, String> categoryMapping = new HashMap<String, String>();
        SharedPreferences sp = getSharedPreferences("CategoryColors", MODE_PRIVATE);
        Set<String> keys = sp.getAll().keySet();
        for (String key : keys) {
            categoryMapping.put(key, sp.getString(key, "#616161"));
        }

        return categoryMapping;
    }

    public void setCategoryMapping(HashMap<String, String> map) {
        SharedPreferences.Editor editor = getSharedPreferences("CategoryColors", MODE_PRIVATE).edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            editor.putString(key, value);
        }
        editor.apply();
    }
}
