package com.westudio.wecampus.ui.square;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseDetailActivity;

/**
 * Created by Martian on 13-10-21.
 */
public class SearchActivity extends BaseDetailActivity{
    private ListView mLvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLvResult = (ListView) findViewById(R.id.search_result_list);

        findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
