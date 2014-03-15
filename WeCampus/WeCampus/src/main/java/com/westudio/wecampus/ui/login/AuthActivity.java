package com.westudio.wecampus.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.PickPhotoActivity;
import com.westudio.wecampus.ui.main.MainActivity;

/**
 * Created by nankonami on 13-9-18.
 * The login/register activity
 */
public class AuthActivity extends PickPhotoActivity {
    public static final String FLAG_CHANGE_PWD = "flag_change_password";

    public static final String LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT";
    public static final String REGISTER_FRAGMENT_TAG = "REG_FRAGMENT";
    public static final String UPDATE_PROFILE_TAG = "UPDATE_PROFILE";

    public static final int PICK_SCHOOL_REQUEST = 17;
    public static final int PICK_GENDER_REQUEST = 18;
    public static final int PICK_SCHOOL_RESULT = 19;
    public static final int PICK_GENDER_RESULT = 20;
    public static final String PICK_SCHOOL_NAME = "school_name";
    public static final String PICK_SCHOOL_ID = "school_id";
    public static final String PICK_GENDER = "gender";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.auth_container, LoginFragment.newInstance(null), LOGIN_FRAGMENT_TAG)
                .commit();

        setUpActionBar();

        //TODO delete
        /**只是为了测试上传资料*/
        /**************************************************************************************
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
        fragmentTransaction.replace(R.id.auth_container, UpdateProfileFragment.newInstance(null),
                AuthActivity.UPDATE_PROFILE_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        /**************************************************************************************/

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("AuthActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AuthActivity");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA: {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    doCropPhoto(selectedImage, mCropedTemp);
                }
                break;
            }
            case CAMERA_WITH_DATA: {
                processPhotoByCamera(mUriTemp);
                break;
            }
            case PHOTO_CROPED_WITH_DATA: {
                UpdateProfileFragment fragment = (UpdateProfileFragment) getSupportFragmentManager().findFragmentByTag(UPDATE_PROFILE_TAG);
                Bundle bundle = new Bundle();
                Bitmap bm = decodeUriAsBitmap(mCropedTemp);
                bundle.putParcelable("cropedImage", bm);
                bundle.putString("imagePath", mCropedTemp.getPath());
                fragment.setCropedAvatarImage(bundle);
                break;
            }
        }
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            cancelAuth();
        }
        MobclickAgent.onEvent(this, "register_back_btn");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cancelAuth();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void cancelAuth() {
        if (getIntent().getBooleanExtra(FLAG_CHANGE_PWD, false)) {
            MainActivity.getInstance().finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
