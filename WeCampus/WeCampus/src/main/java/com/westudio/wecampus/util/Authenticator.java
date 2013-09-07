package com.westudio.wecampus.util;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseApplication;

/**
 * Created by nankonami on 13-9-7.
 */
public class Authenticator extends AbstractAccountAuthenticator {

    private Context mContext;

    public Authenticator(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    /**
     * This method will be called when we add an account to
     * account manager
     * @param accountAuthenticatorResponse
     * @param s
     * @param s2
     * @param strings
     * @param bundle
     * @return
     * @throws NetworkErrorException
     */
    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String s, String s2, String[] strings, Bundle bundle)
            throws NetworkErrorException {

        //First check whether the account exits
        final AccountManager accountManager = AccountManager.get(mContext);
        if(accountManager.getAccountsByType(BaseApplication.ACCOUNT_TYPE).length == 0) {

        } else {
            //The same type account exits
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Looper.loop();
                }
            }).start();
            return  null;
        }
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle)
            throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle)
            throws NetworkErrorException {

        //If the caller request an account type we don't support
        //then return an error
        if(!s.equals(BaseApplication.ACCOUNT_TYPE)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, mContext.getResources().getString(R.string.authenticate_error));
            return bundle;
        }

        //

        return null;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle)
            throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings)
            throws NetworkErrorException {
        // This call is used to query whether the Authenticator supports
        // specific features. We don't expect to get called, so we always
        // return false (no) for any queries.
        final Bundle bundle = new Bundle();
        bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
        return bundle;
    }
}
