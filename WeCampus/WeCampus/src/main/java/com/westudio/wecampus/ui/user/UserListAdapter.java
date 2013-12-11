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
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.Participants;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.Constants;
import com.westudio.wecampus.util.PinYin;

import java.util.ArrayList;

/**
 * Created by nankonami on 13-12-11.
 */
public class UserListAdapter extends BaseAdapter implements SectionIndexer {

    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Participants> data;

    protected Drawable defaultDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));

    public UserListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        data = new ArrayList<Participants>();
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

        Participants user = data.get(position);
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

    public void addAll(ArrayList<Participants> users) {
        data.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // not English nor Chinese
                    char c = ((User)getItem(j)).nickname.charAt(0);
                    if ((c < 19968 && c > 171941) && (c < 'a' && c > 'z')
                            && (c < 'A' && c > 'Z')) {
                        return j;
                    }
                } else {
                    if (PinYin.match(String.valueOf(((User)getItem(j)).nickname.charAt(0)),
                            mSections.charAt(i))) {
                        return j;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView nickname;
        ImageLoader.ImageContainer imageRequest;
    }
}
