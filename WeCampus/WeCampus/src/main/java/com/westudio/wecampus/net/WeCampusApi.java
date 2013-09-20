package com.westudio.wecampus.net;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.util.BitmapLruCache;
import com.westudio.wecampus.util.CacheUtil;
import com.westudio.wecampus.util.HttpUtil;

import java.io.File;

/**
 * Created by nankonami on 13-9-9.
 */
public class WeCampusApi {

    //The default memory cache size
    private static final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager)BaseApplication.getContext().
            getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 3;

    private static RequestQueue requestQueue = newRequestQueue();
    private static ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapLruCache(MEM_CACHE_SIZE));

    private static DiskBasedCache diskBasedCache = (DiskBasedCache)requestQueue.getCache();

    private WeCampusApi() {
    }

    /**
     * Add common request parameter
     * @return
     */
    private static Bundle getBundle() {
        Bundle bundle = new Bundle();

        return bundle;
    }

    //Open the disk cache
    private static Cache openCache() {
        return new DiskBasedCache(CacheUtil.getExternalCacheDir(BaseApplication.getContext()), 10 * 1024 * 1024);
    }

    /**
     * When you call the Volley.newRequestQueue you have no
     * need to call the start method
     * @return
     */
    private static final RequestQueue newRequestQueue() {
        RequestQueue queue = new RequestQueue(openCache(), new BasicNetwork(new HurlStack()));
        queue.start();
        return queue;
    }

    /**
     * Get cached image from the file
     * @param url
     * @return
     */
    public static File getCachedImage(String url) {
        return diskBasedCache.getFileForKey(url);
    }

    /**
     * GET ACTIVITY LIST
     * @param page
     * @param listener
     * @param errorListener
     */
    public static void getActivityList(Object tag, final int page, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Bundle bundle = getBundle();

        Request request = new GsonRequest<Activity.ActivityRequestData>(Request.Method.GET, HttpUtil.getActivityList(bundle), Activity.ActivityRequestData.class,
                listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Common network request api
     * @param request
     * @param tag
     */
    public static void executeRequest(Request request, Object tag) {
        if(tag != null) {
            request.setTag(tag);
        }

        requestQueue.add(request);
    }

    public static void login(Object tag, String email, String pwd, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Bundle bundle = getBundle();

        Request request = new GsonRequest<User>(Request.Method.GET, null, User.class, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Request Image from net
     * @param imageUrl
     * @param listener
     * @return
     */
    public static ImageLoader.ImageContainer requestImage(String imageUrl, ImageLoader.ImageListener listener) {
        return requestImage(imageUrl, listener, 0, 0);
    }

    /**
     * Request Image from net
     * @param imageUrl
     * @param listener
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static ImageLoader.ImageContainer requestImage(String imageUrl, ImageLoader.ImageListener listener,
                int maxWidth, int maxHeight) {
        return imageLoader.get(imageUrl, listener, maxWidth, maxHeight);
    }

    /**
     * The default image listener
     * @param imageView
     * @param defaultImageDrawable
     * @param errorImageDrawable
     * @return
     */
    public static ImageLoader.ImageListener getImageListener(final ImageView imageView, final Drawable defaultImageDrawable,
                final Drawable errorImageDrawable) {
        return new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean b) {
                if(response.getBitmap() != null) {
                    if(!b && defaultImageDrawable != null) {
                        TransitionDrawable transitionDrawable = new TransitionDrawable(
                                new Drawable[]{
                                        defaultImageDrawable,
                                        new BitmapDrawable(BaseApplication.getContext().getResources(), response.getBitmap())
                                }
                        );
                        transitionDrawable.setCrossFadeEnabled(true);
                        imageView.setImageDrawable(transitionDrawable);
                        transitionDrawable.startTransition(100);
                    } else {
                        imageView.setImageDrawable(defaultImageDrawable);
                    }
                } else if(defaultImageDrawable != null) {
                    imageView.setImageDrawable(defaultImageDrawable);
                }
            }
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(errorImageDrawable != null) {
                    imageView.setImageDrawable(errorImageDrawable);
                }
            }
        };
    }

}
