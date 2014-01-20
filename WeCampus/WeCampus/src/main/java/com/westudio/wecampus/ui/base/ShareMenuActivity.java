package com.westudio.wecampus.ui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.ActivityDataHelper;
import com.westudio.wecampus.data.OrgDataHelper;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.CacheUtil;
import com.westudio.wecampus.util.DateUtil;
import com.westudio.wecampus.util.ImageUtil;
import com.westudio.wecampus.util.Utility;
import com.westudio.wecampus.util.WbShareTool;
import com.westudio.wecampus.util.WxShareTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by martian on 13-11-28.
 */
public class ShareMenuActivity extends SherlockFragmentActivity implements View.OnClickListener,
        IWeiboHandler.Response {
    public static final String ACTIVITY_ID = "activity_id";
    public static final String CAN_JOIN = "can_join";

    private View btnShareToWx;
    private View btnShareToMoment;
    private View btnShareToWeibo;
    private View btnShareToMail;
    private View btnQuit;
    private ProgressDialog progressDialog;
    private ActivityDataHelper dataHelper;
    private OrgDataHelper orgDataHelper;

    private AsyncTask queryTask;

    private Activity activity;

    private WbShareTool tool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share_dialog);

        Window window = getWindow();
        WindowManager.LayoutParams wlp = new WindowManager.LayoutParams();;
        wlp.copyFrom(window.getAttributes());
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        dataHelper = new ActivityDataHelper(this);
        orgDataHelper = new OrgDataHelper(this);

        int id = getIntent().getIntExtra(ACTIVITY_ID, -1);
        if (id >= 0) {
            queryActivity(id);
        }

        initWidgets();

        btnQuit.setVisibility(getIntent().getBooleanExtra(CAN_JOIN, true) ? View.GONE : View.VISIBLE);

        tool  = new WbShareTool(this);
        if (savedInstanceState != null) {
            tool.getmWeiboShareAPI().handleWeiboResponse(getIntent(), this);
        }
    }

    @Override
    protected void onDestroy() {
        if (queryTask != null) {
            queryTask.cancel(true);
        }
        super.onDestroy();
    }

    private void initWidgets() {
        btnShareToMoment = findViewById(R.id.share_to_moment);
        btnShareToMoment.setOnClickListener(this);
        btnShareToWx = findViewById(R.id.share_to_wx);
        btnShareToWx.setOnClickListener(this);
        btnShareToWeibo = findViewById(R.id.share_to_weibo);
        btnShareToWeibo.setOnClickListener(this);
        btnShareToMail = findViewById(R.id.share_to_mail);
        btnShareToMail.setOnClickListener(this);
        btnQuit = findViewById(R.id.item_quit);
        btnQuit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (activity == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.share_to_moment:
                shareToWx(WxShareTool.ShareType.MOMENT);
                break;
            case R.id.share_to_wx:
                shareToWx(WxShareTool.ShareType.FRIENDS);
                break;
            case R.id.share_to_weibo:
                shareToWeibo();
                break;
            case R.id.share_to_mail:
                sendToEmail();
                break;
            case R.id.item_quit:
                setResult(RESULT_OK, new Intent());
                finish();
                break;
        }
    }

    private void sendToEmail() {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "我在“" + getString(R.string.app_name) + "”发现了一个活动");
        StringBuilder sbText = new StringBuilder();
        sbText.append(activity.title);

        // 添加正文
        if (activity.organization != null) {
            sbText.append("【").append(activity.organization.name).append("】");
        }
        sbText.append("，快来一起参加吧\n>>>").append(activity.url);

        intent.putExtra(Intent.EXTRA_TEXT, sbText.toString());

        if (ImageUtil.IMAGE_NOT_FOUND.equals(activity.image)) {
            startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
        } else {
            WeCampusApi.requestImage(activity.image, new ImageLoader.ImageListener() {

                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    File temp = new File(CacheUtil.getExternalCacheDir(ShareMenuActivity.this), "temp.jpeg");
                    Bitmap bm = imageContainer.getBitmap();
                    if (bm != null) {
                        try {
                            FileOutputStream fos = new FileOutputStream(temp.getPath());
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //intent.setType("image/jpeg");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(temp));
                    }

                    startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
                }
            });
        }
    }

    private void shareToWx(final WxShareTool.ShareType type) {
        WxShareTool wxShareTool = null;
        wxShareTool = new WxShareTool(this);
        final WxShareTool finalWxShareTool = wxShareTool;
        WeCampusApi.requestImage(activity.image, new ImageLoader.ImageListener() {

            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                initWxShareTool(finalWxShareTool, imageContainer.getBitmap()).fireShareToWx(type);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                initWxShareTool(finalWxShareTool, null).fireShareToWx(type);
            }
        });
    }

    private WxShareTool initWxShareTool(WxShareTool tool, Bitmap bm) {
        tool.buildMessage(activity.title, getShareSubtitle(), activity.url, bm);
        return tool;
    }

    private String getShareSubtitle() {
        StringBuilder subTitle = new StringBuilder();
        subTitle.append("时间：");
        subTitle.append(DateUtil.getActivityTime(this, activity.begin, activity.end));
        subTitle.append("\n地点：").append(activity.location);
        return subTitle.toString();
    }

    private void shareToWeibo() {
        WeCampusApi.requestImage(activity.image, new ImageLoader.ImageListener() {

            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                tool.sendShareMessage(activity.title, getShareSubtitle(), activity.url,
                        imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tool.sendShareMessage(activity.title, getShareSubtitle(), activity.url, null);
            }
        });
    }

    private void queryActivity(final int id) {
        queryTask = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                activity = dataHelper.query(id);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (activity.organization_id != 0) {
                    queryTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            activity.organization = orgDataHelper.query(activity.organization_id);
                            return null;
                        }
                    };
                    Utility.executeAsyncTask(queryTask);
                }
            }
        };

        Utility.executeAsyncTask(queryTask);
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, "ok", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, "cancel", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(this, "fail" + "Error Message: " + baseResponse.errMsg,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}
