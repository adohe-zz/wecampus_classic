package com.westudio.wecampus.ui.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.ui.base.BasePageListFragment;
import com.westudio.wecampus.util.HttpUtil;
import com.westudio.wecampus.util.Utility;

/**
 * Created by nankonami on 13-12-11.
 */
public class FollowActivityFragment extends BasePageListFragment<ActivityList.RequestData> {

    private int uid;

    public static FollowActivityFragment newInstance(Bundle args) {
        FollowActivityFragment fragment = new FollowActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        uid = bundle.getInt(ListActivity.USER_ID, -1);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArgument();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        loadFirstPage();
        return contentView;
    }

    @Override
    protected String getRequestUrl() {
        return HttpUtil.getUserByIdWithOp(uid, HttpUtil.UserOp.FACTIVITY, null, 0);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_list_object;
    }

    @Override
    protected int getListViewId() {
        return R.id.object_list;
    }

    @Override
    protected UserActivityAdapter getAdapter() {
        return (UserActivityAdapter)super.getAdapter();
    }

    @Override
    protected BaseAdapter newAdapter() {
        return new UserActivityAdapter(getActivity());
    }

    @Override
    protected Class getResponseDataClass() {
        return ActivityList.RequestData.class;
    }

    @Override
    protected void processResponseData(ActivityList.RequestData data) {
    }

    @Override
    protected void updateUI(ActivityList.RequestData data) {
        Utility.log("size", data.getObjects().size());
        getAdapter().addAll(data.getObjects());
    }
}
