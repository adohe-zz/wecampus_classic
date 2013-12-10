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

    private SearchListAttacher attacher;
    public SearchActivityAdapter(Context context) {
        super(context);
    }

    public void requestData(String keywords, int page) {
        if (page == 1) {
            attacher.setStatus(SearchListAttacher.Status.LOADING);
        } else {
            attacher.setStatus(SearchListAttacher.Status.LOADING_MORE);
        }
        WeCampusApi.searchActivity(mContext, page, keywords, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        attacher.setStatus(SearchListAttacher.Status.NO_RESULT);
    }

    @Override
    public void onResponse(ActivityList.RequestData data) {
        isLastPage = data.getObjects().isEmpty();
        addAll(data.getObjects());
        if (activityLists.isEmpty() && isLastPage) {
            attacher.setStatus(SearchListAttacher.Status.NO_RESULT);
        } else {
            attacher.page++;
        }
        if (isLastPage) {
            attacher.setStatus(SearchListAttacher.Status.LOADING_END);
        }
    }

    public void setAttacher(SearchListAttacher attacher) {
        this.attacher = attacher;
    }

    public void clear() {
        activityLists.clear();
        notifyDataSetChanged();
    }
}
