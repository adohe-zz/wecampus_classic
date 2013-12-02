package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityCategory;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.activity.ActivityListFragment;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.intro.IntroActivity;
import com.westudio.wecampus.ui.main.LeftMenuFragment;
import com.westudio.wecampus.ui.main.MainActivity;

import java.util.HashMap;

/**
 * Created by nankonami on 13-9-11.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);


        CategoryHandler handler = new CategoryHandler();
        requestActivityCategory(handler);
    }

    private void completeSplash() {
        SplashActivity.this.finish();
        Intent intent = null;
        if (BaseApplication.getInstance().hasAccount) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
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

            completeSplash();
        }
    }
}
