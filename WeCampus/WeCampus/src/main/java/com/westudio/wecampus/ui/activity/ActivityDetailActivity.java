package com.westudio.wecampus.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import com.westudio.wecampus.ui.base.ImageDetailActivity;
import com.westudio.wecampus.util.Utility;

/**
 * Created by nankonami on 13-10-4.
 * Activity that display the detail of activity
 */
public class ActivityDetailActivity extends BaseDetailActivity{

    private static final int[] IMG_IDS = {R.drawable.detail_pager_img, R.drawable.detail_pager_img_two};

    //Widgets
    private TextView tvOrg;
    private ImageView ivOrgAvatar;
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
    private ActivityDetailUpdater updater;

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
        ivOrgAvatar = (ImageView) findViewById(R.id.detail_org_avatar);
        tvTitle = (TextView)findViewById(R.id.detail_tv_title);
        tvTime = (TextView)findViewById(R.id.detail_tv_time);
        tvLocation = (TextView)findViewById(R.id.detail_tv_location);
        tvTag = (TextView)findViewById(R.id.detail_tv_tag);
        tvTicket = (TextView)findViewById(R.id.detail_tv_ticket);
        tvCompany = (TextView)findViewById(R.id.detail_tv_company);
        tvContent = (TextView)findViewById(R.id.detail_tv_content);
        ivPoster = (ImageView)findViewById(R.id.detail_img_poster);

        tvTitle.setText(activity.title);
        tvLocation.setText(activity.location);
        tvTag.setText(activity.category);
        tvTicket.setText(activity.location);
        tvCompany.setText(activity.sponsor_name);
        tvContent.setText(activity.description);

        Drawable defaultDrawable = new ColorDrawable(Color.rgb(229, 255, 255));
        WeCampusApi.requestImage(activity.image, WeCampusApi.getImageListener(ivPoster,
                defaultDrawable, defaultDrawable));
        ivPoster.setOnClickListener(clickListener);

        showBottomActionBar();

        updateExtraUi();

        updater = new ActivityDetailUpdater();
        updater.fetchActivityDetail();
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

    private void updateExtraUi() {
        if (activity.organization != null) {
            tvOrg.setText(activity.organization.getName());
            Drawable defaultOrgAvatar = getResources().getDrawable(R.drawable.detail_organization);
            WeCampusApi.requestImage(activity.image, WeCampusApi.getImageListener(ivOrgAvatar,
                    defaultOrgAvatar, defaultOrgAvatar));
        }

        if (activity.have_ticket) {
            findViewById(R.id.detail_tv_ticket).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.detail_tv_ticket)).setText(activity.ticket_service);
        } else {
            findViewById(R.id.detail_tv_ticket).setVisibility(View.GONE);
        }

        if (activity.have_sponsor) {
            findViewById(R.id.detail_tv_ticket).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.detail_rl_sponsor)).setText(activity.sponsor_name);
        } else {
            findViewById(R.id.detail_rl_sponsor).setVisibility(View.GONE);
        }
    }

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

    private View.OnClickListener clickListener =  new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.detail_img_poster: {
                    Intent intent = new Intent(ActivityDetailActivity.this, ImageDetailActivity.class);
                    intent.putExtra(ImageDetailActivity.KEY_IMAGE_URL, activity.image);
                    startActivity(intent);
                    break;
                }
            }
        }
    };



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
            WeCampusApi.postJoinActivity(ActivityDetailActivity.this, activity.id, this, this);
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
            WeCampusApi.postLikeActivity(ActivityDetailActivity.this, activity.id, like, this, this);
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

    private class ActivityDetailUpdater implements Response.Listener<Activity.ActivityRequestData>, Response.ErrorListener{

        public void fetchActivityDetail() {
            WeCampusApi.getActivityDetail(ActivityDetailActivity.this, activity.id, this, this);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //TODO fetch activity failed
        }

        @Override
        public void onResponse(final Activity.ActivityRequestData data) {
            Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... objects) {
                    dataHelper.update(data.objects);
                    return null;
                }
            });

            activity = data.objects;
            updateExtraUi();
        }
    }
}
