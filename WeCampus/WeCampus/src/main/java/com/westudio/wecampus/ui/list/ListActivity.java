package com.westudio.wecampus.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseListActivity;

/**
 * Created by nankonami on 13-12-11.
 */
public class ListActivity extends BaseListActivity {

    public static String USER_ID = "user_id";
    public static String TYPE = "type";

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        bundle = getIntent().getExtras();

        updateActionBar();

        Fragment fragment;
        if(bundle.getInt(TYPE, -1) == 1) {
            fragment = JoinActivityFragment.newInstance(bundle);
        } else if(bundle.getInt(TYPE, -1) == 2) {
            fragment = FollowActivityFragment.newInstance(bundle);
        } else {
            fragment = FollowOrganizationFragment.newInstance(bundle);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_list_frame,
                fragment, null).commit();
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(bundle.getInt(TYPE, -1) == 1) {
            getSupportActionBar().setTitle(getResources().getString(R.string.activity_join));
        } else if(bundle.getInt(TYPE, -1) == 2) {
            getSupportActionBar().setTitle(getResources().getString(R.string.activity_like));
        } else {
            getSupportActionBar().setTitle(getResources().getString(R.string.org_like));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
