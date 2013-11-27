package com.westudio.wecampus.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.login.AuthActivity;
import com.westudio.wecampus.ui.user.MyHomepageActivity;
import com.westudio.wecampus.ui.user.MyProfileActivity;
import com.westudio.wecampus.util.ImageUtil;

/**
 * Created by martian on 13-9-15.
 * This Fragment is for main menu in the app drawer
 */
public class LeftMenuFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout mActivitySection, mUserSection, mSquareSection, mSettingsSection;
    private ImageButton mBtnEdit;
    private Button mBtnSignOut;
    private Button mBtnSignIn;
    private LogoutHandler mLogoutHandler;

    public static LeftMenuFragment newInstance() {
        LeftMenuFragment f = new LeftMenuFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leftmenu, container, false);

        //avatar
        ImageView avatar = (ImageView) view.findViewById(R.id.img_avatar);
        Bitmap bm = ((BitmapDrawable)avatar.getDrawable()).getBitmap();
        bm = ImageUtil.getRoundedCornerBitmap(bm);
        avatar.setImageBitmap(bm);
        avatar.setOnClickListener(this);
        mBtnEdit = (ImageButton) view.findViewById(R.id.edit_button);
        mBtnEdit.setOnClickListener(this);
        mBtnSignOut = (Button) view.findViewById(R.id.btn_sign_out);
        mBtnSignOut.setOnClickListener(this);
        mBtnSignIn = (Button) view.findViewById(R.id.btn_sign_in);
        mBtnSignIn.setOnClickListener(this);

        if (BaseApplication.getInstance().hasAccount) {
            mBtnSignOut.setVisibility(View.VISIBLE);
        } else {
            mBtnSignIn.setVisibility(View.VISIBLE);
        }

        //list
        mActivitySection = (RelativeLayout) view.findViewById(R.id.activity_section);
        mUserSection = (RelativeLayout) view.findViewById(R.id.user_section);
        mSquareSection = (RelativeLayout) view.findViewById(R.id.square_section);
        mSettingsSection = (RelativeLayout) view.findViewById(R.id.settings_section);
        mActivitySection.setOnClickListener(this);
        mUserSection.setOnClickListener(this);
        mSquareSection.setOnClickListener(this);
        mSettingsSection.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogoutHandler = new LogoutHandler();
    }

    @Override
    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (view.getId()) {
            case R.id.activity_section: {
                mainActivity.changeContent(MainActivity.ContentType.ACTIVITY);
                break;
            }
            case R.id.user_section: {
                mainActivity.changeContent(MainActivity.ContentType.USERS);
                break;
            }
            case R.id.square_section: {
                mainActivity.changeContent(MainActivity.ContentType.SQUARE);
                break;
            }
            case R.id.settings_section: {

                break;
            }
            case R.id.img_avatar: {
                mainActivity.changeContent(MainActivity.ContentType.HOMEPAGE);
                break;
            }
            case R.id.edit_button: {
                startActivity(new Intent(getActivity(), MyProfileActivity.class));
                break;
            }
            case R.id.btn_sign_in: {
                startActivity(new Intent(getActivity(), AuthActivity.class));
                break;
            }
            case R.id.btn_sign_out: {
                mLogoutHandler.logout();
                break;
            }
        }
    }

    private class LogoutHandler implements Response.Listener, Response.ErrorListener {
        ProgressDialog progressDialog;

        public void logout() {
            BaseApplication app = BaseApplication.getInstance();
            int id = app.getAccountMgr().getUserId();
            app.hasAccount = false;
            app.getAccountMgr().clearAccountInfo();
            WeCampusApi.logout(LeftMenuFragment.this, id, this, this);

            if ( progressDialog == null) {
                progressDialog = new ProgressDialog(getActivity());
            }
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.show();
        }
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            progressDialog.dismiss();
        }

        @Override
        public void onResponse(Object o) {
            progressDialog.dismiss();
            mBtnSignOut.setVisibility(View.GONE);
            mBtnSignIn.setVisibility(View.VISIBLE);
        }
    }
}
