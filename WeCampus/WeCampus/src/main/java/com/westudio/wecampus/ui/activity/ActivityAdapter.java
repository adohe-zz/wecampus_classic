package com.westudio.wecampus.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.westudio.wecampus.R;

import java.util.List;

/**
 * Created by martian on 13-9-11.
 */
public class ActivityAdapter extends BaseAdapter{
    private List<String> data;
    private Context context;

    public ActivityAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.row_activity_list, viewGroup, false);

        return view;
    }
}
