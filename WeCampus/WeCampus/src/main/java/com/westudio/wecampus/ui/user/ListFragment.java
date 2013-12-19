package com.westudio.wecampus.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseFragment;
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
public class ListFragment extends BaseFragment implements OnRefreshListener,
        Response.ErrorListener,
        Response.Listener<User.UserListData> {

    private int id;

    private PullToRefreshAttacher mPullToRefreshAttacher;
    private IndexableListView mUserList;
    private UserListAdapter mAdapter;

    public static ListFragment newInstance(Bundle args) {
        ListFragment f = new ListFragment();
        f.setArguments(args);
        return f;
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        id = bundle.getInt(UserListActivity.ACTIVITY_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArgument();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);

        mUserList = (IndexableListView) view.findViewById(R.id.user_list);

        mAdapter = new UserListAdapter(getActivity());
        mUserList.setAdapter(mAdapter);

        mPullToRefreshAttacher = ((UserListActivity)getActivity()).getPullToRefreshAttacher();
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

        mAdapter = new UserListAdapter(getActivity());
        mUserList.setFastScrollEnabled(true);
        mUserList.setAdapter(mAdapter);
        mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UserHomepageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(UserHomepageActivity.USER_ID, ((User)mAdapter.getItem(position)).id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        requestUsers();
        return view;
    }

    private void requestUsers() {
        WeCampusApi.getActivityParticipantsWithId(this, id, this, this);
        mPullToRefreshAttacher.setRefreshing(true);
    }

    @Override
    public void onRefreshStarted(View view) {
        requestUsers();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }


    @Override
    public void onResponse(User.UserListData data) {
        Utility.log("size", data.getObjects().size());
        mPullToRefreshAttacher.setRefreshComplete();
        mAdapter.addAll(data.getObjects());
    }
}
