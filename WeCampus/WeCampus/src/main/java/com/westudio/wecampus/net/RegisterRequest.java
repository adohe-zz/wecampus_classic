package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.westudio.wecampus.data.model.User;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by martian on 13-11-4.
 */
public class RegisterRequest extends GsonRequest<User> {
    private RegisterData mRegisterData;
    private MultipartEntity mEntity = new MultipartEntity();

    public RegisterRequest(int method, String url, RegisterData registerData, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, User.class, successListener, errorListener);
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
        params.put("school_id", mRegisterData.schoolId);
        params.put("nickname", mRegisterData.nickname);
        params.put("gender", mRegisterData.gender);
        return params;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data";
    }

    private void buildConentEntity() {
        JSONObject jsonObject = new JSONObject();
        try {
            for(Map.Entry<String, String> entry : getParams().entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            mEntity.addPart("data", new StringBody(jsonObject.toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
                Utility.log("response string", data);
                return Response.success(mGson.fromJson(data, clazz), HttpHeaderParser.parseCacheHeaders(response));
            }

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
