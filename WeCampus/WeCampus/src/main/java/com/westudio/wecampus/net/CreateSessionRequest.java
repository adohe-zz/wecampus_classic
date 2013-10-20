package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.westudio.wecampus.util.HttpUtil;

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
public class CreateSessionRequest<T> extends GsonRequest<T> {

    private String account;
    private String pwd;
    private int schoolID;

    /**
     * Constructor used for GET/DELETE/PATCH
     *
     * @param method
     * @param url
     * @param account
     * @param pwd
     * @param schoolID
     * @param clazz
     * @param successListener
     * @param errorListener
     */
    public CreateSessionRequest(int method, String url, String account, String pwd, int schoolID, Class<T> clazz,
                                Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, clazz, successListener, errorListener);

        this.account = account;
        this.pwd = pwd;
        this.schoolID = schoolID;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        params.put("password", HttpUtil.encryPwd(pwd));
        params.put("school_id", String.valueOf(schoolID));
        return params;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        JSONObject jsonObject = new JSONObject();
        try {
            for(Map.Entry<String, String> entry : getParams().entrySet()) {
                jsonObject.put(entry.getKey(), URLEncoder.encode(entry.getValue(), getParamsEncoding()));
            }
            return jsonObject.toString().getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
