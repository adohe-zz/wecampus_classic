package com.westudio.wecampus.ui.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.westudio.wecampus.R;

/**
 * Created by martian on 13-9-21.
 */
public class BaseDetailActivity extends BaseGestureActivity {

    @Override
    public void setContentView(int layoutResId) {

        LayoutInflater layoutInflater = getLayoutInflater();

        ViewGroup allContent = (ViewGroup)layoutInflater.inflate(R.layout.activity_base_detail, null);
        ViewGroup content = (ViewGroup)allContent.findViewById(R.id.base_detail_frame);
        content.removeAllViews();
        content.addView(layoutInflater.inflate(layoutResId, null));

        super.setContentView(allContent);
    }

}
