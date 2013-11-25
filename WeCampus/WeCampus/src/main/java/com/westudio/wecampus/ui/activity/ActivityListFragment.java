package com.westudio.wecampus.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.ActivityDataHelper;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.adapter.CardsAnimationAdapter;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.ui.view.LoadingFooter;
import com.westudio.wecampus.util.ListViewUtils;
import com.westudio.wecampus.util.Utility;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

/**
 * Created by martian on 13-9-11.
 * Fragment that display the activity list
 */
public class ActivityListFragment extends BaseFragment implements OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor>, Response.ErrorListener, Response.Listener<Activity.ActivityRequestData> {

    public static final String ACTIVITY_ID = "activity_id";

    private PullToRefreshAttacher mPullToRefreshAttacher;
    private ActivityAdapter mAdapter;
    private ActivityDataHelper mDataHelper;

    private android.app.Activity activity;
    private ListView listView;

    private LoadingFooter loadingFooter;

    private boolean isRefreshFromTop = true;
    private int mPage = 1;

    public static ActivityListFragment newInstance(Bundle args) {
        ActivityListFragment f = new ActivityListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                requestFirstPageAndScrollToTop();
                return true;
            default:
               return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        listView = (ListView) view.findViewById(R.id.activity_list);
        View header = new View(activity);
        loadingFooter = new LoadingFooter(activity);
        mAdapter = new ActivityAdapter(activity, listView);
        CardsAnimationAdapter animationAdapter = new CardsAnimationAdapter(activity, mAdapter);
        animationAdapter.setListView(listView);

        listView.addHeaderView(header);
        listView.addFooterView(loadingFooter.getView());
        listView.setAdapter(animationAdapter);
        mPullToRefreshAttacher = ((MainActivity)activity).getPullToRefreshAttacher();

        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

        mDataHelper = new ActivityDataHelper(BaseApplication.getContext());
        getLoaderManager().initLoader(0, null, this);

        //Set the listview scroll listener
        listView.setOnScrollListener( new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(loadingFooter.getState() == LoadingFooter.State.Loading ||
                        loadingFooter.getState() == LoadingFooter.State.TheEnd ) {
                    return;
                }

                if(firstVisibleItem + visibleItemCount >= totalItemCount
                        && totalItemCount != 0
                        && totalItemCount != listView.getFooterViewsCount() +
                        listView.getFooterViewsCount() && mAdapter.getCount() > 0) {
                    requestActivity(mPage);
                }
            }
        });

        //Set the listview item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, ActivityDetailActivity.class);
                Activity ac = (Activity) mAdapter.getItem(position);
                intent.putExtra(ACTIVITY_ID, ac.id);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
        });

        //Set the listview long click listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRefreshStarted(View view) {
        requestActivity(1);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return mDataHelper.getCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.changeCursor(cursor);
        if(cursor != null && cursor.getCount() == 0) {
            requestActivity(mPage);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.changeCursor(null);

    }

    /**
     * Request activity for each page
     * @param page
     */
    private void requestActivity(final int page) {
        isRefreshFromTop = page == 1;
        if(!mPullToRefreshAttacher.isRefreshing() && isRefreshFromTop) {
            mPullToRefreshAttacher.setRefreshing(true);
        }

        WeCampusApi.getActivityList(this, page, this, this);
    }

    /**
     * Request the first page activity and listview scroll to top
     */
    private void requestFirstPageAndScrollToTop() {
        ListViewUtils.smoothScrollListViewToTop(listView);
        requestActivity(1);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Utility.log("error", volleyError.getMessage());
    }

    @Override
    public void onResponse(final Activity.ActivityRequestData activityListRequestData) {
        if(activityListRequestData.getObjects().size() == 0) {
            loadingFooter.setState(LoadingFooter.State.TheEnd);
        } else {
            Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... params) {
                    if(isRefreshFromTop) {
                        mDataHelper.deleteAll();
                    }

                    mPage ++;
                    mDataHelper.bulkInsert(activityListRequestData.getObjects());
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    mPullToRefreshAttacher.setRefreshComplete();
                }
            });
        }
    }
}
