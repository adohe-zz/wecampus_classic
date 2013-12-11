package com.westudio.wecampus.ui.base;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.GsonRequest;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.activity.ActivityListActivity;
import com.westudio.wecampus.ui.adapter.CardsAnimationAdapter;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.ui.view.LoadingFooter;
import com.westudio.wecampus.util.ListViewUtils;
import com.westudio.wecampus.util.Utility;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

/**
 * Created by nankonami on 13-9-18.
 */
public abstract class BasePageListFragment<T> extends BaseFragment implements
        PullToRefreshAttacher.OnRefreshListener {

    private BaseAdapter baseAdapter;

    protected ListView listView;
    protected int page = 1;
    protected LoadingFooter loadingFooter;

    private PullToRefreshAttacher mPullToRefreshAttacher;

    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewResId(), container, false);
        listView = (ListView)view.findViewById(getListViewId());
        View header = new View(getActivity());
        loadingFooter = new LoadingFooter(getActivity());

        listView.addHeaderView(header);
        listView.addFooterView(loadingFooter.getView());

        mPullToRefreshAttacher = ((BaseListActivity)mActivity).mPullToRefreshAttacher;
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) view.findViewById(R.id.base_page_ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

        baseAdapter = newAdapter();
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(BaseApplication.getContext(), baseAdapter);
        animationAdapter.setListView(listView);
        listView.setAdapter(animationAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(loadingFooter.getState() == LoadingFooter.State.Loading
                        || loadingFooter.getState() == LoadingFooter.State.TheEnd) {
                    return;
                }
                if(firstVisibleItem + visibleItemCount >= totalItemCount
                        && totalItemCount != 0
                        && totalItemCount != listView.getFooterViewsCount() +
                        listView.getFooterViewsCount() && listView.getCount() > 0) {
                    loadNextPage();
                }
            }
        });

        return view;
    }

    /**
     * Load the first page data
     */
    protected void loadFirstPage() {
        loadData(1);
    }

    /**
     * Load the next page data
     */
    protected void loadNextPage() {
        Utility.log("load", "next");
        loadingFooter.setState(LoadingFooter.State.Loading);
        loadData(page + 1);
    }

    /**
     * Load the first page and scroll to top position
     */
    protected void requestFirstPageAndScrollToTop() {
        ListViewUtils.smoothScrollListViewToTop(listView);
        loadFirstPage();
    }

    protected void loadData(final int page) {
        final boolean isRefreshFromTop = page == 1;
        if(!mPullToRefreshAttacher.isRefreshing() && isRefreshFromTop) {
            mPullToRefreshAttacher.setRefreshing(true);
        }

        WeCampusApi.requestPageData(mActivity, getRequestUrl(), getResponseDataClass(), new Response.Listener<T>() {
            @Override
            public void onResponse(final T t) {
                mPullToRefreshAttacher.setRefreshComplete();
                Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        processResponseData(t);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        updateUI(t);
                        if(isRefreshFromTop) {
                            mPullToRefreshAttacher.setRefreshComplete();
                        } else {
                            loadingFooter.setState(LoadingFooter.State.Idle, 3000);
                        }
                    }
                });
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(mActivity, R.string.network_problem, Toast.LENGTH_SHORT).show();
                        if(isRefreshFromTop) {
                            mPullToRefreshAttacher.setRefreshComplete();
                        } else {
                            loadingFooter.setState(LoadingFooter.State.Idle, 3000);
                        }
                    }
                }
        );
    }

    /**
     * Get the request url
     * @return
     */
    protected abstract String getRequestUrl();

    /**
     * Get the content view resource id
     * @return
     */
    protected abstract int getContentViewResId();

    /**
     * Get the listview id
     * @return
     */
    protected abstract int getListViewId();

    /**
     * Create the list view adapter
     * @return
     */
    protected abstract BaseAdapter newAdapter();

    /**
     * Get the list view adapter
     * @return
     */
    protected BaseAdapter getAdapter() {
        return baseAdapter;
    }

    /**
     * Get the response data class
     * @return
     */
    protected abstract Class getResponseDataClass();

    /**
     * Process the response data
     */
    protected abstract void processResponseData(T data);

    /**
     * Update the ui in the main thread
     * @param data
     */
    protected abstract void updateUI(T data);

    @Override
    public void onRefreshStarted(View view) {
        loadFirstPage();
    }
}
