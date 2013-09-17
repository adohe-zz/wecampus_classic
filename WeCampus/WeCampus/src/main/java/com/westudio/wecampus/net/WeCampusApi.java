package com.westudio.wecampus.net;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.util.BitmapLruCache;
import com.westudio.wecampus.util.HttpUtil;

/**
 * Created by nankonami on 13-9-9.
 */
public class WeCampusApi {

    private static RequestQueue requestQueue = newRequestQueue();
    private ImageLoader imageLoader;

    public WeCampusApi() {
        this.imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
    }

    /**
     * Add common request parameter
     * @return
     */
    private static Bundle getBundle() {
        Bundle bundle = new Bundle();

        return bundle;
    }

    /**
     * GET ACTIVITY LIST
     * @param page
     * @param listener
     * @param errorListener
     */
    public static void getActivityList(final int page, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Bundle bundle = getBundle();

        requestQueue.add(new GsonRequest<Activity.ActivityRequestData>(Request.Method.GET, HttpUtil.getActivityList(bundle), Activity.ActivityRequestData.class,
                listener, errorListener));
    }

    /**
     * When you call the Volley.newRequestQueue you have no
     * need to call the start method
     * @return
     */
    private static final RequestQueue newRequestQueue() {
        return Volley.newRequestQueue(BaseApplication.getContext());
    }
}
