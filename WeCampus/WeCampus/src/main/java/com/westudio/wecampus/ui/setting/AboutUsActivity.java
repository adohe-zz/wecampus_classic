package com.westudio.wecampus.ui.setting;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.westudio.wecampus.R;

/**
 * Created by nankonami on 13-12-3.
 */
public class AboutUsActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        updateActionBar();
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.setting_about_us);
    }
}
