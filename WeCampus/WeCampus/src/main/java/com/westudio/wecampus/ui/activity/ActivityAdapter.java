package com.westudio.wecampus.ui.activity;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.modle.Activity;

/**
 * Created by martian on 13-9-11.
 */
public class ActivityAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;

    private ListView mListView;

    public ActivityAdapter(Context context, ListView listView) {
        super(context, null, false);
        mLayoutInflater = ((android.app.Activity)context).getLayoutInflater();
        mListView = listView;
    }

    @Override
    public Object getItem(int position) {
        mCursor.moveToPosition(position);
        return Activity.fromCursor(mCursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mLayoutInflater.inflate(R.layout.row_activity_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Activity ac = Activity.fromCursor(mCursor);
        TextView tv = (TextView) view.findViewById(R.id.text);
        tv.setText(ac.getId() + "");
    }
}
