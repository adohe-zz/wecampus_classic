package com.westudio.wecampus.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.exception.WeiboShareException;

/**
 * Created by martian on 14-1-19.
 */
public class WbShareTool  {
    public static String APP_KEY = "3126350995";

    private IWeiboShareAPI mWeiboShareAPI;

    public WbShareTool(Context context) {
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, APP_KEY);
    }

    public void sendShareMessage(String title, String text, String url, Bitmap bitmap) {
        try {
            if (mWeiboShareAPI.checkEnvironment(true)) {
                mWeiboShareAPI.registerApp();
                WeiboMultiMessage message = new WeiboMultiMessage();
                message.mediaObject = getWebpageObj(title, text, url, bitmap);

                SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
                // 用transaction唯一标识一个请求
                request.transaction = String.valueOf(System.currentTimeMillis());
                request.multiMessage = message;

                mWeiboShareAPI.sendRequest(request);
            }
        } catch (WeiboShareException e) {
            e.printStackTrace();
        }
    }

    private WebpageObject getWebpageObj(String title, String text, String url, Bitmap bitmap) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = com.sina.weibo.sdk.utils.Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = text;

        // 设置 Bitmap 类型的图片到视频对象里
        if (bitmap != null) {
            byte[] data = ImageUtil.cropBitmap(bitmap);
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            mediaObject.setThumbImage(bm);
        }

        mediaObject.actionUrl = url;
        mediaObject.defaultText = text;
        return mediaObject;
    }

    public IWeiboShareAPI getmWeiboShareAPI() {
        return mWeiboShareAPI;
    }
}
