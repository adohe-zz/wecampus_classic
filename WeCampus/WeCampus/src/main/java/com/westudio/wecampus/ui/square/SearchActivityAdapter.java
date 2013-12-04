package com.westudio.wecampus.ui.square;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.organiztion.OrganizationActivityAdapter;


/**
 * Created by martian on 13-12-3.
 */
public class SearchActivityAdapter extends OrganizationActivityAdapter implements
        Response.Listener<ActivityList.RequestData>, Response.ErrorListener{
    private boolean isLastPage;

    public SearchActivityAdapter(Context context) {
        super(context);
    }

    public void requestData(String keywords, int page) {
        WeCampusApi.searchActivity(mContext, page, keywords, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        //TODO
    }

    @Override
    public void onResponse(ActivityList.RequestData data) {
        isLastPage = data.getObjects().isEmpty();
        addAll(data.getObjects());
    }
}
