package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.westudio.wecampus.ui.base.BaseApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by martian on 13-11-14.
 * This class is used to fire a request with a 'AUTH_TOKEN' in Http header
 */
public class AuthedGsonRequest<T> extends GsonRequest<T> {
    public static final String AUTH_TOKEN_KEY = "Auth-Token";

    public AuthedGsonRequest(int method, String url, Class<T> clazz, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, clazz, successListener, errorListener);
    }

    public AuthedGsonRequest(int method, String url, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, successListener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();

        if(BaseApplication.getInstance().hasAccount) {
            headers.put(AUTH_TOKEN_KEY, BaseApplication.getInstance().getAccountMgr().getToken());
        }

        return headers;
    }
}
