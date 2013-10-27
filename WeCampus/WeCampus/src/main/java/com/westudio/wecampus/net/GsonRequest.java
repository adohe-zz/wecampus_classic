package com.westudio.wecampus.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.westudio.wecampus.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by nankonami on 13-9-10.
 */
public class GsonRequest<T> extends Request<T> {

    private static final String TAG = "GSONREQUEST";

    private Class<T> clazz;
    private Gson mGson;
    private Response.Listener listener;
    private Response.ErrorListener errorListener;

    /**
     * Constructor used for GET/DELETE/PATCH
     * @param method
     * @param url
     * @param clazz
     * @param successListener
     * @param errorListener
     */
    public GsonRequest(int method, String url, Class<T> clazz,
                       Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.clazz = clazz;
        this.mGson = new Gson();
        this.listener = successListener;
        this.errorListener = errorListener;
    }

    /**
     * @param method
     * @param url
     * @param successListener
     * @param errorListener
     */
    public GsonRequest(int method, String url, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.mGson = new Gson();
        this.listener = successListener;
        this.errorListener = errorListener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Utility.log("statuscode", response.statusCode);
        try {
            if(response.statusCode != 200) {
                return Response.error(new VolleyError("Network Error"));
            } else {
                String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                Utility.log("data", data);
                T list = mGson.fromJson(data, new TypeToken<T>(){}.getType());
                return Response.success(list, HttpHeaderParser.parseCacheHeaders(response));
            }

            /*String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonObject = new JSONObject(data);
            if(Integer.valueOf(jsonObject.getJSONObject("Status").getString("Id")) != 0) {
                return Response.error(new VolleyError(jsonObject.getJSONObject("Status").getString("Memo")));
            } else {
                return Response.success(mGson.fromJson(jsonObject.getJSONObject("Data").toString(), clazz), HttpHeaderParser.parseCacheHeaders(response));
            }*/
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } /*catch (JSONException e) {
            return null;
        }*/
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(T t) {
        listener.onResponse(t);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }
}
