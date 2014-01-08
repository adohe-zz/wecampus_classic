package com.westudio.wecampus.ui.square;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.activity.ActivityDetailActivity;
import com.westudio.wecampus.ui.activity.ActivityListFragment;
import com.westudio.wecampus.ui.base.BaseDetailActivity;
import com.westudio.wecampus.ui.organiztion.OrganizationHomepageActivity;
import com.westudio.wecampus.ui.user.UserHomepageActivity;
import com.westudio.wecampus.ui.view.HeaderTabBar;
import com.westudio.wecampus.ui.view.LoadingFooter;
import com.westudio.wecampus.util.Utility;

/**
 * Created by Martian on 13-10-21.
 */
public class SearchActivity extends BaseDetailActivity{
    public static final String SELECTED_POSITION = "selected_position";

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

    // 是否在做网络请求的标志位
    private boolean isLoadingData;

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
        mLvResult.setOnItemClickListener(onItemClickListener);

        mEtKeywords = (EditText) findViewById(R.id.search_bar);
        mEtKeywords.setOnEditorActionListener(new OnSearchActionListener());
        mHeaderTab = (HeaderTabBar) findViewById(R.id.search_result_tabbar);
        mHeaderTab.setTexts(R.string.search_tab_activity, R.string.serach_tab_user,
                R.string.search_tab_org);
        mHeaderTab.setmOnTabSelectedListener(tabSelectedListener);
        mHeaderTab.setSelected(getIntent().getIntExtra(SELECTED_POSITION, 0), true);

        // 设置适配器
        mAttacher.emptyView = (ImageView) findViewById(R.id.empty_image);
        mAttacher.progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mActivityAdapter = new SearchActivityAdapter(this);
        mActivityAdapter.setAttacher(mAttacher);
        mOrgAdapter = new SearchOrgAdapter(this);
        mOrgAdapter.setAttacher(mAttacher);
        mSearchUserAdapter = new SearchUserAdapter(this);
        mSearchUserAdapter.setAttacher(mAttacher);

        OnRefreshListener onRefreshFinishedListener = new OnRefreshListener() {
            @Override
            public void onRefreshFinished() {
                isLoadingData = false;
            }

            @Override
            public void onRefreshStarted() {
                isLoadingData = true;
            }
        };
        mSearchUserAdapter.setOnRefreshListener(onRefreshFinishedListener);
        mOrgAdapter.setOnRefreshListener(onRefreshFinishedListener);
        mActivityAdapter.setOnRefreshListener(onRefreshFinishedListener);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtKeywords.getWindowToken(), 0);
    }

    private class OnSearchActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_SEARCH && textView.getText().length() > 0) {
                mKeywords = textView.getText().toString();
                hideKeyboard();
                switch (mHeaderTab.getCurrentPosition()) {
                    case 0: {
                        // 如果当前显示的是活动标签
                        mActivityAdapter.clear();
                        mActivityAdapter.requestData(mKeywords, true);
                        mLvResult.setAdapter(mActivityAdapter);
                        mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 13f));
                        break;
                    }
                    case 1: {
                        mSearchUserAdapter.clear();
                        mSearchUserAdapter.requestData(mKeywords, true);
                        mLvResult.setAdapter(mSearchUserAdapter);
                        mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 1f));
                        break;
                    }
                    case 2: {
                        mOrgAdapter.clear();
                        mOrgAdapter.requestData(mKeywords, true);
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
                mActivityAdapter.requestData(mEtKeywords.getText().toString(), true);
            }
            mLvResult.setAdapter(mActivityAdapter);
            mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 13f));
            mAttacher.page = 1;
        }

        @Override
        public void onSecondTabSelected() {
            if (mHeaderTab.getCurrentPosition() != 1 && mEtKeywords.getText().length() > 0) {
                mSearchUserAdapter.clear();
                mSearchUserAdapter.requestData(mEtKeywords.getText().toString(), true);
            }
            mLvResult.setAdapter(mSearchUserAdapter);
            mLvResult.setDividerHeight(Utility.dip2px(SearchActivity.this, 1f));
            mAttacher.page = 1;
        }

        @Override
        public void onThirdTabSelected() {
            if (mHeaderTab.getCurrentPosition() != 2 && mEtKeywords.getText().length() > 0) {
                mOrgAdapter.clear();
                mOrgAdapter.requestData(mEtKeywords.getText().toString(), true);
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
                    mAttacher.loadingFooter.getState() == LoadingFooter.State.TheEnd || isLoadingData) {
                return;
            }

            if(firstVisibleItem + visibleItemCount >= totalItemCount
                    && totalItemCount != 0
                    && totalItemCount != mLvResult.getFooterViewsCount() +
                    mLvResult.getFooterViewsCount() && mLvResult.getAdapter().getCount() > 0) {
                switch(mHeaderTab.getCurrentPosition()) {
                    case 0:
                        mActivityAdapter.requestData(mKeywords, false);
                        break;
                    case 1:
                        mSearchUserAdapter.requestData(mKeywords, false);
                        break;
                    case 2:
                        mOrgAdapter.requestData(mKeywords, false);
                        break;
                }
            }
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent;
            switch (mHeaderTab.getCurrentPosition()) {
                case 0:
                    ActivityList activity = (ActivityList) adapterView.getAdapter().getItem(i);
                    intent = new Intent(SearchActivity.this, ActivityDetailActivity.class);
                    intent.putExtra(ActivityListFragment.ACTIVITY_ID, activity.id);
                    startActivity(intent);
                    break;
                case 1:
                    //别人的主页
                    User user = (User) adapterView.getAdapter().getItem(i);
                    intent = new Intent(SearchActivity.this, UserHomepageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(UserHomepageActivity.USER, user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 2:
                    //组织详情
                    Organization org = (Organization) adapterView.getAdapter().getItem(i);
                    intent = new Intent(SearchActivity.this, OrganizationHomepageActivity.class);
                    intent.putExtra(OrganizationHomepageActivity.ORG_ID, org.id);
                    startActivity(intent);
                    break;

            }
            SearchActivity.this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
    };

}
