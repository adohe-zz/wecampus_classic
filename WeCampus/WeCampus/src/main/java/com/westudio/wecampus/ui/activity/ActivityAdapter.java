package com.westudio.wecampus.ui.activity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.data.model.Constants;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.util.DateUtil;

import java.util.HashMap;

/**
 * Created by martian on 13-9-11.
 */
public class ActivityAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflater;

    private ListView mListView;

    //The default image
    private Drawable defaultDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));

    private Context mContext;

    private HashMap<String, String> colors;

    public ActivityAdapter(Context context, ListView listView) {
        super(context, null, false);
        mContext = context;
        mLayoutInflater = ((android.app.Activity)context).getLayoutInflater();
        mListView = listView;
        colors = BaseApplication.getInstance().getCategoryColors();
    }

    @Override
    public Object getItem(int position) {
        return Activity.fromCursor(mCursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mLayoutInflater.inflate(R.layout.row_activity_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = getHolder(view);

        if(holder.imageRequest != null) {
            holder.imageRequest.cancelRequest();
        }

        view.setEnabled(!mListView.isItemChecked(cursor.getPosition()
                + mListView.getHeaderViewsCount()));

        Activity activity = Activity.fromCursor(cursor);
        if(Constants.IMAGE_NOT_FOUND.equals(activity.image)) {
            holder.imageView.setVisibility(View.GONE);
            holder.text_summary.setVisibility(View.VISIBLE);
            holder.text_summary.setText(activity.summary);
        } else {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageRequest = WeCampusApi.requestImage(activity.image, WeCampusApi.getImageListener(holder.imageView,
                defaultDrawable, defaultDrawable));
            holder.text_summary.setVisibility(View.GONE);
        }
        holder.text_title.setText(activity.title);
        holder.text_time.setText(DateUtil.getActivityTime(mContext, activity.begin, activity.end));
        holder.text_location.setText(activity.location);
        holder.text_tag.setText(activity.category);
        String color = context.getString(R.string.default_category_color);
        if (colors.containsKey(activity.category)) {
            color = colors.get(activity.category);
        }
        Drawable drawable = new ColorDrawable(Color.parseColor(color));
        holder.text_tag.setBackgroundDrawable(drawable);
        holder.text_like.setText(String.valueOf(activity.count_of_fans));
    }

    private ViewHolder getHolder(final View view) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();

        if(viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        return viewHolder;
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

        public ViewHolder(View view) {
            imageView = (ImageView)view.findViewById(R.id.activity_list_item_image);
            text_tag = (TextView)view.findViewById(R.id.activity_list_item_tag);
            text_title = (TextView)view.findViewById(R.id.activity_list_item_title);
            text_time = (TextView)view.findViewById(R.id.activity_list_item_time);
            text_location = (TextView)view.findViewById(R.id.activity_list_item_location);
            text_like = (TextView)view.findViewById(R.id.activity_list_item_like);
            text_summary = (TextView)view.findViewById(R.id.activity_list_item_summary);
        }
    }
}
