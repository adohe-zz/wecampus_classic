package com.westudio.wecampus.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.view.LoadingFooter;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

/**
 * Created by martian on 13-12-19.
 */
public class UserListFragment extends SherlockFragment implements Response.Listener<User.UserListData>,
        Response.ErrorListener {

    protected ListView mUserList;
    protected LoadingFooter mLoadingFooter;
    protected PullToRefreshAttacher mPullToRefreshAttacher;

    protected UserListAdapter mAdapter;

    protected int mPage = 1;

    public static UserListFragment newInstance() {
        UserListFragment f = new UserListFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        mUserList = (ListView) view.findViewById(R.id.user_list);
        mLoadingFooter = new LoadingFooter(getActivity());
        mUserList.addFooterView(mLoadingFooter.getView());
        mUserList.setOnScrollListener(onScrollListener);
        mAdapter = new UserListAdapter(getActivity());
        mPullToRefreshAttacher = ((UserListActivity)getActivity()).getPullToRefreshAttacher();

        requestData(1);
        return view;
    }

    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {
            if(mLoadingFooter.getState() == LoadingFooter.State.Loading ||
                    mLoadingFooter.getState() == LoadingFooter.State.TheEnd ) {
                return;
            }

            if(firstVisibleItem + visibleItemCount >= totalItemCount
                    && totalItemCount != 0
                    && totalItemCount != mUserList.getFooterViewsCount() +
                    mUserList.getFooterViewsCount() && mUserList.getAdapter().getCount() > 0) {
                requestData(mPage);
            }
        }
    };

    public void requestData(int page) {
        //TODO
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        // TODO
        mLoadingFooter.setState(LoadingFooter.State.TheEnd);
        mPullToRefreshAttacher.setRefreshComplete();
    }

    @Override
    public void onResponse(User.UserListData userListData) {
        if (userListData.objects.isEmpty()) {
            mLoadingFooter.setState(LoadingFooter.State.TheEnd);
        } else {
            mAdapter.addAll(userListData.objects);
            mPullToRefreshAttacher.setRefreshComplete();
        }
    }
}
