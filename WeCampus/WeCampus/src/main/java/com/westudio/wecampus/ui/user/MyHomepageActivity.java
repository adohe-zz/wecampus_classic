package com.westudio.wecampus.ui.user;

import android.os.Bundle;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseDetailActivity;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

/**
 * Created by martian on 13-9-21.
 * This Activity is used to display the profile of current user
 */
public class MyHomepageActivity extends BaseDetailActivity {

    private static final String FRAGMENT = "MY_HOME_PAGE";

    private PullToRefreshAttacher mPullToRefreshAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_homepage);

        updateActionBar();

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.my_home_page,
                new MyHomepageFragment(), FRAGMENT).commit();
    }

    public PullToRefreshAttacher getmPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
