package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.util.HttpUtil;
import com.westudio.wecampus.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nankonami on 13-10-19.
 * Create Session Request
 */
public class CreateSessionRequest extends GsonRequest<User> {

    private String account;
    private String pwd;

    /**
     * Constructor used for GET/DELETE/PATCH
     *
     * @param method
     * @param url
     * @param account
     * @param pwd
     * @param successListener
     * @param errorListener
     */
    public CreateSessionRequest(int method, String url, String account, String pwd,
                                Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, User.class, successListener, errorListener);
        this.account = account;
        this.pwd = pwd;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", account);
        params.put("password", pwd);
        return params;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        JSONObject jsonObject = new JSONObject();
        try {
            for(Map.Entry<String, String> entry : getParams().entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String body = "data=" +  jsonObject.toString();
        try {
            return (body.getBytes(getParamsEncoding()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Response<User> parseNetworkResponse(NetworkResponse response) {

        try {
            if(response.statusCode != 200) {
                return Response.error(new VolleyError("Network Error"));
            } else {
                String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

                return Response.success(mGson.fromJson(data, clazz), HttpHeaderParser.parseCacheHeaders(response));
            }

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
