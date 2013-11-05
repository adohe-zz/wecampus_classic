package com.westudio.wecampus.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.UnderlinePageIndicator;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseDetailActivity;
import com.westudio.wecampus.ui.intro.IntroImageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nankonami on 13-10-4.
 * Activity that display the detail of activity
 */
public class ActivityDetailActivity extends BaseDetailActivity {

    private static final int[] IMG_IDS = {R.drawable.detail_pager_img, R.drawable.detail_pager_img_two};

    //Widgets
    private ViewPager viewPager;
    private UnderlinePageIndicator pageIndicator;
    private TextView tvOrg;
    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvLocation;
    private TextView tvTag;
    private ImageView ivPoster;
    private TextView tvTicket;
    private TextView tvCompany;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        updateActionBar();
        initWidget();
    }

    private void initWidget() {
        tvOrg = (TextView)findViewById(R.id.detail_tv_organization);
        tvTitle = (TextView)findViewById(R.id.detail_tv_title);
        tvTime = (TextView)findViewById(R.id.detail_tv_time);
        tvLocation = (TextView)findViewById(R.id.detail_tv_location);
        tvTag = (TextView)findViewById(R.id.detail_tv_tag);
        tvTicket = (TextView)findViewById(R.id.detail_tv_ticket);
        tvCompany = (TextView)findViewById(R.id.detail_tv_company);
        tvContent = (TextView)findViewById(R.id.detail_tv_content);
        tvContent.setText("You see, most well written code will instantly tell you what it does. With some experience " +
                "in functional JavaScript, and having read both Functional JavaScript and JavaScript Allongé " +
                "(both amazing books), I had no problem reading it. But explain it (and why one would even care about doing it) " +
                "to someone with no experience in functional programming?");
        ivPoster = (ImageView)findViewById(R.id.detail_img_poster);
        showBottomActionBar();
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail_menu_share:
                showShareDialog();
                return true;
            case R.id.detail_menu_like:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /**
     * Display the share dialog
     */
    private void showShareDialog() {
        String title = getResources().getString(R.string.app_name);
        String content = getResources().getString(R.string.share);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        startActivity(Intent.createChooser(intent, content));
    }
}
