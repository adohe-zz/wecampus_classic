package com.westudio.wecampus.ui.organiztion;

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
import com.westudio.wecampus.util.DateUtil;
import com.westudio.wecampus.util.ImageUtil;

import java.util.ArrayList;

/**
 * Created by nankonami on 13-11-28.
 */
public class OrganizationActivityAdapter extends BaseAdapter {

    private ArrayList<ActivityList> activityLists;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private Drawable defaultDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));

    public OrganizationActivityAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        activityLists = new ArrayList<ActivityList>();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.row_org_activity_list, parent, false);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.activity_list_item_image);
            viewHolder.text_tag = (TextView)convertView.findViewById(R.id.activity_list_item_tag);
            viewHolder.text_title = (TextView)convertView.findViewById(R.id.activity_list_item_title);
            viewHolder.text_time = (TextView)convertView.findViewById(R.id.activity_list_item_time);
            viewHolder.text_location = (TextView)convertView.findViewById(R.id.activity_list_item_location);
            viewHolder.text_like = (TextView)convertView.findViewById(R.id.activity_list_item_like);
            viewHolder.text_summary = (TextView)convertView.findViewById(R.id.activity_list_item_summary);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(viewHolder.imageRequest != null) {
            viewHolder.imageRequest.cancelRequest();
        }
        ActivityList activityList = (ActivityList)getItem(position);
        if(activityList.image.equals(ImageUtil.IMAGE_NOT_FOUND)) {
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.text_summary.setVisibility(View.VISIBLE);
            viewHolder.text_summary.setText(activityList.summary);
        } else {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.imageRequest = WeCampusApi.requestImage(activityList.image, WeCampusApi.getImageListener(viewHolder.imageView,
                    defaultDrawable, defaultDrawable));
            viewHolder.text_summary.setVisibility(View.GONE);
        }
        viewHolder.text_title.setText(activityList.title);
        viewHolder.text_time.setText(DateUtil.getActivityTime(mContext, activityList.begin, activityList.end));
        viewHolder.text_location.setText(activityList.location);
        viewHolder.text_tag.setText(activityList.category);
        String color = BaseApplication.categoryMapping.get(activityList.category);
        Drawable drawable = new ColorDrawable(Color.parseColor(color));
        viewHolder.text_tag.setBackgroundDrawable(drawable);
        viewHolder.text_like.setText(String.valueOf(activityList.count_of_fans));

        return convertView;
    }

    public void addAll(ArrayList<ActivityList> activityLists) {
        this.activityLists.addAll(activityLists);
        notifyDataSetChanged();
    }

    static class ViewHolder {
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
