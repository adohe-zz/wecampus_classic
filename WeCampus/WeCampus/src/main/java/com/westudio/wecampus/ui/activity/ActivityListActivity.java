package com.westudio.wecampus.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.westudio.wecampus.R;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;


/**
 * Created by nankonami on 13-11-29.
 */
public class ActivityListActivity extends SherlockFragmentActivity {

    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    private PullToRefreshAttacher mPullToRefreshAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

        Fragment f = ListFragment.newInstance(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_list_frame,
                f).commit();
    }

    public PullToRefreshAttacher getmPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }
}