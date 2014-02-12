package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityCategory;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.intro.IntroActivity;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.util.Utility;

import java.util.HashMap;

/**
 * Created by nankonami on 13-9-11.
 */
public class SplashActivity extends Activity {

    private static final long SPLASH_TIME_OUT = 2700;

    private AsyncTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);


        CategoryHandler handler = new CategoryHandler();
        requestActivityCategory(handler);

        task = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                try {
                    Thread.sleep(SPLASH_TIME_OUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                WeCampusApi.cancelRequest(SplashActivity.this);
                completeSplash();
            }
        };
        Utility.executeAsyncTask(task);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("SplashActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SplashActivity");
        MobclickAgent.onPause(this);
    }

    private void completeSplash() {
        SplashActivity.this.finish();
        Intent intent = null;
        SharedPreferences sp = getSharedPreferences("user_account_config", MODE_PRIVATE);
        boolean isFirstOpen = sp.getBoolean("isFirstUse", true);
        if (!isFirstOpen) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirstUse", false);
            editor.apply();
            intent = new Intent(SplashActivity.this, IntroActivity.class);
        }
        startActivity(intent);
    }

    private void requestActivityCategory(CategoryHandler handler) {
        WeCampusApi.getActivityCategory(this, handler, handler);
    }

    private class CategoryHandler implements Response.Listener<ActivityCategory.CategoryRequestData>,
            Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            completeSplash();
        }

        @Override
        public void onResponse(ActivityCategory.CategoryRequestData categoryRequestData) {
            HashMap<String, String> colors = new HashMap<String, String>();
            for(ActivityCategory category : categoryRequestData.getObjects()) {
                colors.put(category.name, category.color);
            }
            BaseApplication.getInstance().setCategoryMapping(colors);

            if (task != null) {
                task.cancel(true);
            }
            completeSplash();
        }
    }
}
