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

    public void saveAccountInfo(String id, String token) {
        SharedPreferences.Editor editor =
                mContext.getSharedPreferences(ACCOUNT_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_ID, id);
        editor.putString(PREF_TOKEN, token);
        editor.apply();

        mToken = token;
    }

    public String getToken() {
        if (TextUtils.isEmpty(mToken)) {
            SharedPreferences sp = mContext.getSharedPreferences(ACCOUNT_PREFERENCE, Context.MODE_PRIVATE);
            return sp.getString(PREF_TOKEN, "");
        } else {
            return mToken;
        }
    }
}
