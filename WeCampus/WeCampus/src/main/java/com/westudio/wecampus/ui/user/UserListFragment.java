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
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.view.LoadingFooter;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

/**
 * Created by martian on 13-12-19.
 */
public class UserListFragment extends SherlockFragment implements Response.Listener<User.UserListData>,
        Response.ErrorListener {
    public static final String USER_LIST_TYPE = "user_list_type";
    public static final String USER_OR_ACTIVITY_ID = "user_or_activity_id";
    public static final int PARTICIPATES = 0;
    public static final int FANS = 1;
    public static final int FOLLOWERS = 2;


    protected ListView mUserList;
    protected LoadingFooter mLoadingFooter;
    protected PullToRefreshAttacher mPullToRefreshAttacher;

    protected UserListAdapter mAdapter;

    protected int mPage = 1;
    protected int mType = PARTICIPATES;
    protected int mUserOrActivitiyId;

    public static UserListFragment newInstance(Bundle args) {
        UserListFragment f = new UserListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = savedInstanceState.getInt(USER_LIST_TYPE);
        mUserOrActivitiyId = savedInstanceState.getInt(USER_OR_ACTIVITY_ID);
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
        switch (mType) {
            case PARTICIPATES:
                WeCampusApi.getActivityParticipantsWithId(this, mUserOrActivitiyId, this, this);
                break;
            case FANS:
                // TODO 获取粉丝列表
                break;
            case FOLLOWERS:
                // TODO 获取关注列表
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        // TODO 错误提示
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
            mPage++;
        }
    }
}
