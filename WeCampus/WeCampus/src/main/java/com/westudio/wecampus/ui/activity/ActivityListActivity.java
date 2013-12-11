package com.westudio.wecampus.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseListActivity;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;


/**
 * Created by nankonami on 13-11-29.
 */
public class ActivityListActivity extends BaseListActivity {

    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        updateActionBar();
        Fragment f = ListFragment.newInstance(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_list_frame,
                f).commit();
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
