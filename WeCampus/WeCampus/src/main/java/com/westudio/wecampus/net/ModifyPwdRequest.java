package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by nankonami on 13-12-4.
 */
public class ModifyPwdRequest extends AuthedGsonRequest<User> {

    private String oldPwd;
    private String newPwd;

    public ModifyPwdRequest(String oldPwd, String newPwd, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(Method.POST, HttpUtil.getProfileWithOp(HttpUtil.ProfileOp.PASSWORD), User.class, successListener, errorListener);
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        JSONObject json = new JSONObject();
        try {
            json.put("old_password", oldPwd);
            json.put("password", newPwd);
            json.put("password_confirmation", newPwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String body = "data=" + json.toString();

        try {
            return body.getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
