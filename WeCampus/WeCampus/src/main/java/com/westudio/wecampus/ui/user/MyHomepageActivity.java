package com.westudio.wecampus.ui.user;

import android.os.Bundle;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseDetailActivity;

/**
 * Created by martian on 13-9-21.
 * This Activity is used to display the profile of current user
 */
public class MyHomepageActivity extends BaseDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_my_homepage);

    }

}
