package com.westudio.wecampus.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.westudio.wecampus.util.Authenticator;

/**
 * Created by nankonami on 13-9-7.
 * Authentication service class
 */
public class AuthenticationService extends Service {

    private Authenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
