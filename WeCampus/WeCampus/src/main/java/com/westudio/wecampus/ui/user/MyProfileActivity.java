package com.westudio.wecampus.ui.user;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.UserDataHelper;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.BaseDetailActivity;
import com.westudio.wecampus.util.Utility;

/**
 * Created by Martian on 13-10-20.
 */
public class MyProfileActivity extends BaseDetailActivity {

    private TextView tvNickName;
    private TextView tvGender;
    private TextView tvSchool;
    private TextView tvWord;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvBirthday;
    private TextView tvLove;
    private TextView tvRole;

    private UserDataHelper mDataHelper;
    private User mUser;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_profile);
        registerSwipeToCloseListener(findViewById(R.id.content_frame));

        updateActionBar();
        initWidget();
        mDataHelper = new UserDataHelper(this);
        id = BaseApplication.getInstance().getAccountMgr().getUserId();

        refreshUserFromDb();
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initWidget() {
        tvNickName = (TextView)findViewById(R.id.nickname);
        tvGender = (TextView)findViewById(R.id.gender);
        tvSchool = (TextView)findViewById(R.id.school);
        tvWord = (TextView)findViewById(R.id.words);
        tvName = (TextView)findViewById(R.id.real_name);
        tvPhone = (TextView)findViewById(R.id.telephone);
        tvEmail = (TextView)findViewById(R.id.email);
        tvBirthday = (TextView)findViewById(R.id.birthday);
        tvLove = (TextView)findViewById(R.id.relationship);
        tvRole = (TextView)findViewById(R.id.role);
    }

    private void updateUI() {
        tvNickName.setText(mUser.nickname);
        tvGender.setText(mUser.gender);
        tvSchool.setText(mUser.school.getName());
    }

    private void refreshUserFromDb() {
        if(id != 0) {
            Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                @Override
                protected void onPostExecute(Object o) {
                    if(mUser != null) {
                        updateUI();
                    }
                }

                @Override
                protected Object doInBackground(Object... params) {
                    mUser = mDataHelper.query(id);
                    return null;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)  {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
