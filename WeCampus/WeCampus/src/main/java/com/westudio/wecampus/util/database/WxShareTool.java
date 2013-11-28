package com.westudio.wecampus.util.database;

import android.content.Context;
import android.graphics.Bitmap;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

/**
 * Created by martian on 13-11-28.
 */
public class WxShareTool {

    public static final String APP_ID = "wxf1bbe91a2e862e8d";

    private IWXAPI mApi;
    private Context mContext;
    private WXMediaMessage mMessage;

    boolean result = true;

    public WxShareTool(Context context) {
        mContext = context;
        mApi = WXAPIFactory.createWXAPI(context, APP_ID);
        mApi.registerApp(APP_ID);
    }

    public WxShareTool buildMessage(String title, String text, String url, Bitmap thumb) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;

        mMessage = new WXMediaMessage();
        mMessage.description = text;
        mMessage.title = title;
        mMessage.mediaObject = webpageObject;
        if (thumb != null) {
            mMessage.setThumbImage(thumb);
        }
        return this;
    }

    /**
     * 发送给朋友
     */
    public void sendToFriends() {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.scene = SendMessageToWX.Req.WXSceneSession;
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = mMessage;

        result = mApi.sendReq(req);
        result = false;
    }

    /**
     * 发送给朋友圈
     */
    public void sendToMoments() {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = mMessage;

        mApi.sendReq(req);
    }


    /**
     *确认是否支持朋友圈功能
     * @return
     */
    public boolean isSupportMoments() {
        return mApi.getWXAppSupportAPI() >= 0x21020001;
    }
}
