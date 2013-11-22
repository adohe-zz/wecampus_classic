package com.westudio.wecampus.ui.organiztion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.westudio.wecampus.R;

/**
 * Created by martian on 13-11-22.
 */
public class IntroAdapter extends BaseAdapter{

    private Context mContext;

    public IntroAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.row_organization_brief, viewGroup, false);
        return view;
    }
}
