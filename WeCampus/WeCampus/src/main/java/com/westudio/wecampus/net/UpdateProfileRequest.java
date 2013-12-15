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
 * Created by martian on 13-11-15.
 */
public class UpdateProfileRequest extends AuthedGsonRequest<User>{

    private User mUser;

    public UpdateProfileRequest(User user, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(Method.POST, HttpUtil.getProfileWithOp(HttpUtil.ProfileOp.DETAIL), User.class, successListener, errorListener);
        this.mUser = user;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        JSONObject json = new JSONObject();
        try {
            json.put("nickname", mUser.nickname);
            json.put("words", mUser.words);
            json.put("gender", mUser.gender);
            json.put("name", mUser.name);
            json.put("email", mUser.email);
            json.put("phone", mUser.phone);
            json.put("birthday", mUser.birthday);
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
