package com.westudio.wecampus.net;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

/**
 * Created by nankonami on 13-9-10.
 */
public class GsonRequest<T> extends Request<T> {
    private Class<T> clazz;
    private Gson mGson;

    public GsonRequest(int method, String url, Class<T> clazz, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        return null;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(T t) {

    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }
}
