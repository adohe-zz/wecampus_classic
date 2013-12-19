package com.westudio.wecampus.ui.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.UserDataHelper;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.ui.square.SearchActivity;
import com.westudio.wecampus.util.Utility;
import com.westudio.wecampus.util.WxShareTool;
import com.woozzu.android.widget.IndexableListView;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

/**
 * Created by jam on 13-9-19.
 *
 * 展示好友的的Fragment
 */
public class FriendsListFragment extends BaseFragment implements OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor>, Response.ErrorListener,
        Response.Listener<User.UserListData> {

    private PullToRefreshAttacher mPullToRefreshAttacher;
    private IndexableListView mUserList;
    private FriendsAdapter mAdapter;
    private UserDataHelper mDataHelper;

    public static FriendsListFragment newInstance() {
        FriendsListFragment f = new FriendsListFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);

        mUserList = (IndexableListView) view.findViewById(R.id.user_list);

        //TODO
        View introHeader = getActivity().getLayoutInflater().inflate(R.layout.friends_list_intro_header, null);
        mUserList.addHeaderView(introHeader);
        View searchHeader = getActivity().getLayoutInflater().inflate(R.layout.friends_list_search_header, null);
        mUserList.addHeaderView(searchHeader);

        mAdapter = new FriendsAdapter(getActivity(), mUserList);
        mUserList.setAdapter(mAdapter);

        mPullToRefreshAttacher = ((MainActivity)getActivity()).getPullToRefreshAttacher();
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

        mDataHelper = new UserDataHelper(getActivity());
        getLoaderManager().initLoader(1, null, this);

        mAdapter = new FriendsAdapter(getActivity(), mUserList);
        mUserList.setFastScrollEnabled(true);
        mUserList.setAdapter(mAdapter);
        mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    WxShareTool tool = new WxShareTool(getActivity());
                    tool.buildAppMessage().fireShareToWx(WxShareTool.ShareType.FRIENDS);
                } else if(position == 1) {
                    Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                    searchIntent.putExtra(SearchActivity.SELECTED_POSITION, 1);
                    startActivity(searchIntent);
                } else {
                    Intent intent = new Intent(getActivity(), UserHomepageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(UserHomepageActivity.USER, mAdapter.getItem(position - 2));
                    bundle.putBoolean(UserHomepageActivity.USER_LIST, true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        requestUsers();

        return view;
    }

    private void requestUsers() {
        if (!mPullToRefreshAttacher.isRefreshing()) {
            mPullToRefreshAttacher.setRefreshing(true);
        }
        int id = BaseApplication.getInstance().getAccountMgr().getUserId();
        WeCampusApi.getFriends(this, id, this, this);
    }

    @Override
    public void onRefreshStarted(View view) {
        requestUsers();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        int uid = BaseApplication.getInstance().getAccountMgr().getUserId();
        return mDataHelper.getFriendsCursorLoader(uid);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.changeCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.changeCursor(null);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }

    @Override
    public void onResponse(final User.UserListData userListData) {
        Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                mDataHelper.deleteAll();
                mDataHelper.bulkInsert(userListData.objects);

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
