package com.westudio.wecampus.ui.square;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.ImageUtil;

/**
 * Created by martian on 13-12-8.
 */
public class SearchUserAdapter extends BaseSearchAdapter<User> implements
        Response.Listener<User.UserListData>, Response.ErrorListener{
    private Drawable defalutMaleDrawable;
    private Drawable defalutFemaleDrawable;
    private boolean isLastPage;

    public SearchUserAdapter(Context context) {
        super(context);
        defalutMaleDrawable = context.getResources().getDrawable(R.drawable.ic_default_male);
        defalutFemaleDrawable = context.getResources().getDrawable(R.drawable.ic_default_female);
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

        User user = mList.get(i);
        if(user.avatar.equals(ImageUtil.IMAGE_NOT_FOUND)) {
            viewHolder.imageView.setImageDrawable(user.gender.equals("女") ? defalutFemaleDrawable :
                    defalutMaleDrawable);
        } else if (user.gender.equals("女")) {
            WeCampusApi.requestImage(user.avatar,
                    WeCampusApi.getImageListener(viewHolder.imageView, defalutFemaleDrawable,
                            defalutFemaleDrawable));
        } else {
            WeCampusApi.requestImage(user.avatar,
                    WeCampusApi.getImageListener(viewHolder.imageView, defalutMaleDrawable,
                            defalutMaleDrawable));
        }

        viewHolder.textName.setText(user.name);

        return convertView;
    }

    public void requestData(String keywords, int page) {
        WeCampusApi.searchUser(mContext, page, keywords, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        //TODO
    }

    @Override
    public void onResponse(User.UserListData data) {
        isLastPage = data.objects.isEmpty();
        addAll(data.objects);
    }
}
