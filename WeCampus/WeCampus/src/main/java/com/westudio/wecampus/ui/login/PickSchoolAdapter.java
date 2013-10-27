package com.westudio.wecampus.ui.login;

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
import com.westudio.wecampus.data.model.School;
import com.westudio.wecampus.net.WeCampusApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nankonami on 13-10-27.
 */
public class PickSchoolAdapter extends BaseAdapter {

    private Context context;
    private List<School> schoolList;
    private LayoutInflater layoutInflater;

    //The default image
    private Drawable defaultDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));

    public PickSchoolAdapter(Context context) {
        this.context = context;
        schoolList = new ArrayList<School>();
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        if(schoolList != null) {
            return schoolList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return schoolList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.school_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.school_icon);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.school_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(viewHolder.imageRequest != null) {
            viewHolder.imageRequest.cancelRequest();
        }

        School school = (School)getItem(position);
        viewHolder.imageRequest = WeCampusApi.requestImage(school.getIcon(), WeCampusApi.getImageListener(viewHolder.imageView,
                defaultDrawable, defaultDrawable));
        viewHolder.textView.setText(school.getName());

        return convertView;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public ImageLoader.ImageContainer imageRequest;
    }
}
