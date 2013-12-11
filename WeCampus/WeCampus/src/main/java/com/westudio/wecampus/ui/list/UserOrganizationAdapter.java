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
import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.Constants;

import java.util.ArrayList;

/**
 * Created by nankonami on 13-12-11.
 */
public class UserOrganizationAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayouInflater;

    protected Drawable defaultDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));

    private ArrayList<Organization> data;

    public UserOrganizationAdapter(Context context) {
        mContext = context;
        mLayouInflater = LayoutInflater.from(mContext);
        data = new ArrayList<Organization>();
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
            view = mLayouInflater.inflate(R.layout.row_organization_list, parent, false);
            viewHolder.avatar = (ImageView)view.findViewById(R.id.org_list_avatar);
            viewHolder.name = (TextView)view.findViewById(R.id.org_list_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        Organization organization = data.get(position);
        if(viewHolder.imageRequest != null) {
            viewHolder.imageRequest.cancelRequest();
        }
        if(Constants.IMAGE_NOT_FOUND.equals(organization.avatar)) {

        } else {
            viewHolder.imageRequest = WeCampusApi.requestImage(organization.avatar, WeCampusApi.getImageListener(viewHolder.avatar,
                    defaultDrawable, defaultDrawable));
        }
        viewHolder.name.setText(organization.name);
        return view;
    }

    public void addAll(ArrayList<Organization> organizations) {
        this.data.addAll(organizations);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView name;
        ImageLoader.ImageContainer imageRequest;
    }
}
