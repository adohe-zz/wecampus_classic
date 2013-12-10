package com.westudio.wecampus.ui.square;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseDetailActivity;
import com.westudio.wecampus.ui.view.HeaderTabBar;
import com.westudio.wecampus.ui.view.LoadingFooter;
import com.westudio.wecampus.util.Utility;

/**
 * Created by Martian on 13-10-21.
 */
public class SearchActivity extends BaseDetailActivity{
    private ListView mLvResult;
    private EditText mEtKeywords;
    private HeaderTabBar mHeaderTab;
    private SearchListAttacher mAttacher = new SearchListAttacher();
    // 已经输入的关键字
    private String mKeywords;

    // 3个不同的适配器
    private SearchActivityAdapter mActivityAdapter;
    private SearchOrgAdapter mOrgAdapter;
    private SearchUserAdapter mSearchUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLvResult = (ListView) findViewById(R.id.search_result_list);
        mLvResult.setEmptyView(findViewById(R.id.empty_view));
        mAttacher.loadingFooter = new LoadingFooter(this);
        mLvResult.addFooterView(mAttacher.loadingFooter.getView());
        mLvResult.setOnScrollListener(onScrollChangedListener);

        mEtKeywords = (EditText) findViewById(R.id.search_bar);
        mEtKeywords.setOnEditorActionListener(new OnSearchActionListener());
        mHeaderTab = (HeaderTabBar) findViewById(R.id.search_result_tabbar);
        mHeaderTab.setTexts(R.string.search_tab_activity, R.string.serach_tab_user,
                R.string.search_tab_org);
        mHeaderTab.setmOnTabSelectedListener(tabSelectedListener);

        // 设置适配器
        mAttacher.emptyView = (ImageView) findViewById(R.id.empty_image);
        mAttacher.progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mActivityAdapter = new SearchActivityAdapter(this);
        mActivityAdapter.setAttacher(mAttacher);
        mOrgAdapter = new SearchOrgAdapter(this);
        mOrgAdapter.setAttacher(mAttacher);
        mSearchUserAdapter = new SearchUserAdapter(this);
        mSearchUserAdapter.setAttacher(mAttacher);

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
            if (i == EditorInfo.IME_ACTION_SEARCH && textView.getText().length() > 0) {
                mKeywords = textView.getText().toString();
                switch (mHeaderTab.getCurrentPosition()) {
                    case 0: {
                        // 如果当前显示的是活动标签
                        mActivityAdapter.clear();
                        mActivityAdapter.requestData(mKeywords, 1);
                        mLvResult.setAdapter(mActivityAdapter);
                        mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 13f));
                        break;
                    }
                    case 1: {
                        mSearchUserAdapter.clear();
                        mSearchUserAdapter.requestData(mKeywords, 1);
                        mLvResult.setAdapter(mSearchUserAdapter);
                        mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 1f));
                        break;
                    }
                    case 2: {
                        mOrgAdapter.clear();
                        mOrgAdapter.requestData(mKeywords, 1);
                        mLvResult.setAdapter(mOrgAdapter);
                        mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 1f));
                        break;
                    }
                }

                mAttacher.page = 1;
                return true;
            }

            return false;
        }
    }

    private HeaderTabBar.OnTabSelectedListener tabSelectedListener = new HeaderTabBar.OnTabSelectedListener() {

        @Override
        public void onFirstTabSelected() {
            if (mHeaderTab.getCurrentPosition() != 0 && mEtKeywords.getText().length() > 0) {
                mActivityAdapter.clear();
                mActivityAdapter.requestData(mEtKeywords.getText().toString(), 1);
            }
            mLvResult.setAdapter(mActivityAdapter);
            mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 13f));
            mAttacher.page = 1;
        }

        @Override
        public void onSecondTabSelected() {
            if (mHeaderTab.getCurrentPosition() != 1 && mEtKeywords.getText().length() > 0) {
                mSearchUserAdapter.clear();
                mSearchUserAdapter.requestData(mEtKeywords.getText().toString(), 1);
            }
            mLvResult.setAdapter(mSearchUserAdapter);
            mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 1f));
            mAttacher.page = 1;
        }

        @Override
        public void onThirdTabSelected() {
            if (mHeaderTab.getCurrentPosition() != 2 && mEtKeywords.getText().length() > 0) {
                mOrgAdapter.clear();
                mOrgAdapter.requestData(mEtKeywords.getText().toString(), 1);
            }
            mLvResult.setAdapter(mOrgAdapter);
            mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 1f));
            mAttacher.page = 1;
        }
    };

    private AbsListView.OnScrollListener onScrollChangedListener =
            new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(mAttacher.loadingFooter.getState() == LoadingFooter.State.Loading ||
                    mAttacher.loadingFooter.getState() == LoadingFooter.State.TheEnd ) {
                return;
            }

            if(firstVisibleItem + visibleItemCount >= totalItemCount
                    && totalItemCount != 0
                    && totalItemCount != mLvResult.getFooterViewsCount() +
                    mLvResult.getFooterViewsCount() && mLvResult.getAdapter().getCount() > 0) {
                switch(mHeaderTab.getCurrentPosition()) {
                    case 0:
                        mActivityAdapter.requestData(mKeywords, mAttacher.page);
                        break;
                    case 1:
                        mSearchUserAdapter.requestData(mKeywords, mAttacher.page);
                        break;
                    case 2:
                        mOrgAdapter.requestData(mKeywords, mAttacher.page);
                        break;
                }
            }
        }
    };

}
