package com.westudio.wecampus.ui.splash;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseApplication;

import java.io.IOException;

/**
 * Created by nankonami on 13-9-11.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);

        jumpToMainActivity();
    }

    private void jumpToMainActivity() {
        final AccountManager accountManager = AccountManager.get(this);
        final Account[] accounts = accountManager.getAccountsByType(BaseApplication.ACCOUNT_TYPE);

        if(accounts.length > 0) {
            accountManager.getAuthToken(accounts[0], BaseApplication.AUTHTOKEN_TYPE, null, null,
                    new AccountManagerCallback<Bundle>() {
                        @Override
                        public void run(AccountManagerFuture<Bundle> result) {
                            try {
                                Bundle bundle = result.getResult();
                                BaseApplication application = BaseApplication.getInstance();
                                application.hasAccount = true;

                                finish();
                            } catch (OperationCanceledException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (AuthenticatorException e) {
                                e.printStackTrace();
                            }
                        }
                    }, null);
        } else {

        }
    }
}
