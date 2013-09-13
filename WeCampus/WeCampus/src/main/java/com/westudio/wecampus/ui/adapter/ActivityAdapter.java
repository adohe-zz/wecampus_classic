package com.westudio.wecampus.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

/**
 * Created by nankonami on 13-9-14.
 */
public class ActivityAdapter extends CursorAdapter {

    private LayoutInflater layoutInflater;
    private ListView listView;

    public ActivityAdapter(Context context, ListView listView) {
        super(context, null, false);
        layoutInflater = ((Activity)context).getLayoutInflater();
        this.listView = listView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
