package com.westudio.wecampus.ui.square;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.ImageUtil;

/**
 * Created by martian on 13-12-5.
 */
public class SearchOrgAdapter extends BaseSearchAdapter<Organization> {
    private Drawable defalutDrawable;

    public SearchOrgAdapter() {
        super();
        defalutDrawable = new ColorDrawable(Color.rgb(120, 120, 120));
    }
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.row_user_list, parent, false);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.user_avatar);
            viewHolder.textName = (TextView) convertView.findViewById(R.id.user_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(viewHolder.imageRequest != null) {
            viewHolder.imageRequest.cancelRequest();
        }

        Organization org = mList.get(i);
        if(org.avatar.equals(ImageUtil.IMAGE_NOT_FOUND)) {

        } else {
            WeCampusApi.requestImage(org.getAvatar(),
                    WeCampusApi.getImageListener(viewHolder.imageView, defalutDrawable, defalutDrawable));
        }

        viewHolder.textName.setText(org.getName());

        return convertView;
    }
}
