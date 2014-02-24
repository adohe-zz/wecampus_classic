package com.westudio.wecampus.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    private static final String BOUNDARY = "0D9F3kdjfiefejnksdflkejrejj123";

    public UploadAvatarRequest(String imagePath, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(Method.POST, HttpUtil.URL_POST_PROFILE_AVATAR, User.class, successListener, errorListener);
        mImagePath = imagePath;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + BOUNDARY;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ByteArrayOutputStream imageBos = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageBos);

            bos.write(("--" + BOUNDARY + "\r\n").getBytes());
            bos.write( ("Content-Disposition: form-data; name=\"image\"; filename=\"avatar.jpeg\"\r\n"  ).getBytes());
            bos.write( ("Content-Type: image/jpeg\r\n"  ).getBytes());
            bos.write( ("Content-Transfer-Encoding: binary\r\n"  ).getBytes());
            bos.write("\r\n".getBytes());
            bos.write(imageBos.toByteArray());
            bos.write("\r\n".getBytes());
            bos.write(("--" + BOUNDARY + "--").getBytes());
        } catch (IOException ioe) {

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
