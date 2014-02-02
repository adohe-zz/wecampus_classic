package com.westudio.wecampus.ui.about;

import android.os.Bundle;
import android.webkit.WebView;

import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.ui.base.BaseGestureActivity;

/**
 * Created by nankonami on 13-10-4.
 * Terms of Use Activity
 */
public class TermsOfUseActivity extends BaseGestureActivity {

    private static final String URL = "http://wecampus.net/terms.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Update the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webView = new WebView(this);
        webView.loadUrl(URL);
        setContentView(webView);

        registerSwipeToCloseListener(webView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("TermsOfUserActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageStart("TermsOfUserActivity");
        MobclickAgent.onPause(this);
    }

}
