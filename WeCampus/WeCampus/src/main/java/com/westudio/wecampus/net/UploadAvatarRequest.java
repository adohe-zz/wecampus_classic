package com.westudio.wecampus.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.westudio.wecampus.util.HttpUtil;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by jam on 13-11-14.
 */
public class UploadAvatarRequest extends AuthedGsonRequest<Void>{

    private String mImagePath;
    private MultipartEntity mEntity = new MultipartEntity();

    public UploadAvatarRequest(String imagePath, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(Method.POST, HttpUtil.URL_POST_PROFILE_AVATAR, successListener, errorListener);
        mImagePath = imagePath;
    }

    @Override
    public String getBodyContentType() {
        return mEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        mEntity.addPart("image", new FileBody(new File(mImagePath), "image/*"));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<Void> parseNetworkResponse(NetworkResponse response) {
        if(response.statusCode != 200) {
            return Response.error(new VolleyError("Network Error"));
        }
        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
    }
}
