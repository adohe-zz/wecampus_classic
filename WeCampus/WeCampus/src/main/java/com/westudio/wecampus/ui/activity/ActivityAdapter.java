package com.westudio.wecampus.ui.activity;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.util.Utility;

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

        ViewHolder holder = getHolder(view);

        if(holder != null) {
            Activity activity = Activity.fromCursor(mCursor);

            holder.text_title.setText(activity.getTitle());
            holder.text_time.setText("test");
            holder.text_location.setText("test");
            holder.text_tag.setText(Utility.getActivityCategoryAccordingChannelId(activity.getChannel_Id()));
            holder.text_like.setText(String.valueOf(activity.getLike()));
        }
    }

    private ViewHolder getHolder(final View view) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();

        if(viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        return viewHolder;
    }

    private class ViewHolder {

        ImageView imageView;
        TextView text_tag;
        TextView text_title;
        TextView text_time;
        TextView text_location;
        TextView text_like;

        public ViewHolder(View view) {
            this.imageView = (ImageView)view.findViewById(R.id.activity_list_item_image);
            this.text_tag = (TextView)view.findViewById(R.id.activity_list_item_tag);
            this.text_title = (TextView)view.findViewById(R.id.activity_list_item_title);
            this.text_time = (TextView)view.findViewById(R.id.activity_list_item_time);
            this.text_location = (TextView)view.findViewById(R.id.activity_list_item_location);
            this.text_like = (TextView)view.findViewById(R.id.activity_list_item_like);
        }
    }
}
