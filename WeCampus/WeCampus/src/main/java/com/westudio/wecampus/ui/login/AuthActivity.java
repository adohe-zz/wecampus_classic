package com.westudio.wecampus.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.westudio.wecampus.R;

/**
 * Created by nankonami on 13-9-18.
 * The login/register activity
 */
public class AuthActivity extends SherlockFragmentActivity {

    public static final String LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT";
    public static final String REGISTER_FRAGMENT_TAG = "REG_FRAGMENT";
    public static final String UPDATE_PROFILE_TAG = "UPDATE_PROFILE";

    public static final int PICK_SCHOOL_REQUEST = 1;
    public static final int PICK_ACADEMY_REQUEST = 2;

    private String mEmail;
    private String mPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.auth_container, LoginFragment.newInstance(null), LOGIN_FRAGMENT_TAG)
                .commit();

        setUpActionBar();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void handleLogin(final String email, final String pwd) {
        mEmail = email;
        mPwd = pwd;


    }
}
