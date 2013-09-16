package com.westudio.wecampus.ui.main;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.util.ImageUtil;

/**
 * Created by martian on 13-9-15.
 * This Fragment is for main menu in the app drawer
 */
public class LeftMenuFragment extends Fragment{

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

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
