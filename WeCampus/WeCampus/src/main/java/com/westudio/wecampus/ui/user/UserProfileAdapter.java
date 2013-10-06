package com.westudio.wecampus.ui.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.westudio.wecampus.data.model.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nankonami on 13-10-6.
 */
public class UserProfileAdapter extends BaseAdapter {

    private Context context;
    private List<Activity> activityList;

    public UserProfileAdapter(Context context) {
        this.context = context;
        activityList = new ArrayList<Activity>();
    }

    @Override
    public int getCount() {
        return activityList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    private static class ViewHolder {

    }
}
