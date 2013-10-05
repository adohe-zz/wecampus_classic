package com.westudio.wecampus.ui.activity;

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
    private TextView tvLike;
    private TextView tvRead;
    private Button btnAttend;
    private TextView tvTicket;
    private TextView tvCompany;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail);

        updateActionBar();
        initViewPager();
        initWidget();
    }

    private void initViewPager() {
        List<View> viewList = new ArrayList<View>();
        for(int i = 0; i < IMG_IDS.length; i++) {
            LinearLayout linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.page_activity_detail, null);
            ImageView imageView = (ImageView)linearLayout.findViewById(R.id.detail_viewpager_pic);
            imageView.setImageResource(IMG_IDS[i]);
            viewList.add(linearLayout);
        }

        viewPager = (ViewPager)findViewById(R.id.detail_viewpager);
        pageIndicator = (UnderlinePageIndicator)findViewById(R.id.detail_viewpager_indicator);
        IntroImageAdapter adapter = new IntroImageAdapter(viewList);
        viewPager.setAdapter(adapter);
        pageIndicator.setViewPager(viewPager, 0);
        pageIndicator.setFades(false);
    }

    private void initWidget() {
        tvOrg = (TextView)findViewById(R.id.detail_tv_organization);
        tvTitle = (TextView)findViewById(R.id.detail_tv_title);
        tvTime = (TextView)findViewById(R.id.detail_tv_time);
        tvLocation = (TextView)findViewById(R.id.detail_tv_location);
        tvLike = (TextView)findViewById(R.id.detail_tv_like);
        tvRead = (TextView)findViewById(R.id.detail_tv_read);
        btnAttend = (Button)findViewById(R.id.detail_btn_attent);
        tvTicket = (TextView)findViewById(R.id.detail_tv_ticket);
        tvCompany = (TextView)findViewById(R.id.detail_tv_company);
        tvContent = (TextView)findViewById(R.id.detail_tv_content);
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail_menu_share:
                return true;
            case R.id.detail_menu_like:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
