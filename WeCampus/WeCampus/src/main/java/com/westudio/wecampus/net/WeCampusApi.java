package com.westudio.wecampus.net;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.util.BitmapLruCache;

/**
 * Created by nankonami on 13-9-9.
 */
public class WeCampusApi {
    private final RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public WeCampusApi(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
    }
}
