package com.westudio.wecampus.ui.organiztion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.OrgDataHelper;
import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.activity.ActivityDetailActivity;
import com.westudio.wecampus.ui.activity.ActivityListFragment;
import com.westudio.wecampus.ui.adapter.CardsAnimationAdapter;
import com.westudio.wecampus.ui.base.BaseGestureActivity;
import com.westudio.wecampus.ui.view.HeaderTabBar;
import com.westudio.wecampus.ui.view.LoadingFooter;
import com.westudio.wecampus.ui.view.PinnedHeaderListView;
import com.westudio.wecampus.util.Utility;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

/**
 * Created by martian on 13-11-22.
 */
public class OrganizationHomepageActivity extends BaseGestureActivity implements OnRefreshListener {

    public static final String ORG_ID = "organization_id";

    private PinnedHeaderListView mListView;
    private LoadingFooter loadingFooter;
    private HeaderTabBar mPinnedHeader;
    private IntroAdapter mIntroAdapter;
    private OrganizationActivityAdapter activityAdapter;

    private int mId;
    private Organization mOrganization;
    private OrgDataHelper mOrgDataHelper;
    private OrgQueryTask mOrgQueryTask;
    private RequestOrgHandler mRequestOrgHandler;
    private RequestOrgActivityHandler mRequestOrgAcHandler;
    private PullToRefreshAttacher mPullToRefreshAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_homepage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mId = getIntent().getIntExtra(ORG_ID, -1);
        mOrgDataHelper = new OrgDataHelper(this);
        mRequestOrgHandler = new RequestOrgHandler();
        mRequestOrgAcHandler = new RequestOrgActivityHandler();

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

        mPinnedHeader = (HeaderTabBar) findViewById(R.id.pinned_header);
        mListView = (PinnedHeaderListView) findViewById(R.id.listview);
        registerSwipeToCloseListener(mListView);
        mIntroAdapter = new IntroAdapter(this);

        mListView.setmPinnedHeader(mPinnedHeader);
        activityAdapter = new OrganizationActivityAdapter(this);
        CardsAnimationAdapter animationAdapter = new CardsAnimationAdapter(this, activityAdapter);
        animationAdapter.setListView(mListView);
        mListView.setAdapter(animationAdapter);

        mListView.setTabTexts(R.string.posted_activities, R.string.brief_introduction, 0);

        mListView.setHeaderOffScreenListener(new PinnedHeaderListView.OnHeaderOffScreenListener() {
            @Override
            public void onHeaderOffScreen() {
                mPinnedHeader.setVisibility(View.VISIBLE);
            }

            @Override
            public void onHeaderInScreen() {
                mPinnedHeader.setVisibility(View.GONE);
            }
        });

        mListView.setmTabSelectedListener(new PinnedHeaderListView.OnTabSelectedListener() {
            @Override
            public void onTabOneSelected() {
                mListView.setAdapter(activityAdapter);
            }

            @Override
            public void onTabTwoSelected() {
                mListView.setAdapter(mIntroAdapter);
            }

            @Override
            public void onTabThreeSelecd() {
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrganizationHomepageActivity.this, ActivityDetailActivity.class);
                ActivityList ac = (ActivityList)activityAdapter.getItem(position - 1);
                intent.putExtra(ActivityListFragment.ACTIVITY_ID, ac.id);
                startActivity(intent);
            }
        });

        mOrgQueryTask = new OrgQueryTask();
        Utility.executeAsyncTask(mOrgQueryTask);
        getOrgActivity(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOrgQueryTask != null) {
            mOrgQueryTask.cancel(true);
        }
    }

    private void getOrgActivity(final int page) {
        WeCampusApi.getOrganizationActivity(this, mId, page, mRequestOrgAcHandler, mRequestOrgAcHandler);
    }

    private void updateUI() {
        mListView.setName(mOrganization.name);
        mListView.setAvatar(mOrganization.avatar);
        mIntroAdapter.setData(mOrganization.admin_name, mOrganization.description,
                mOrganization.admin_url);
    }

    @Override
    public void onRefreshStarted(View view) {
        WeCampusApi.getOrganization(this, mId, mRequestOrgHandler, mRequestOrgHandler);
    }

    private class OrgQueryTask extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... objects) {
            mOrganization = mOrgDataHelper.query(mId);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            updateUI();
        }
    }

    private class RequestOrgHandler implements Response.Listener<Organization>, Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            mPullToRefreshAttacher.setRefreshComplete();
        }

        @Override
        public void onResponse(final Organization organization) {
            mPullToRefreshAttacher.setRefreshComplete();
            if (organization != null) {
                Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... objects) {
                        mOrgDataHelper.update(organization);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        mOrgQueryTask = new OrgQueryTask();
                        Utility.executeAsyncTask(mOrgQueryTask);
                    }
                });
            }
        }
    }

    //Request Organization Activity Handler
    private class RequestOrgActivityHandler implements Response.Listener<ActivityList.RequestData>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if(mPullToRefreshAttacher.isRefreshing()) {
                mPullToRefreshAttacher.setRefreshComplete();
            }
        }

        @Override
        public void onResponse(ActivityList.RequestData requestData) {
            if(mPullToRefreshAttacher.isRefreshing()) {
                mPullToRefreshAttacher.setRefreshComplete();
            }

            activityAdapter.addAll(requestData.getObjects());
        }
    }
}
