package com.westudio.wecampus.ui.intro;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.R;

/**
 * Created by nankonami on 13-9-11.
 * The Introduction activity
 */
public class IntroActivity extends SherlockFragmentActivity {

    private static String INTRO_FRAGMENT = "intro_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_intro);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.intro_fragment_container, IntroFragment.newInstance(null), INTRO_FRAGMENT)
                .commit();

        setUpActionBar();

    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("IntroActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("IntroActivity");
        MobclickAgent.onPause(this);
    }
}