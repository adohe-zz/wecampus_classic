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
        Response.Listener<User.UserListData>{
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
            viewHolder.imageView.setImageDrawable("女".equals(user.gender) ? defalutFemaleDrawable :
                    defalutMaleDrawable);
        } else if ("女".equals(user.gender)) {
            WeCampusApi.requestImage(user.avatar,
                    WeCampusApi.getImageListener(viewHolder.imageView, defalutFemaleDrawable,
                            defalutFemaleDrawable));
        } else {
            WeCampusApi.requestImage(user.avatar,
                    WeCampusApi.getImageListener(viewHolder.imageView, defalutMaleDrawable,
                            defalutMaleDrawable));
        }

        viewHolder.textName.setText(user.nickname);

        return convertView;
    }

    public void requestData(String keywords, boolean searchNew) {
        if (searchNew) {
            mAttacher.page = 1;
        }
        if (mAttacher.page == 1) {
            mAttacher.setStatus(SearchListAttacher.Status.LOADING);
        } else {
            mAttacher.setStatus(SearchListAttacher.Status.LOADING_MORE);
        }
        WeCampusApi.searchUser(mContext, mAttacher.page, keywords, this, this);
    }

    @Override
    public void onResponse(User.UserListData data) {
        isLastPage = data.objects.isEmpty();
        if (isLastPage && mList.isEmpty()) {
            mAttacher.setStatus(SearchListAttacher.Status.NO_RESULT);
        } else {
            mAttacher.page++;
        }

        if (isLastPage) {
            mAttacher.setStatus(SearchListAttacher.Status.LOADING_END);
        }
        addAll(data.objects);
    }
}
