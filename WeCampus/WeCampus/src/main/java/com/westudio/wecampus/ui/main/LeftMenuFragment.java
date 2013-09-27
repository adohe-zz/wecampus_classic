package com.westudio.wecampus.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.user.MyProfileActivity;
import com.westudio.wecampus.util.ImageUtil;

/**
 * Created by martian on 13-9-15.
 * This Fragment is for main menu in the app drawer
 */
public class LeftMenuFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout mActivitySection, mUserSection, mSquareSection, mSettingsSection;

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
                startActivity(new Intent(getActivity(), MyProfileActivity.class));
                break;
            }
        }
    }
}
