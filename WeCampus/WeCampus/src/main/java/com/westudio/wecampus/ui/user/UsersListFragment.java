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
import android.widget.AbsListView;
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
import com.westudio.wecampus.ui.view.LoadingFooter;
import com.westudio.wecampus.util.Utility;
import com.woozzu.android.widget.IndexableListView;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

/**
 * Created by jam on 13-9-19.
 *
 * Fragment display user list
 */
public class UsersListFragment extends BaseFragment implements OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor>, Response.ErrorListener,
        Response.Listener<User.UserListData> {

    private PullToRefreshAttacher mPullToRefreshAttacher;
    private IndexableListView mUserList;
    private UserAdapter mAdapter;
    private UserDataHelper mDataHelper;
    private LoadingFooter loadingFooter;

    public static UsersListFragment newInstance() {
        UsersListFragment f = new UsersListFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        mUserList = (IndexableListView) view.findViewById(R.id.user_list);
        loadingFooter = new LoadingFooter(getActivity());

        //TODO
        View header = new View(getActivity());
        mUserList.addHeaderView(header);

        mAdapter = new UserAdapter(getActivity(), mUserList);
        mUserList.addFooterView(loadingFooter.getView());
        mUserList.setAdapter(mAdapter);

        mPullToRefreshAttacher = ((MainActivity)getActivity()).getPullToRefreshAttacher();
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

        mDataHelper = new UserDataHelper(getActivity());
        getLoaderManager().initLoader(1, null, this);

        mUserList.setOnScrollListener( new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(loadingFooter.getState() == LoadingFooter.State.Loading ||
                        loadingFooter.getState() == LoadingFooter.State.TheEnd ) {
                    return;
                }


            }
        });

        mAdapter = new UserAdapter(getActivity(), mUserList);
        mUserList.setFastScrollEnabled(true);
        mUserList.setAdapter(mAdapter);
        mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), UserHomepageActivity.class));
            }
        });

        return view;
    }

    private void requestUsers() {
        int id = BaseApplication.getInstance().getAccountMgr().getUserId();
        WeCampusApi.getFriends(this, id, this, this);
    }

    @Override
    public void onRefreshStarted(View view) {
        requestUsers();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return mDataHelper.getCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.changeCursor(cursor);
        if(cursor != null && cursor.getCount() == 0) {
            requestUsers();
        }
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
