package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.intro.IntroActivity;
import com.westudio.wecampus.ui.main.MainActivity;

/**
 * Created by nankonami on 13-9-11.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);

        completeSplash();
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
}
