package com.westudio.wecampus.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.WeCampusApi;

/**
 * Created by nankonami on 13-9-18.
 * The login/register activity
 */
public class AuthActivity extends SherlockFragmentActivity {

    public static final String LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT";
    public static final String REGISTER_FRAGMENT_TAG = "REG_FRAGMENT";
    public static final String UPDATE_PROFILE_TAG = "UPDATE_PROFILE";

    public static final int PICK_SCHOOL_REQUEST = 1;
    public static final int PICK_GENDER_REQUEST = 2;
    public static final int PICK_SCHOOL_RESULT = 3;
    public static final int PICK_GENDER_RESULT = 4;
    public static final String PICK_SCHOOL_NAME = "school_name";
    public static final String PICK_SCHOOL_ID = "school_id";
    public static final String PICK_GENDER = "gender";

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
