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
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.data.model.Advertisement;
import com.westudio.wecampus.data.model.School;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.util.BitmapLruCache;
import com.westudio.wecampus.util.CacheUtil;
import com.westudio.wecampus.util.HttpUtil;
import com.westudio.wecampus.util.database.ResponseDiskCache;

/**
 * Created by nankonami on 13-9-9.
 */
public class WeCampusApi {

    //The default memory cache size
    private static final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager)BaseApplication.getContext().
            getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 3;

    private static RequestQueue requestQueue = newRequestQueue();
    private static ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapLruCache(MEM_CACHE_SIZE));

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

    /**
     * Open the disk cache for the response
     * @return
     */
    private static Cache openCache() {
        return new ResponseDiskCache(CacheUtil.getExternalCacheDir(BaseApplication.getContext()), 10*1024*1024);
    }

    /**
     * When you call the Volley.newRequestQueue you have no
     * need to call the start method
     * @return
     */
    private static RequestQueue newRequestQueue() {
        RequestQueue queue = new RequestQueue(openCache(), new BasicNetwork(new HurlStack()));
        queue.start();

        return queue;
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

        Request request = new GsonRequest<Activity.ActivityRequestData>(Request.Method.GET, HttpUtil.URL_GET_ACTIVITIES, Activity.ActivityRequestData.class,
                listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * GET ACTIVITY DETAIL BY ID
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void getActivityById(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new GsonRequest<Activity.ActivityRequestData>(Request.Method.GET, HttpUtil.getActivityByIdWithOp(id, HttpUtil.ActivityOp.DETAIL),
                listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * LIKE ACTIVITY
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void likeActivityWithId(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new GsonRequest<Activity.ActivityRequestData>(Request.Method.POST, HttpUtil.getActivityByIdWithOp(id, HttpUtil.ActivityOp.LIKE),
                listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * DISLIKE ACTIVITY
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void disLikeActivityWithId(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new GsonRequest<Activity.ActivityRequestData>(Request.Method.POST, HttpUtil.getActivityByIdWithOp(id, HttpUtil.ActivityOp.DISLIKE),
                listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get school list
     * @param tag
     * @param page
     * @param listener
     * @param errorListener
     */
    public static void getSchoolList(Object tag, final int page, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new GsonRequest<School.SchoolRequestData>(Request.Method.GET, HttpUtil.URL_GET_SCHOOLS, School.SchoolRequestData.class, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Login
     * @param tag
     * @param account
     * @param pwd
     * @param listener
     * @param errorListener
     */
    public static void postLogin(Object tag, String account, String pwd, Response.Listener listener,
                                 Response.ErrorListener errorListener) {
        Request request = new CreateSessionRequest(Request.Method.POST, HttpUtil.URL_POST_SESSION,
                account, pwd, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    public static void postRegister(Object tag, String email, String nickname, String password,
                                    String gender, String schoolId, Response.Listener listener,
                                    Response.ErrorListener errorListener) {
        RegisterRequest.RegisterData data = new RegisterRequest.RegisterData();
        data.email = email;
        data.nickname = nickname;
        data.password = password;
        data.gender = gender;
        data.schoolId = schoolId;

        Request request = new RegisterRequest(Request.Method.POST, HttpUtil.URL_POST_REGISTER, data, listener, errorListener);

        if (tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    public static void getAdvertisement(Object tag, Response.Listener listener,
                                        Response.ErrorListener errorListener) {
        Request request = new GsonRequest<Advertisement.AdResultData>(Request.Method.GET,
                HttpUtil.URL_GET_ADVERTISEMENTS, Advertisement.AdResultData.class, listener, errorListener);
        if (tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Request Image from network
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
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if(response.getBitmap() != null) {
                    if(!isImmediate && defaultImageDrawable != null) {
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
                        imageView.setImageBitmap(response.getBitmap());
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

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

}
