package com.westudio.wecampus.ui.user;

import android.os.Bundle;
import android.view.KeyEvent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseListActivity;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

/**
 * Created by nankonami on 13-12-11.
 */
public class UserListActivity extends SherlockFragmentActivity {

    public static String ACTIVITY_ID = "activity_id";
    private PullToRefreshAttacher mPullToRefreshAttacher;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
        updateActionBar();

        getSupportFragmentManager().beginTransaction().replace(R.id.user_list_frame, ListFragment.newInstance(
                getIntent().getExtras()), null).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }
}
