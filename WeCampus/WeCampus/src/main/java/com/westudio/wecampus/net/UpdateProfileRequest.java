package com.westudio.wecampus.net;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.util.HttpUtil;
import com.westudio.wecampus.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by martian on 13-11-15.
 */
public class UpdateProfileRequest extends AuthedGsonRequest<User>{

    private User mUser;

    public UpdateProfileRequest(User user, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(Method.POST, HttpUtil.getProfileWithOp(HttpUtil.ProfileOp.DETAIL), User.class, successListener, errorListener);
        this.mUser = user;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        headers.put("X-HTTP-Method-Override", "PATCH");
        return headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        JSONObject json = new JSONObject();
        try {
            if(!TextUtils.isEmpty(mUser.nickname))
                json.put("nickname", mUser.nickname);
            if(!TextUtils.isEmpty(mUser.words))
                json.put("words", mUser.words);
            if(!TextUtils.isEmpty(mUser.gender))
                json.put("gender", mUser.gender);
            if(!TextUtils.isEmpty(mUser.name))
                json.put("name", mUser.name);
            if(!TextUtils.isEmpty(mUser.contact_email))
                json.put("contact_email", mUser.contact_email);
            if(!TextUtils.isEmpty(mUser.phone))
                json.put("phone", mUser.phone);
            if(!TextUtils.isEmpty(mUser.birthday))
                json.put("birthday", mUser.birthday);
            if(!TextUtils.isEmpty(mUser.emotion))
                json.put("emotion", mUser.emotion);
            if(!TextUtils.isEmpty(mUser.stage))
                json.put("stage", mUser.stage);
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

    @Override
    public String getBodyContentType() {
        return "multipart/form-data";
    }
}
