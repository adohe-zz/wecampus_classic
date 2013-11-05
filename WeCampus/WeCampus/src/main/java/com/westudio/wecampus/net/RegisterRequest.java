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
 * Created by martian on 13-11-4.
 */
public class RegisterRequest<T> extends GsonRequest<T> {
    private RegisterData mRegisterData;

    public RegisterRequest(int method, String url, Class<T> clazz, RegisterData registerData, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, clazz, successListener, errorListener);
        mRegisterData = registerData;
    }

    public static class RegisterData {
        public String email;
        public String nickname;
        public String password;
        public String gender;
        public String schoolId;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", mRegisterData.email);
        params.put("password", mRegisterData.password);
        params.put("schoolId", mRegisterData.schoolId);
        params.put("nickname", mRegisterData.nickname);
        params.put("gender", mRegisterData.gender);
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
