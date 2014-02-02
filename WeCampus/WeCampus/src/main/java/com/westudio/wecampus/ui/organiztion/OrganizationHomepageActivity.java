package com.westudio.wecampus.ui.organiztion;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.OrgDataHelper;
import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.data.model.OrgFans;
import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.activity.ActivityDetailActivity;
import com.westudio.wecampus.ui.activity.ActivityListFragment;
import com.westudio.wecampus.ui.adapter.CardsAnimationAdapter;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.BaseGestureActivity;
import com.westudio.wecampus.ui.view.HeaderTabBar;
import com.westudio.wecampus.ui.view.LoadingFooter;
import com.westudio.wecampus.ui.view.PinnedHeaderListView;
import com.westudio.wecampus.util.Utility;

import java.util.ArrayList;
import java.util.List;

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
    private TextView tvLike;

    private IntroAdapter mIntroAdapter;
    private OrganizationActivityAdapter activityAdapter;

    private int mId;
    private Organization mOrganization;
    private OrgDataHelper mOrgDataHelper;
    private OrgQueryTask mOrgQueryTask;
    private RequestOrgHandler mRequestOrgHandler;
    private RequestOrgActivityHandler mRequestOrgAcHandler;
    private FollowOrganizationHandler followOrganizationHandler;
    private PullToRefreshAttacher mPullToRefreshAttacher;

    private boolean bNetworkFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_homepage);

        updateActionBar();

        mId = getIntent().getIntExtra(ORG_ID, -1);
        mOrgDataHelper = new OrgDataHelper(this);
        mRequestOrgHandler = new RequestOrgHandler();
        mRequestOrgAcHandler = new RequestOrgActivityHandler();
        followOrganizationHandler = new FollowOrganizationHandler(this);

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

        tvLike = (TextView)findViewById(R.id.user_like);
        tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BaseApplication.getInstance().hasAccount) {
                    followOrganizationHandler.followOrganization(mOrganization.can_follow);
                } else {
                    Toast.makeText(OrganizationHomepageActivity.this, R.string.please_login, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mOrgQueryTask = new OrgQueryTask();
        Utility.executeAsyncTask(mOrgQueryTask);
        mRequestOrgHandler.requestOrg();
        getOrgActivity(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("OrganizationHomePageActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("OrganizationHomePageActivity");
        MobclickAgent.onPause(this);
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.org_intro);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOrgQueryTask != null) {
            mOrgQueryTask.cancel(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void getOrgActivity(final int page) {
        WeCampusApi.getOrganizationActivity(this, mId, page, mRequestOrgAcHandler, mRequestOrgAcHandler);
    }

    private void updateUI() {
        mListView.setName(mOrganization.name);
        mListView.setAvatar(mOrganization.avatar);
        mIntroAdapter.setData(mOrganization.admin_name, mOrganization.description,
                mOrganization.admin_email);
        tvLike.setText(String.valueOf(mOrganization.count_of_fans));
        if(BaseApplication.getInstance().hasAccount) {
            if(mOrganization.can_follow) {
                tvLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_list_activity_like_un), null,
                        null, null);
            } else {
                tvLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_list_activity_like_sl), null,
                        null, null);
            }
        } else {
            tvLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_list_activity_like_un), null,
                    null, null);
        }
    }

    @Override
    public void onRefreshStarted(View view) {
        WeCampusApi.getOrganization(this, mId, mRequestOrgHandler, mRequestOrgHandler);
    }

    public void updateOrgToDb() {
        Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                mOrgDataHelper.update(mOrganization);
                return null;
            }
        });
    }

    private class OrgQueryTask extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... objects) {
            mOrganization = mOrgDataHelper.query(mId);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if(mOrganization != null && !bNetworkFinished) {
                updateUI();
            }
        }
    }

    private class RequestOrgHandler implements Response.Listener<Organization>, Response.ErrorListener{

        public RequestOrgHandler() {

        }

        public void requestOrg() {
            WeCampusApi.getOrganization(OrganizationHomepageActivity.this, mId, this, this);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            bNetworkFinished = true;
            mPullToRefreshAttacher.setRefreshComplete();
        }

        @Override
        public void onResponse(final Organization organization) {
            mPullToRefreshAttacher.setRefreshComplete();
            bNetworkFinished = true;
            if (organization != null) {
                mOrganization = organization;
                updateUI();
                //Update the database
                updateOrgToDb();
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

    /*//Request Organization fans handler
    private class RequestOrgFansHandler implements Response.Listener<OrgFans.RequestData>, Response.ErrorListener {

        private Activity ac;
        private TextView tvLike;

        public RequestOrgFansHandler(Activity activity) {
            this.ac = activity;
            tvLike = (TextView)ac.findViewById(R.id.user_like);
            tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(BaseApplication.getInstance().hasAccount) {
                        if(Utility.isConnect(OrganizationHomepageActivity.this)) {
                            followOrganizationHandler.followOrganization();
                        } else {
                            Toast.makeText(OrganizationHomepageActivity.this, getResources().getString(R.string.network_problem),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OrganizationHomepageActivity.this, getResources().getString(R.string.please_login),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            tvLike.setText("0");
        }

        @Override
        public void onResponse(OrgFans.RequestData requestData) {
            tvLike.setText(String.valueOf(requestData.getObjects().size()));
            fansList.addAll(requestData.getObjects());
        }

        public void refreshUI() {
            WeCampusApi.getOrganizationFans(OrganizationHomepageActivity.this, mId, this, this);
        }
    }*/

    //Follow organization handler
    private class FollowOrganizationHandler implements Response.Listener<Organization>, Response.ErrorListener {
        private Activity ac;

        public FollowOrganizationHandler(Activity activity) {
            this.ac = activity;
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void onResponse(Organization organization) {
            if(organization.can_follow) {
                Toast.makeText(ac, getResources().getString(R.string.unfollow_success), Toast.LENGTH_SHORT).show();
                tvLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_list_activity_like_un), null,
                    null, null);
            } else {
                Toast.makeText(ac, getResources().getString(R.string.follow_success), Toast.LENGTH_SHORT).show();
                tvLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_list_activity_like_sl), null,
                    null, null);
            }
        }

        public void followOrganization(boolean can_follow) {
            if(can_follow) {
                WeCampusApi.followOrganization(OrganizationHomepageActivity.this, mId, this, this);
            } else {
                WeCampusApi.unfollowOrganization(OrganizationHomepageActivity.this, mId, this, this);
            }
        }
    }
}
