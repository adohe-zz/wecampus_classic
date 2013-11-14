package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by martian on 13-11-14.
 * This class is used to fire a request with a 'AUTH_TOKEN' in Http header
 */
public class AuthedGsonRequest<T> extends GsonRequest<T> {
    public static final String AUTH_TOKEN_KEY = "Auth-Token";
    private String mToken;

    public AuthedGsonRequest(String token, int method, String url, Class<T> clazz, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, clazz, successListener, errorListener);
        this.mToken = token;
    }

    public AuthedGsonRequest(String token, int method, String url, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, successListener, errorListener);
        this.mToken = token;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> mapHeaders = new HashMap<String, String>();
        mapHeaders.put(AUTH_TOKEN_KEY, mToken);
        return mapHeaders;
    }
}
