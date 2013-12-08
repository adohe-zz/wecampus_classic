package com.westudio.wecampus.net;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.util.HttpUtil;

/**
 * Created by nankonami on 13-12-8.
 */
public class ForgetPwdRequest extends GsonRequest<Object> {

    private Response.Listener listener;
    private Response.ErrorListener errorListener;

    public ForgetPwdRequest(int method, String url, Class<Object> clazz, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(Method.POST, HttpUtil.getProfileWithOp(HttpUtil.ProfileOp.FPASSWORD), clazz, successListener, errorListener);
        this.listener = successListener;
        this.errorListener = errorListener;
    }

    @Override
    protected Response<Object> parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(Object o) {
        super.deliverResponse(o);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }
}
