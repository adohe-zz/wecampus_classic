package com.westudio.wecampus.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityCategory;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.activity.ActivityListFragment;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.square.SquareFragment;
import com.westudio.wecampus.ui.user.UserHomepageFragment;
import com.westudio.wecampus.ui.user.UsersListFragment;

import java.util.HashMap;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

public class MainActivity extends SherlockFragmentActivity {
    public static final int MSG_SWITCH_CONTENT = 9009;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggleCompat mDrawerToggle;
    private PullToRefreshAttacher mPullToRefreshAttacher;
    private ContentType mCurrentContent = ContentType.ACTIVITY;

    public enum ContentType {
        ACTIVITY, USERS, SQUARE, SETTINGS, HOMEPAGE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggleCompat(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {

            }

            public void onDrawerOpened(View drawerView) {

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

        Fragment activityFragment = ActivityListFragment.newInstance(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, activityFragment).commit();
        Fragment menuFragment = LeftMenuFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.left_drawer, menuFragment).commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }

    public void changeContent(ContentType type) {
        if (mCurrentContent != type) {
            mCurrentContent = type;

            Class<?> clazz = null;
            if (type == ContentType.ACTIVITY) {
                clazz = ActivityListFragment.class;
            } else if (type == ContentType.USERS) {
                clazz = UsersListFragment.class;
            } else if (type == ContentType.SQUARE) {
                clazz = SquareFragment.class;
            } else if (type == ContentType.HOMEPAGE) {
                clazz = UserHomepageFragment.class;
            }
            Fragment f = Fragment.instantiate(this, clazz.getName());
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
        }

        mDrawerLayout.closeDrawers();

    }
}
