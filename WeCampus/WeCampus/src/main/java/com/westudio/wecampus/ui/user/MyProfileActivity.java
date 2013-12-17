package com.westudio.wecampus.ui.user;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

    public static final String NICK_NAME = "nick_name";
    public static final String REAL_NAME = "real_name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final int UPDATE_NICK_REQUEST = 1;
    public static final int UPDATE_NICK_RESULT = 2;
    public static final int UPDATE_NAME_REQUEST = 3;
    public static final int UPDATE_NAME_RESULT = 4;
    public static final int UPDATE_PHONE_REQUEST = 5;
    public static final int UPDATE_PHONE_RESULT = 6;
    public static final int UPDATE_EMAIL_REQUEST = 7;
    public static final int UPDATE_EMAIL_RESULT = 8;

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
        tvNickName.setOnClickListener(clickListener);
        tvGender = (TextView)findViewById(R.id.gender);
        tvGender.setOnClickListener(clickListener);
        tvSchool = (TextView)findViewById(R.id.school);
        tvWord = (TextView)findViewById(R.id.words);
        tvWord.setOnClickListener(clickListener);
        tvName = (TextView)findViewById(R.id.real_name);
        tvName.setOnClickListener(clickListener);
        tvPhone = (TextView)findViewById(R.id.telephone);
        tvPhone.setOnClickListener(clickListener);
        tvEmail = (TextView)findViewById(R.id.email);
        tvEmail.setOnClickListener(clickListener);
        tvBirthday = (TextView)findViewById(R.id.birthday);
        tvBirthday.setOnClickListener(clickListener);
        tvLove = (TextView)findViewById(R.id.relationship);
        tvLove.setOnClickListener(clickListener);
        tvRole = (TextView)findViewById(R.id.role);
        tvRole.setOnClickListener(clickListener);
    }

    private void updateUI() {
        tvNickName.setText(mUser.nickname);
        tvGender.setText(mUser.gender);
        tvSchool.setText(mUser.school_name);
        tvWord.setText(mUser.words);
        tvName.setText(mUser.name);
        tvPhone.setText(mUser.phone);
        tvEmail.setText(mUser.email);
        tvLove.setText(mUser.emotion);
        tvRole.setText(mUser.stage);
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nickname: {
                    Intent intent = new Intent(MyProfileActivity.this, UpdateNickActivity.class);
                    intent.putExtra(NICK_NAME, mUser.nickname);
                    startActivityForResult(intent, UPDATE_NICK_REQUEST);
                    break;
                }
                case R.id.real_name: {
                    Intent intent = new Intent(MyProfileActivity.this, UpdateNameActivity.class);
                    intent.putExtra(REAL_NAME, mUser.name);
                    startActivityForResult(intent, UPDATE_NAME_REQUEST);
                    break;
                }
                case R.id.telephone: {
                    Intent intent = new Intent(MyProfileActivity.this, UpdatePhoneActivity.class);
                    intent.putExtra(PHONE, mUser.phone);
                    startActivityForResult(intent, UPDATE_PHONE_REQUEST);
                    break;
                }
                case R.id.email: {
                    Intent intent = new Intent(MyProfileActivity.this, UpdateEmailActivity.class);
                    intent.putExtra(EMAIL, mUser.nickname);
                    startActivityForResult(intent, UPDATE_EMAIL_REQUEST);
                    break;
                }
                case R.id.school: {
                    break;
                }
                case R.id.gender: {
                    break;
                }
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)  {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
