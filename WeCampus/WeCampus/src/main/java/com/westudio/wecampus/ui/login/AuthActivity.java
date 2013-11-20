package com.westudio.wecampus.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.PickPhotoActivity;

/**
 * Created by nankonami on 13-9-18.
 * The login/register activity
 */
public class AuthActivity extends PickPhotoActivity {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.auth_container, LoginFragment.newInstance(null), LOGIN_FRAGMENT_TAG)
                .commit();

        setUpActionBar();

        //TODO delete
        /**只是为了测试上传资料*/
        /**************************************************************************************/
        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
        fragmentTransaction.replace(R.id.auth_container, UpdateProfileFragment.newInstance(null),
                AuthActivity.UPDATE_PROFILE_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        /**************************************************************************************/

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
                Uri selectedImage = data.getData();
                doCropPhoto(selectedImage, mUriTemp);
                break;
            }
            case CAMERA_WITH_DATA: {
                doCropPhoto(mUriTemp, mUriTemp);
                break;
            }
            case PHOTO_CROPED_WITH_DATA: {
                UpdateProfileFragment fragment = (UpdateProfileFragment) getSupportFragmentManager().findFragmentByTag(UPDATE_PROFILE_TAG);
                Bundle bundle = new Bundle();
                Bitmap bm = decodeUriAsBitmap(mUriTemp);
                bundle.putParcelable("cropedImage", bm);
                bundle.putString("imagePath", mUriTemp.getPath());
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
