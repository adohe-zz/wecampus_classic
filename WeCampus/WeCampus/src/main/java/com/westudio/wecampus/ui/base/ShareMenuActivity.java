package com.westudio.wecampus.ui.base;

import android.graphics.Bitmap;
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
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.Utility;
import com.westudio.wecampus.util.database.WxShareTool;

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
        WxShareTool wxShareTool = null;
        if (activity == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.share_to_moment:

                break;
            case R.id.share_to_wx:
                wxShareTool = new WxShareTool(this);
                final WxShareTool finalWxShareTool = wxShareTool;
                WeCampusApi.requestImage(activity.image, new ImageLoader.ImageListener() {

                    @Override
                    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                        shareToWx(finalWxShareTool, imageContainer.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        shareToWx(finalWxShareTool, null);
                    }
                });
                break;
            case R.id.share_to_sms:

                break;
            case R.id.share_to_mail:

                break;
        }
    }

    private void shareToWx(WxShareTool tool, Bitmap bm) {
        tool.buildMessage(activity.title, activity.location, activity.url, bm);
        tool.sendToFriends();
    }

    private void queryActivity(final int id) {
        queryTask = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                activity = dataHelper.query(id);
                return null;
            }
        };

        Utility.executeAsyncTask(queryTask);
    }
}
