package com.westudio.wecampus.ui.base;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.westudio.wecampus.R;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

/**
 * Created by nankonami on 13-12-11.
 */
public class BaseListActivity extends SherlockFragmentActivity {

    protected PullToRefreshAttacher mPullToRefreshAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
    }
}
