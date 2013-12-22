package com.westudio.wecampus.ui.user;

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
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.Constants;

import java.util.ArrayList;


public class UserListAdapter extends BaseAdapter {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<User> data;

    protected Drawable defaultDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));

    public UserListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        data = new ArrayList<User>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
            view = mLayoutInflater.inflate(R.layout.row_user_list, parent, false);
            viewHolder.avatar = (ImageView)view.findViewById(R.id.user_avatar);
            viewHolder.nickname = (TextView)view.findViewById(R.id.user_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        User user = data.get(position);
        if(viewHolder.imageRequest != null) {
            viewHolder.imageRequest.cancelRequest();
        }
        if(Constants.IMAGE_NOT_FOUND.equals(user.avatar)) {

        } else {
            viewHolder.imageRequest = WeCampusApi.requestImage(user.avatar, WeCampusApi.getImageListener(viewHolder.avatar,
                    defaultDrawable, defaultDrawable));
        }
        viewHolder.nickname.setText(user.nickname);
        return view;
    }

    public void addAll(ArrayList<User> users) {
        data.addAll(users);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView nickname;
        ImageLoader.ImageContainer imageRequest;
    }
}
