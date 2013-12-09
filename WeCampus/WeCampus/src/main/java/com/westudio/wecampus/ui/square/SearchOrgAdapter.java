package com.westudio.wecampus.ui.square;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.ImageUtil;

/**
 * Created by martian on 13-12-5.
 */
public class SearchOrgAdapter extends BaseSearchAdapter<Organization> implements
        Response.Listener<Organization.OrganizationRequestData>, Response.ErrorListener{
    private Drawable defalutDrawable;
    private boolean isLastPage;

    public SearchOrgAdapter(Context context) {
        super(context);
        defalutDrawable = new ColorDrawable(context.getResources().getColor(R.color.default_org_avatar));
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
            viewHolder.imageView.setImageDrawable(defalutDrawable);
        } else {
            WeCampusApi.requestImage(org.getAvatar(),
                    WeCampusApi.getImageListener(viewHolder.imageView, defalutDrawable, defalutDrawable));
        }

        viewHolder.textName.setText(org.getName());

        return convertView;
    }

    public void requestData(String keywords, int page) {
        if (page == 1) {
            mProgressBar.setVisibility(View.VISIBLE);
            mEmptyImage.setVisibility(View.GONE);
        }

        WeCampusApi.searchOrgs(mContext, page, keywords, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        mEmptyImage.setImageResource(R.drawable.search_no_result);
        mProgressBar.setVisibility(View.GONE);
        mEmptyImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(Organization.OrganizationRequestData data) {
        mProgressBar.setVisibility(View.GONE);
        mEmptyImage.setVisibility(View.VISIBLE);
        if (mList.isEmpty()) {
            mEmptyImage.setImageResource(R.drawable.search_no_result);
        }
        isLastPage = data.getObjects().isEmpty();
        addAll(data.getObjects());

    }
}