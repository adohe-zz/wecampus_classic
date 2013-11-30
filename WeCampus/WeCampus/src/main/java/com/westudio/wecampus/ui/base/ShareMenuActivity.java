package com.westudio.wecampus.ui.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.ActivityDataHelper;
import com.westudio.wecampus.data.OrgDataHelper;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.CacheUtil;
import com.westudio.wecampus.util.DateUtil;
import com.westudio.wecampus.util.ImageUtil;
import com.westudio.wecampus.util.Utility;
import com.westudio.wecampus.util.database.WxShareTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by martian on 13-11-28.
 */
public class ShareMenuActivity extends SherlockFragmentActivity implements View.OnClickListener {
    public static final String ACTIVITY_ID = "activity_id";

    private View btnShareToWx;
    private View btnShareToMoment;
    private View btnShareToSms;
    private View btnShareToMail;

    private ActivityDataHelper dataHelper;
    private OrgDataHelper orgDataHelper;

    private AsyncTask queryTask;

    private Activity activity;
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
        btnShareToSms = findViewById(R.id.share_to_sms);
        btnShareToSms.setOnClickListener(this);
        btnShareToMail = findViewById(R.id.share_to_mail);
        btnShareToMail.setOnClickListener(this);
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
            case R.id.share_to_sms:

                break;
            case R.id.share_to_mail:
                sendToEmail();
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
        StringBuilder subTitle = new StringBuilder();
        subTitle.append("时间：");
        subTitle.append(DateUtil.getActivityTime(this, activity.begin, activity.end));
        subTitle.append("\n地点：").append(activity.location);
        tool.buildMessage(activity.title, subTitle.toString(), activity.url, bm);

        return tool;
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
}
