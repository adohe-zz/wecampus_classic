package com.westudio.wecampus.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.exception.WeiboShareException;
import com.westudio.wecampus.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by martian on 14-1-19.
 */
public class WbShareTool  {
    public String APP_KEY = Config.WB_APP_KEY();

    private IWeiboShareAPI mWeiboShareAPI;
    private Context mContext;

    public WbShareTool(Context context) {
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, APP_KEY);
        mContext = context;
    }

    public void sendShareMessage(String title, String text, String url, Bitmap source) {
        try {
            if (mWeiboShareAPI.checkEnvironment(true)) {
                mWeiboShareAPI.registerApp();
                WeiboMultiMessage message = new WeiboMultiMessage();
                message.mediaObject = getWebpageObj(title, text, url, source);

                ImageObject imageObject = new ImageObject();
                String path = CacheUtil.getExternalCacheDir(mContext).getPath() + "t" +System.currentTimeMillis() + ".jpg";
                Bitmap shareImage = ImageUtil.resizeImageTo(source, 2 * 1024 * 1024);
                ImageUtil.saveBitmapToPath(path, shareImage);
                imageObject.imagePath = path;
                message.imageObject = imageObject;

                SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
                // 用transaction唯一标识一个请求
                request.transaction = String.valueOf(System.currentTimeMillis());
                request.multiMessage = message;

                mWeiboShareAPI.sendRequest(request);
            }
        } catch (WeiboShareException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WebpageObject getWebpageObj(String title, String text, String url, Bitmap bitmap) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = com.sina.weibo.sdk.utils.Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = text;

        // 设置 Bitmap 类型的图片到网页对象里
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        }
        byte[] data = ImageUtil.cropCenterSquareToSize(bitmap, 32 * 1024);
        mediaObject.thumbData = data;

        mediaObject.actionUrl = url;
        mediaObject.defaultText = text;
        return mediaObject;
    }

    public void sendShareAppMsg() {
        String text = "精彩校园生活，从缤纷活动开始！这里只有你想不到，没有做不到，快来一起试试吧@WeCampus校园社交" +
                ">>>";
        String url = "http://www.wecampus.net";

        Bitmap shareImage = null;
        AssetManager am= mContext.getAssets();
        try {
            InputStream is = am.open("shareweibo.jpg");
            shareImage = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        sendShareMessage(mContext.getString(R.string.app_name), text, url, shareImage);
    }

    public IWeiboShareAPI getmWeiboShareAPI() {
        return mWeiboShareAPI;
    }
}
