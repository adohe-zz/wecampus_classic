package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by nankonami on 13-12-8.
 */
public class ForgetPwdRequest<T> extends GsonRequest<T> {

    private Response.Listener listener;
    private Response.ErrorListener errorListener;

    private String email;

    public ForgetPwdRequest(String email, Class<T> clazz, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(Method.POST, HttpUtil.getProfileWithOp(HttpUtil.ProfileOp.FPASSWORD), clazz, successListener, errorListener);
        this.listener = successListener;
        this.errorListener = errorListener;
        this.email = email;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(T t) {
        super.deliverResponse(t);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        JSONObject json = new JSONObject();
        try {
            json.put("email", this.email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            return json.toString().getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data";
    }
}
