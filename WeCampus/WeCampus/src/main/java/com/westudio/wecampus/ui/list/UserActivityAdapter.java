package com.westudio.wecampus.ui.list;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.util.Constants;
import com.westudio.wecampus.util.DateUtil;
import com.westudio.wecampus.util.ImageUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nankonami on 13-12-11.
 */
public class UserActivityAdapter extends BaseAdapter {

    protected ArrayList<ActivityList> activityLists;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    protected Drawable defaultDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));
    protected HashMap<String, String> colors;

    public UserActivityAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        activityLists = new ArrayList<ActivityList>();
        colors = BaseApplication.getInstance().getCategoryColors();
    }

    @Override
    public int getCount() {
        return activityLists.size();
    }

    @Override
    public Object getItem(int position) {
        return activityLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if(view == null) {
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.row_activity_list, parent, false);
            viewHolder.imageView = (ImageView)view.findViewById(R.id.activity_list_item_image);
            viewHolder.text_like = (TextView)view.findViewById(R.id.activity_list_item_like);
            viewHolder.text_location = (TextView)view.findViewById(R.id.activity_list_item_location);
            viewHolder.text_summary = (TextView)view.findViewById(R.id.activity_list_item_summary);
            viewHolder.text_tag = (TextView)view.findViewById(R.id.activity_list_item_tag);
            viewHolder.text_time = (TextView)view.findViewById(R.id.activity_list_item_time);
            viewHolder.text_title = (TextView)view.findViewById(R.id.activity_list_item_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        if(viewHolder.imageRequest != null) {
            viewHolder.imageRequest.cancelRequest();
        }
        ActivityList activityList = (ActivityList)getItem(position);
        if(Constants.IMAGE_NOT_FOUND.equals(activityList.image)) {
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.text_summary.setVisibility(View.VISIBLE);
            viewHolder.text_summary.setText(activityList.summary);
            viewHolder.text_title.setPadding(0, 0, 108, 0);
        } else {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.imageRequest = WeCampusApi.requestImage(activityList.image, WeCampusApi.getImageListener(viewHolder.imageView,
                    defaultDrawable, defaultDrawable));
            viewHolder.text_summary.setVisibility(View.GONE);
            viewHolder.text_title.setPadding(0, 0, 0, 0);
        }
        viewHolder.text_title.setText(activityList.title);
        viewHolder.text_time.setText(DateUtil.getActivityTime(mContext, activityList.begin, activityList.end));
        viewHolder.text_location.setText(activityList.location);
        viewHolder.text_tag.setText(activityList.category);
        String color = mContext.getString(R.string.default_category_color);
        if (colors.containsKey(activityList.category)) {
            color = colors.get(activityList.category);
        }
        Drawable drawable = new ColorDrawable(Color.parseColor(color));
        viewHolder.text_tag.setBackgroundDrawable(drawable);
        viewHolder.text_like.setText(String.valueOf(activityList.count_of_fans));

        return view;
    }

    public void addAll(ArrayList<ActivityList> activityLists) {
        this.activityLists.addAll(activityLists);
        notifyDataSetChanged();
    }

    private static class ViewHolder {

        ImageView imageView;
        TextView text_tag;
        TextView text_title;
        TextView text_time;
        TextView text_location;
        TextView text_like;
        TextView text_summary;
        ImageLoader.ImageContainer imageRequest;
    }
}
