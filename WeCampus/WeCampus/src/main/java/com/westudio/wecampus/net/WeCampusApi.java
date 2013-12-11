package com.westudio.wecampus.net;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
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
import com.westudio.wecampus.data.model.ActivityCategory;
import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.data.model.Advertisement;
import com.westudio.wecampus.data.model.OrgFans;
import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.data.model.Participants;
import com.westudio.wecampus.data.model.School;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.util.BitmapLruCache;
import com.westudio.wecampus.util.CacheUtil;
import com.westudio.wecampus.util.HttpUtil;
import com.westudio.wecampus.util.ResponseDiskCache;

/**
 * Created by nankonami on 13-9-9.
 */
public class WeCampusApi {

    //The default memory cache size
    private static final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager)BaseApplication.getContext().
            getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 3;

    private static RequestQueue requestQueue = newRequestQueue();
    public static RequestQueue imageRequestQueue = newRequestQueueForImage();

    private static ImageLoader imageLoader = new ImageLoader(imageRequestQueue, new BitmapLruCache(MEM_CACHE_SIZE));

    private WeCampusApi() {
    }

    /**
     * Open Disk Cache for response
     * @return
     */
    private static Cache openCache() {
        return new DiskBasedCache(CacheUtil.getExternalCacheDir(BaseApplication.getContext()), 10*1024*1024);
    }

    /**
     * Open the disk cache for the image request
     * @return
     */
    private static Cache openCacheForImage() {
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
     * Get the request queue for image
     */
    private static RequestQueue newRequestQueueForImage() {
        RequestQueue queue = new RequestQueue(openCacheForImage(), new BasicNetwork(new HurlStack()));
        queue.start();

        return queue;
    }

    public static void cancelRequest(Object tag) {
        requestQueue.cancelAll(tag);
    }

    /**
     * GET ACTIVITY LIST
     * @param page
     * @param listener
     * @param errorListener
     */
    public static void getActivityList(Object tag, final int page, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new GsonRequest<Activity.ActivityRequestData>(Request.Method.GET,
                HttpUtil.getActivityByIdWithOp(0, HttpUtil.ActivityOp.LIST, page),
                Activity.ActivityRequestData.class, listener, errorListener);

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
        Request request = new AuthedGsonRequest<Activity>(Request.Method.GET,
                HttpUtil.getActivityByIdWithOp(id, HttpUtil.ActivityOp.DETAIL, 0),
                Activity.class, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the activity of one category
     * @param tag
     * @param page
     * @param category
     * @param listener
     * @param errorListener
     */
    public static void getActivityOfCategory(Object tag, final int page, final String category, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new GsonRequest<Activity.ActivityRequestData>(Request.Method.GET,
                HttpUtil.getActivityOfCategory(category, page),
                Activity.ActivityRequestData.class, listener, errorListener);

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
        Request request = new AuthedGsonRequest<Activity>(Request.Method.POST,
                HttpUtil.getActivityByIdWithOp(id, HttpUtil.ActivityOp.LIKE, 0),
                Activity.class, listener, errorListener);

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
        Request request = new AuthedGsonRequest<Activity>(Request.Method.POST,
                HttpUtil.getActivityByIdWithOp(id, HttpUtil.ActivityOp.DISLIKE, 0),
                Activity.class, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * JOIN ACTIVITY
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void joinActivityWithId(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest<Activity>(Request.Method.POST,
                HttpUtil.getActivityByIdWithOp(id, HttpUtil.ActivityOp.JOIN, 0),
                Activity.class, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * QUIT ACTIVITY
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void quitActivityWithId(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest<Activity>(Request.Method.POST,
                HttpUtil.getActivityByIdWithOp(id, HttpUtil.ActivityOp.QUIT, 0),
                Activity.class, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get Activity Participants with id
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void getActivityParticipantsWithId(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest<Participants.ParticipantsRequestData>(Request.Method.GET,
                HttpUtil.getActivityByIdWithOp(id, HttpUtil.ActivityOp.PARTICIPATE, 0),
                Participants.ParticipantsRequestData.class, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }

        requestQueue.add(request);
    }

    /**
     * Get Activity Category
     * @param tag
     * @param listener
     * @param errorListener
     */
    public static void getActivityCategory(Object tag, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new GsonRequest<ActivityCategory.CategoryRequestData>(Request.Method.GET, HttpUtil.getActivityCategory(),
                ActivityCategory.CategoryRequestData.class, listener, errorListener);

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

    /**
     * Request data page by page
     * @param tag
     * @param clazz
     * @param listener
     * @param errorListener
     */
    public static void requestPageData(Object tag, final String url, Class clazz, Response.Listener listener,
                                       Response.ErrorListener errorListener) {
        Request request = new GsonRequest(Request.Method.GET, url, clazz, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Logout
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void logout(Object tag, int id, Response.Listener listener,
                              Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest(Request.Method.DELETE,
                HttpUtil.URL_POST_SESSION + "/" + id, Object.class, listener, errorListener);

        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Register
     * @param tag
     * @param email
     * @param nickname
     * @param password
     * @param gender
     * @param schoolId
     * @param listener
     * @param errorListener
     */
    public static void postRegister(Object tag, String email, String nickname, String password,
                                    String gender, String schoolId, Response.Listener listener,
                                    Response.ErrorListener errorListener) {
        RegisterRequest.RegisterData data = new RegisterRequest.RegisterData();
        data.email = email;
        data.nickname = nickname;
        data.password = password;
        data.gender = gender;
        data.schoolId = schoolId;

        Request request = new RegisterRequest(Request.Method.POST, HttpUtil.URL_POST_USERS, data, listener, errorListener);

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

    public static void postUpdateAvatar(Object tag, String path, Response.Listener listener, Response.ErrorListener errorListener) {
        Request request = new UploadAvatarRequest(path, listener, errorListener);
        if (tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    public static void postUpdateProfile(Object tag, User user, Response.Listener listener, Response.ErrorListener errorListener) {
        Request request = new UpdateProfileRequest(user, listener, errorListener);
        if (tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the organization detail
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void getOrganization(Object tag, final int id, Response.Listener listener,
                                       Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest<Organization>(Request.Method.GET, HttpUtil.getOrganizationByIdWithOp(id,
                HttpUtil.OrganizationOp.DETAIL, 0, null),
                Organization.class, listener, errorListener);
        if (tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the organization's activities
     * @param tag
     * @param id
     * @param page
     * @param listener
     * @param errorListener
     */
    public static void getOrganizationActivity(Object tag, final int id, final int page, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new GsonRequest<ActivityList.RequestData>(Request.Method.GET, HttpUtil.getOrganizationByIdWithOp(id,
                HttpUtil.OrganizationOp.ACTIVITY, page, null),
                ActivityList.RequestData.class, listener, errorListener);
        if (tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Follow the organization
     * @param tag
     * @param id
     * @param errorListener
     */
    public static void followOrganization(Object tag, final int id, Response.Listener listener, Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest(Request.Method.POST, HttpUtil.getOrganizationByIdWithOp(id, HttpUtil.OrganizationOp.FOLLOW, 0, null),
                Organization.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Unfollow the organization
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void unfollowOrganization(Object tag, final int id, Response.Listener listener, Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest(Request.Method.POST, HttpUtil.getOrganizationByIdWithOp(id, HttpUtil.OrganizationOp.UNFOLLOW, 0, null),
                Organization.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the organization fans list
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void getOrganizationFans(Object tag, final int id, Response.Listener listener, Response.ErrorListener errorListener) {
        Request request = new GsonRequest<OrgFans.RequestData>(Request.Method.GET, HttpUtil.getOrganizationByIdWithOp(id, HttpUtil.OrganizationOp.FANS, 0, null),
                OrgFans.RequestData.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the friends list
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void getFriends(Object tag, final int id, Response.Listener listener,
                                  Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest(Request.Method.GET, HttpUtil.getUserByIdWithOp(id, HttpUtil.UserOp.FRIENDS, null),
                User.UserListData.class, listener, errorListener);
        if (tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the user joined activity list
     * @param tag
     * @param id
     * @param errorListener
     */
    public static void getUserJActivity(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest(Request.Method.GET, HttpUtil.getUserByIdWithOp(id, HttpUtil.UserOp.JACTIVITY, null),
                ActivityList.RequestData.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the user favorite activity list
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void getUserFActivity(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest(Request.Method.GET, HttpUtil.getUserByIdWithOp(id, HttpUtil.UserOp.FACTIVITY, null),
                ActivityList.RequestData.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the user favorite organization list
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void getUserFOrganization(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest<Organization.OrganizationRequestData>(Request.Method.GET, HttpUtil.getUserByIdWithOp(id, HttpUtil.UserOp.FORGANIZATION, null),
                Organization.OrganizationRequestData.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the user information by id
     * @param tag
     * @param id
     * @param listener
     * @param errorListener
     */
    public static void getUserInfoById(Object tag, final int id, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new GsonRequest<User>(Request.Method.GET, HttpUtil.getUserByIdWithOp(id, HttpUtil.UserOp.DETAIL, null),
                User.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Get the profile of current user
     * @param tag
     * @param listener
     * @param errorListener
     */
    public static void getProfile(Object tag, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new AuthedGsonRequest<User>(Request.Method.GET, HttpUtil.getProfileWithOp(HttpUtil.ProfileOp.DETAIL),
                User.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    /**
     * Update the user password
     * @param tag
     * @param oldPwd
     * @param newPwd
     * @param listener
     * @param errorListener
     */
    public static void updatePwd(Object tag, String oldPwd, String newPwd, Response.Listener listener,
                Response.ErrorListener errorListener) {
        Request request = new ModifyPwdRequest(oldPwd, newPwd, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    //搜索API#############################################################################

    public static void searchActivity(Object tag, int page, String keywords, Response.Listener listener,
                                      Response.ErrorListener errorListener) {
        Request request = new GsonRequest(
                Request.Method.GET,
                HttpUtil.searchActivityByKeywords(keywords, page),
                ActivityList.RequestData.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    public static void searchOrgs(Object tag, int page, String keywords, Response.Listener listener,
                                  Response.ErrorListener errorListener) {
        Request request = new GsonRequest(
                Request.Method.GET,
                HttpUtil.getOrganizationByIdWithOp(0, HttpUtil.OrganizationOp.SEARCH, page, keywords),
                Organization.OrganizationRequestData.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    public static void searchUser(Object tag, int page, String keywords, Response.Listener listener,
                                  Response.ErrorListener errorListener) {
        Request request = new GsonRequest(
                Request.Method.GET,
                HttpUtil.getUserByIdWithOp(0, HttpUtil.UserOp.SEARCH, keywords) + "&page=" + page,
                User.UserListData.class, listener, errorListener);
        if(tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    //####################################################################################

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
