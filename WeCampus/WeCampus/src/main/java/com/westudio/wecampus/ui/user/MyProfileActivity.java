package com.westudio.wecampus.ui.user;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.UserDataHelper;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.BaseDetailActivity;
import com.westudio.wecampus.ui.login.AuthActivity;
import com.westudio.wecampus.ui.login.PickGenderActivity;
import com.westudio.wecampus.ui.login.PickSchoolActivity;
import com.westudio.wecampus.util.Utility;

/**
 * Created by Martian on 13-10-20.
 */
public class MyProfileActivity extends BaseDetailActivity {

    public static final String NICK_NAME = "nick_name";
    public static final String REAL_NAME = "real_name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String WORDS = "words";
    public static final String PICK_EMOTION = "emotion";
    public static final String PICK_STAGE = "stage";
    public static final int UPDATE_NICK_REQUEST = 1;
    public static final int UPDATE_NICK_RESULT = 2;
    public static final int UPDATE_NAME_REQUEST = 3;
    public static final int UPDATE_NAME_RESULT = 4;
    public static final int UPDATE_PHONE_REQUEST = 5;
    public static final int UPDATE_PHONE_RESULT = 6;
    public static final int UPDATE_EMAIL_REQUEST = 7;
    public static final int UPDATE_EMAIL_RESULT = 8;
    public static final int UPDATE_WORDS_REQUEST = 9;
    public static final int UPDATE_WORDS_RESULT = 10;
    public static final int PICK_EMOTION_REQUEST = 11;
    public static final int PICK_EMOTION_RESULT = 12;
    public static final int PICK_STAGE_REQUEST = 13;
    public static final int PICK_STAGE_RESULT = 14;

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

    private int schoolId;

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
                    Intent intent = new Intent(MyProfileActivity.this, PickSchoolActivity.class);
                    startActivityForResult(intent, AuthActivity.PICK_SCHOOL_REQUEST);
                    break;
                }
                case R.id.gender: {
                    Intent intent = new Intent(MyProfileActivity.this, PickGenderActivity.class);
                    startActivityForResult(intent, AuthActivity.PICK_GENDER_REQUEST);
                    break;
                }
                case R.id.words: {
                    Intent intent = new Intent(MyProfileActivity.this, UpdateWordsActivity.class);
                    intent.putExtra(WORDS, mUser.words);
                    startActivityForResult(intent, UPDATE_WORDS_REQUEST);
                    break;
                }
                case R.id.relationship: {
                    Intent intent = new Intent(MyProfileActivity.this, PickEmotionActivity.class);
                    intent.putExtra(PICK_EMOTION, tvLove.getText().toString());
                    startActivityForResult(intent, PICK_EMOTION_REQUEST);
                    break;
                }
                case R.id.role: {
                    Intent intent = new Intent(MyProfileActivity.this, PickStageActivity.class);
                    intent.putExtra(PICK_STAGE, tvRole.getText().toString());
                    startActivityForResult(intent, PICK_STAGE_REQUEST);
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
        if(requestCode == UPDATE_NICK_REQUEST && resultCode == UPDATE_NICK_RESULT) {
            tvNickName.setText(data.getStringExtra(NICK_NAME));
        } else if(resultCode == AuthActivity.PICK_GENDER_RESULT) {
            int type = data.getIntExtra(AuthActivity.PICK_GENDER, 2);
            String strGender = (type == 0 ? getString(R.string.gender_male) :
                    (type == 1 ? getString(R.string.gender_female) : getString(R.string.gender_secret)));
            tvGender.setText(strGender);
        } else if(resultCode == AuthActivity.PICK_SCHOOL_RESULT) {
            tvSchool.setText(data.getStringExtra(AuthActivity.PICK_SCHOOL_NAME));
            schoolId = data.getIntExtra(AuthActivity.PICK_SCHOOL_ID, -1);
        } else if(requestCode == UPDATE_WORDS_REQUEST && resultCode == UPDATE_WORDS_RESULT) {
            tvWord.setText(data.getStringExtra(WORDS));
        } else if(requestCode == UPDATE_NAME_REQUEST && resultCode == UPDATE_NAME_RESULT) {
            tvName.setText(data.getStringExtra(REAL_NAME));
        } else if(requestCode == UPDATE_PHONE_REQUEST && resultCode == UPDATE_PHONE_RESULT) {
            tvPhone.setText(data.getStringExtra(PHONE));
        } else if(requestCode == UPDATE_EMAIL_REQUEST && resultCode == UPDATE_EMAIL_RESULT) {
            tvEmail.setText(data.getStringExtra(EMAIL));
        } else if(requestCode == PICK_EMOTION_REQUEST && resultCode == PICK_EMOTION_RESULT) {
            int type = data.getIntExtra(PICK_EMOTION, -1);
            tvLove.setText(Utility.getEmotionByType(this, type));
        } else if(requestCode == PICK_STAGE_REQUEST && resultCode == PICK_STAGE_RESULT) {
            int type = data.getIntExtra(PICK_STAGE, -1);
            tvRole.setText(Utility.getStageByType(this, type));
        }
    }
}
