package com.westudio.wecampus.ui.square;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by martian on 13-12-5.
 */
public abstract class BaseSearchAdapter<T> extends BaseAdapter implements Response.ErrorListener {

    protected ArrayList<T> mList;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected SearchListAttacher mAttacher;

    public BaseSearchAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mList = new ArrayList<T>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addAll(ArrayList<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        mAttacher.setStatus(SearchListAttacher.Status.NO_RESULT);
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView textName;
        ImageLoader.ImageContainer imageRequest;
    }

    public void setAttacher(SearchListAttacher attacher) {
        this.mAttacher = attacher;
    }
}
