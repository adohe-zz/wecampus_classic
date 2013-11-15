package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.util.HttpUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by martian on 13-11-15.
 */
public class UpdateProfileRequest extends AuthedGsonRequest<User>{

    private User mUser;

    public UpdateProfileRequest(User user, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(Method.POST, HttpUtil.URL_PROFILE, successListener, errorListener);
        this.mUser = user;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        Gson gson = new Gson();
        String strUserJson = gson.toJson(mUser, User.class);
        String body = "data=" + strUserJson;

        try {
            return body.getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
