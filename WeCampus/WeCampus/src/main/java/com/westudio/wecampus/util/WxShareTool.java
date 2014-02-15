package com.westudio.wecampus.util;

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

    public static String APP_ID = Config.WX_APP_ID();

    public static final String WEBSITE_URL = "http://www.wecampus.net";

    private IWXAPI mApi;
    private Context mContext;
    private WXMediaMessage mMessage;

    boolean result = true;

    public enum ShareType {
        FRIENDS, MOMENT;
    }

    public WxShareTool(Context context) {
        mContext = context;
        mApi = WXAPIFactory.createWXAPI(context, APP_ID);
        mApi.registerApp(APP_ID);
    }

    public WxShareTool buildAppMessage() {
        return buildMessage("精彩校园生活，从缤纷活动开始，快来一起试试吧！", null, WEBSITE_URL, null);
    }

    public WxShareTool buildMessage(String title, String text, String url, Bitmap thumb) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;

        mMessage = new WXMediaMessage();
        mMessage.description = text;
        mMessage.title = title;
        mMessage.mediaObject = webpageObject;
        if (thumb != null) {
            mMessage.thumbData = ImageUtil.cropBitmap(thumb);
        }
        return this;
    }

    /**
     * 发送分享请求到微信
     * @param type 要分享的地方
     */
    public void fireShareToWx(ShareType type) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        if (type == ShareType.FRIENDS) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
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
