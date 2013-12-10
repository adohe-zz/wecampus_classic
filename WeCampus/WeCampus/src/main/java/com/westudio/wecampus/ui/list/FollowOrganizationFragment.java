package com.westudio.wecampus.ui.list;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.ui.base.BasePageListFragment;

/**
 * Created by nankonami on 13-12-11.
 */
public class FollowOrganizationFragment extends BasePageListFragment<Organization.OrganizationRequestData> {

    public static FollowOrganizationFragment newInstance(Bundle args) {
        FollowOrganizationFragment fragment = new FollowOrganizationFragment();
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
    protected void processResponseData(Organization.OrganizationRequestData data) {

    }
}
