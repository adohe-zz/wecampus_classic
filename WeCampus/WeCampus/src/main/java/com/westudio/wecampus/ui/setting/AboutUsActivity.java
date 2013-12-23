package com.westudio.wecampus.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.about.TermsOfUseActivity;
import com.westudio.wecampus.util.ContentUtil;

/**
 * Created by nankonami on 13-12-3.
 */
public class AboutUsActivity extends SherlockFragmentActivity {

    private RelativeLayout rlLike;
    private RelativeLayout rlWeibo;
    private RelativeLayout rlWeb;
    private TextView tvWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        updateActionBar();
        initWidget();
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.setting_about_us);
    }

    private void initWidget() {
        rlLike = (RelativeLayout)findViewById(R.id.about_rl_like);
        rlLike.setOnClickListener(clickListener);
        rlWeb = (RelativeLayout)findViewById(R.id.about_rl_web);
        rlWeb.setOnClickListener(clickListener);
        rlWeibo = (RelativeLayout)findViewById(R.id.about_rl_weibo);
        rlWeibo.setOnClickListener(clickListener);
        tvWeb = (TextView)findViewById(R.id.about_web);
        findViewById(R.id.about_terms_of_use).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.about_rl_like:
                    final Uri uri = Uri.parse("market://details?id=" +
                    getApplicationContext().getPackageName());
                    final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if(getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(AboutUsActivity.this, R.string.no_market, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.about_rl_weibo:
                    break;
                case R.id.about_rl_web:
                    ContentUtil.addLinks(tvWeb, "http://wecampus.net");
                    break;
                case R.id.about_terms_of_use:
                    startActivity(new Intent(AboutUsActivity.this, TermsOfUseActivity.class));
                    break;
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
