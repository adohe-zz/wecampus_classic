package com.westudio.wecampus.ui.list;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.ui.base.BasePageListFragment;

/**
 * Created by nankonami on 13-12-11.
 */
public class FollowActivityFragment extends BasePageListFragment<ActivityList.RequestData> {

    public static FollowActivityFragment newInstance(Bundle args) {
        FollowActivityFragment fragment = new FollowActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getRequestUrl() {
        return null;
    }

    @Override
    protected int getContentViewResId() {
        return 0;
    }

    @Override
    protected int getListViewId() {
        return 0;
    }

    @Override
    protected BaseAdapter newAdapter() {
        return null;
    }

    @Override
    protected Class getResponseDataClass() {
        return null;
    }

    @Override
    protected void processResponseData(ActivityList.RequestData data) {

    }
}
