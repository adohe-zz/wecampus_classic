package com.westudio.wecampus.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by martian on 13-11-9.
 */
public class AccountManager {
    private static final String ACCOUNT_PREFERENCE = "user_account_config";
    private static final String PREF_ID = "id";
    private static final String PREF_TOKEN = "token";

    private Context mContext;
    private String mToken = "";

    public AccountManager(Context c) {
        mContext = c;
        SharedPreferences sp = mContext.getSharedPreferences(ACCOUNT_PREFERENCE, Context.MODE_PRIVATE);
        mToken = sp.getString(PREF_TOKEN, "");
    }

    public void saveAccountInfo(int id, String token) {
        SharedPreferences.Editor editor =
                mContext.getSharedPreferences(ACCOUNT_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putInt(PREF_ID, id);
        editor.putString(PREF_TOKEN, token);
        editor.apply();

        mToken = token;
    }

    public void clearAccountInfo() {
        SharedPreferences.Editor editor =
                mContext.getSharedPreferences(ACCOUNT_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putInt(PREF_ID, 0);
        editor.putString(PREF_TOKEN, "");
        editor.apply();
    }

    public String getToken() {
        if (TextUtils.isEmpty(mToken)) {
            SharedPreferences sp = mContext.getSharedPreferences(ACCOUNT_PREFERENCE, Context.MODE_PRIVATE);
            return sp.getString(PREF_TOKEN, "");
        } else {
            return mToken;
        }
    }

    public int getUserId() {
        SharedPreferences sp = mContext.getSharedPreferences(ACCOUNT_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getInt(PREF_ID, 0);
    }
}
