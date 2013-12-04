package com.westudio.wecampus.ui.square;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseDetailActivity;

/**
 * Created by Martian on 13-10-21.
 */
public class SearchActivity extends BaseDetailActivity{
    private ListView mLvResult;
    private EditText mEtKeywords;

    // 已经输入的关键字
    private String mKeywords;

    private SearchActivityAdapter mActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLvResult = (ListView) findViewById(R.id.search_result_list);
        mEtKeywords = (EditText) findViewById(R.id.search_bar);
        mEtKeywords.setOnEditorActionListener(new OnSearchActionListener());

        findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        mActivityAdapter = new SearchActivityAdapter(this);
        mLvResult.setAdapter(mActivityAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class OnSearchActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 如果当前显示的是活动标签
                mActivityAdapter.requestData(textView.getText().toString(), 1);

                return true;
            }

            return false;
        }
    }

}
