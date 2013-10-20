package com.westudio.wecampus.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.westudio.wecampus.net.GsonRequest;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.adapter.CardsAnimationAdapter;
import com.westudio.wecampus.ui.view.LoadingFooter;

/**
 * Created by nankonami on 13-9-18.
 */
public abstract class BasePageListFragment<T> extends BaseFragment {
    private BaseAdapter baseAdapter;

    protected ListView listView;
    protected int page = 1;
    protected LoadingFooter loadingFooter;

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
        loadingFooter.setState(LoadingFooter.State.Loading);
        loadData(page + 1);
    }

    protected void loadData(final int page) {
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
     * Get the list view adapter
     * @return
     */
    protected abstract BaseAdapter newAdapter();

    /**
     * Get the response data class
     * @return
     */
    protected abstract Class getResponseDataClass();

    /**
     * Process the response data
     */
    protected abstract void processResponseData();
}
