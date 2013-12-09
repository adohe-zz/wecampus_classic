package com.westudio.wecampus.ui.square;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.organiztion.OrganizationActivityAdapter;


/**
 * Created by martian on 13-12-3.
 */
public class SearchActivityAdapter extends OrganizationActivityAdapter implements
        Response.Listener<ActivityList.RequestData>, Response.ErrorListener{
    private boolean isLastPage;
    private ImageView mEmptyImage;
    private ProgressBar mProgressBar;
    public SearchActivityAdapter(Context context) {
        super(context);
    }

    public void requestData(String keywords, int page) {
        if (page == 1) {
            mProgressBar.setVisibility(View.VISIBLE);
            mEmptyImage.setVisibility(View.GONE);
        }
        WeCampusApi.searchActivity(mContext, page, keywords, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        mEmptyImage.setImageResource(R.drawable.search_no_result);
        mProgressBar.setVisibility(View.GONE);
        mEmptyImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(ActivityList.RequestData data) {
        mProgressBar.setVisibility(View.GONE);
        mEmptyImage.setVisibility(View.VISIBLE);
        isLastPage = data.getObjects().isEmpty();
        addAll(data.getObjects());
        if (activityLists.isEmpty()) {
            mEmptyImage.setImageResource(R.drawable.search_no_result);
        }
    }

    public void setEmptyImage(ImageView image) {
        mEmptyImage = image;
    }

    public void setProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }

    public void clear() {
        activityLists.clear();
        notifyDataSetChanged();
    }
}
