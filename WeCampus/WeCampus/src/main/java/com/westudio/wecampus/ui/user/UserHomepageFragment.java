package com.westudio.wecampus.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.UserDataHelper;
import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.activity.ActivityListActivity;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.util.ImageUtil;
import com.westudio.wecampus.util.Utility;

import couk.jenxsol.parallaxscrollview.views.ParallaxScrollView;


/**
 * Created by Martian on 13-10-24.
 */
public class UserHomepageFragment extends Fragment {

    private ParallaxScrollView mScrollview;
    private Activity mActivity;

    //The id of current user
    private int uid;

    private UserDataHelper mDataHelper;
    private UserInfoHandler mInfoHandler;
    private User mUser;

    //Widgets
    private TextView tvUserName;
    private TextView tvUserWords;
    private TextView tvUserFollow;
    private TextView tvUserFans;
    private ImageView ivUserAvatar;
    private TextView tvMoreActivity;
    private TextView tvAttendActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = BaseApplication.getInstance().getAccountMgr().getUserId();
        mDataHelper = new UserDataHelper(mActivity);
        mInfoHandler = new UserInfoHandler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_homepage, container, false);

        mScrollview = (ParallaxScrollView) view.findViewById(R.id.scroll_view);
        mScrollview.setParallaxOffset(0.3f);

        initWidget(view);

        refreshUserFromDb();
        mInfoHandler.fetchUserInfo();

        return view;
    }

    /**
     * Get the user info from database
     */
    private void refreshUserFromDb() {
        if(uid != 0) {
            Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... params) {
                    mUser = mDataHelper.query(uid);
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    if(mUser != null) {
                        updateUI();
                    }
                }
            });
        }
    }

    /**
     * Initialize the widgets
     */
    private void initWidget(final View view) {
        ivUserAvatar = (ImageView)view.findViewById(R.id.img_avatar);
        tvUserName = (TextView)view.findViewById(R.id.user_name);
        tvUserWords = (TextView)view.findViewById(R.id.user_words);
        tvUserFollow = (TextView)view.findViewById(R.id.user_profile_follow);
        tvUserFans = (TextView)view.findViewById(R.id.user_profile_fans);
        tvMoreActivity = (TextView)view.findViewById(R.id.activity_list_item_no_activity);
        tvMoreActivity.setOnClickListener(clickListener);
        tvAttendActivity = (TextView)view.findViewById(R.id.user_profile_attend_activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Update the ui
     */
    private void updateUI() {
        tvUserName.setText(mUser.name);
        tvUserWords.setText(mUser.words);
        tvUserFollow.setText(String.valueOf(mUser.count_of_followers));
        tvUserFans.setText(String.valueOf(mUser.count_of_fans));
        WeCampusApi.requestImage(mUser.avatar, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                Bitmap data = imageContainer.getBitmap();
                if(data != null) {
                    ivUserAvatar.setImageBitmap(ImageUtil.getRoundedCornerBitmap(data));
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(mUser.gender.equals("nan")) {
                    ivUserAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_male));
                } else {
                    ivUserAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_female));
                }
            }
        });
    }

    private void updateActivityToDb() {
        Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                if(mUser != null) {
                    mDataHelper.update(mUser);
                }
                return null;
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.activity_list_item_no_activity) {
                Intent intent = new Intent(mActivity, ActivityListActivity.class);
                intent.putExtra(ActivityListActivity.EXTRA_CATEGORY, "学术讲座");
                startActivity(intent);
            }
        }
    };

    private class UserActivityHandler implements Response.Listener<com.westudio.wecampus.data.model.Activity.ActivityRequestData>,
                Response.ErrorListener {

        private Activity activity;
        private RelativeLayout rlActivityListItem;
        private ImageView ivImage;
        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvLocation;

        public UserActivityHandler(Activity activity) {
            this.activity = activity;
            rlActivityListItem = (RelativeLayout)activity.findViewById(R.id.activity_list_item);
            ivImage = (ImageView)activity.findViewById(R.id.activity_list_item_image);
            tvTitle = (TextView)activity.findViewById(R.id.activity_list_item_title);
            tvTime = (TextView)activity.findViewById(R.id.activity_list_item_time);
            tvLocation = (TextView)activity.findViewById(R.id.activity_list_item_location);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void onResponse(com.westudio.wecampus.data.model.Activity.ActivityRequestData activityRequestData) {

        }

        public void refreshUI() {
            String attend = getResources().getString(R.string.attend_activity_num);
            tvAttendActivity.setText(String.format(attend, mUser.count_of_follow_activities));

            if(mUser.count_of_follow_activities == 0) {
                rlActivityListItem.setVisibility(View.GONE);
                tvMoreActivity.setText(getResources().getString(R.string.no_attend_activity));
            } else {
                WeCampusApi.getUserJActivity(UserHomepageFragment.this, uid, this, this);
            }
        }

    }

    private class UserOrganizationHandler implements Response.Listener<Organization.OrganizationRequestData>, Response.ErrorListener {

        private Activity activity;

        public UserOrganizationHandler(Activity activity) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void onResponse(Organization.OrganizationRequestData organizationRequestData) {

        }
    }

    private class UserInfoHandler implements Response.Listener<User>, Response.ErrorListener {

        public UserInfoHandler(UserHomepageFragment fragment) {

        }

        //Get the user information from network
        public void fetchUserInfo() {
            WeCampusApi.getProfile(UserHomepageFragment.this, this, this);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void onResponse(User user) {
            mUser = user;
            updateUI();
            updateActivityToDb();
        }
    }
}
