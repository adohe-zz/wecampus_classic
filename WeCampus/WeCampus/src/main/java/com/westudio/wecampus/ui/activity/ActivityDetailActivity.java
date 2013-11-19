package com.westudio.wecampus.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.ActivityDataHelper;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseDetailActivity;

/**
 * Created by nankonami on 13-10-4.
 * Activity that display the detail of activity
 */
public class ActivityDetailActivity extends BaseDetailActivity{
    private static final int[] IMG_IDS = {R.drawable.detail_pager_img, R.drawable.detail_pager_img_two};

    //Widgets
    private TextView tvOrg;
    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvLocation;
    private TextView tvTag;
    private ImageView ivPoster;
    private TextView tvTicket;
    private TextView tvCompany;
    private TextView tvContent;

    ActivityDataHelper dataHelper;

    private int activityId = -1;
    private Activity activity;
    private JoinHandler joinHandler;
    private LikeHandler likeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        activityId = getIntent().getIntExtra(ActivityListFragment.ACTIVITY_ID, -1);

        dataHelper = new ActivityDataHelper(this);
        refreshActivityFromDb();

        updateActionBar();
        initWidget();
    }

    private void refreshActivityFromDb() {
        if (activityId != -1) {
            activity = dataHelper.query(activityId);
        }
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
        ivPoster = (ImageView)findViewById(R.id.detail_img_poster);

        tvTitle.setText(activity.getTitle());
        tvLocation.setText(activity.getLocation());
        tvTag.setText(activity.getCategory());
        tvTicket.setText(activity.getLocation());
        tvCompany.setText(activity.getSponsor_name());
        tvContent.setText(activity.getDescription());

        Drawable defaultDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));
        WeCampusApi.requestImage(activity.getImage(), WeCampusApi.getImageListener(ivPoster,
                defaultDrawable, defaultDrawable));

        showBottomActionBar();

        joinHandler = new JoinHandler(this);
        likeHandler = new LikeHandler(this);
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
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail_menu_share:
                showShareDialog();
                return true;
            case R.id.detail_menu_like:
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }

    private class JoinHandler implements Response.Listener<Activity>, Response.ErrorListener{
        private  android.app.Activity ac;
        ProgressBar progressBar;
        ImageView icon;
        LinearLayout container;

        public JoinHandler(android.app.Activity activity) {
            this.ac = activity;
            progressBar = (ProgressBar) ac.findViewById(R.id.progress_join);
            icon = (ImageView) ac.findViewById(R.id.ic_activity_join);
            container = (LinearLayout) ac.findViewById(R.id.bottom_bar_attend);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    join();
                }
            });
        }

        public void join() {
            progressBar.setVisibility(View.VISIBLE);
            icon.setVisibility(View.GONE);
            WeCampusApi.postJoinActivity(ActivityDetailActivity.this, activity.getId(), this, this);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            progressBar.setVisibility(View.GONE);
            icon.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(Activity activity) {
            container.setVisibility(View.GONE);
        }
    }

    private class LikeHandler implements Response.Listener<Activity>, Response.ErrorListener{
        private  android.app.Activity ac;
        boolean like;
        ProgressBar progressBar;
        ImageView icon;
        LinearLayout container;

        public LikeHandler(android.app.Activity activity) {
            this.ac = activity;
            progressBar = (ProgressBar) ac.findViewById(R.id.progress_like);
            icon = (ImageView) ac.findViewById(R.id.ic_activity_like);
            container = (LinearLayout) ac.findViewById(R.id.bottom_bar_like);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeLikeState();
                }
            });
        }

        public void changeLikeState() {
            progressBar.setVisibility(View.VISIBLE);
            icon.setVisibility(View.GONE);
            WeCampusApi.postLikeActivity(ActivityDetailActivity.this, activity.getId(), like, this, this);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            progressBar.setVisibility(View.GONE);
            icon.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(Activity activity) {

        }
    }
}
