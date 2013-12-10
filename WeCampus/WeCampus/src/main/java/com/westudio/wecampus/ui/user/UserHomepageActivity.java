package com.westudio.wecampus.ui.user;

import android.os.Bundle;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseDetailActivity;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

/**
 * Created by nankonami on 13-10-6.
 * User Profile Activity
 */
public class UserHomepageActivity extends BaseDetailActivity {

    public static String USER_ID = "user_id";
    private static String FRAGMENT = "USER_HOME_PAGE";

    private PullToRefreshAttacher mPullToRefreshAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.user_home_page,
                UserHomepageFragment.newInstance(getIntent().getExtras()), FRAGMENT).commit();
    }

    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }
}
