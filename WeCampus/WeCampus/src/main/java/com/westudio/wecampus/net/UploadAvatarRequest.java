package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.util.HttpUtil;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by jam on 13-11-14.
 */
public class UploadAvatarRequest extends AuthedGsonRequest<User>{

    private String mImagePath;
    private MultipartEntity mEntity = new MultipartEntity();

    public UploadAvatarRequest(String imagePath, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(Method.POST, HttpUtil.URL_POST_PROFILE_AVATAR, User.class, successListener, errorListener);
        mImagePath = imagePath;
    }

    @Override
    public String getBodyContentType() {
        return mEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        mEntity.addPart("image", new FileBody(new File(mImagePath)));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<User> parseNetworkResponse(NetworkResponse response) {
        if(response.statusCode != 200) {
            return Response.error(new VolleyError("Network Error"));
        }
        String data = null;
        try {
            data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Response.success(mGson.fromJson(data, clazz), HttpHeaderParser.parseCacheHeaders(response));
    }
}
