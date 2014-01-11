package com.westudio.wecampus.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.UserDataHelper;
import com.westudio.wecampus.data.model.ActivityList;
import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.activity.ActivityDetailActivity;
import com.westudio.wecampus.ui.activity.ActivityListFragment;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.base.ImageDetailActivity;
import com.westudio.wecampus.ui.list.ListActivity;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.util.Constants;
import com.westudio.wecampus.util.DateUtil;
import com.westudio.wecampus.util.Utility;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;


/**
 * Created by Martian on 13-10-24.
 */
public class  MyHomepageFragment extends BaseFragment implements OnRefreshListener {

    private Activity mActivity;
    private View mView;
    private PullToRefreshAttacher mPullToRefreshAttacher;

    //The id of current user
    private int uid;

    private UserDataHelper mDataHelper;
    private UserInfoHandler mInfoHandler;
    private UserActivityHandler mJActivityHandler;
    private UserFActivityHandler mFActivityHandler;
    private UserOrganizationHandler mOrganizationHandler;
    private User mUser;

    //Widgets
    private TextView tvUserName;
    private TextView tvUserWords;
    private TextView tvUserFollow;
    private TextView tvUserFans;
    private ImageView ivUserAvatar;
    private TextView tvMoreActivity;
    private TextView tvAttendActivity;

    private boolean bNetworkFinished = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        uid = BaseApplication.getInstance().getAccountMgr().getUserId();
        mDataHelper = new UserDataHelper(mActivity);
        mInfoHandler = new UserInfoHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_homepage, container, false);

        if(mActivity instanceof MyHomepageActivity) {
            mPullToRefreshAttacher = ((MyHomepageActivity)mActivity).getmPullToRefreshAttacher();
        } else {
            mPullToRefreshAttacher = ((MainActivity)mActivity).getPullToRefreshAttacher();
        }

        initWidget(mView);

        refreshUserFromDb();
        mInfoHandler.fetchUserInfo();

        return mView;
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
                    if(mUser != null && !bNetworkFinished) {
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
        ivUserAvatar.setOnClickListener(clickListener);
        tvUserName = (TextView)view.findViewById(R.id.user_name);
        tvUserWords = (TextView)view.findViewById(R.id.user_words);
        tvUserFollow = (TextView)view.findViewById(R.id.user_profile_follow);
        tvUserFans = (TextView)view.findViewById(R.id.user_profile_fans);
        tvMoreActivity = (TextView)view.findViewById(R.id.activity_list_item_no_activity);
        tvMoreActivity.setOnClickListener(clickListener);
        tvAttendActivity = (TextView)view.findViewById(R.id.user_profile_attend_activity);

        view.findViewById(R.id.clickable_follow).setOnClickListener(clickListener);
        view.findViewById(R.id.clickable_fans).setOnClickListener(clickListener);

        mJActivityHandler = new UserActivityHandler(mView);
        mFActivityHandler = new UserFActivityHandler(mView);
        mOrganizationHandler = new UserOrganizationHandler(mView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Update the ui
     */
    private void updateUI() {
        tvUserName.setText(mUser.nickname);
        tvUserWords.setText(mUser.words);
        tvUserFollow.setText(String.valueOf(mUser.count_of_followings));
        tvUserFans.setText(String.valueOf(mUser.count_of_fans));
        if(Constants.IMAGE_NOT_FOUND.equals(mUser.avatar)) {
            if(Constants.MALE.equals(mUser.gender)) {
                ivUserAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_male));
            } else {
                ivUserAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_female));
            }
        } else {
            WeCampusApi.requestImage(mUser.avatar, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    Bitmap data = imageContainer.getBitmap();
                    if(data != null) {
                        ivUserAvatar.setImageBitmap(data);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(Constants.MALE.equals(mUser.gender)) {
                        ivUserAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_male));
                    } else {
                        ivUserAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_female));
                    }
                }
            });
        }
    }

    private void updateUserToDb() {
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
                if(mUser.count_of_join_activities == 0) {
                    Toast.makeText(mActivity, R.string.no_attend_activity, Toast.LENGTH_SHORT).show();
                } else if(mUser.count_of_join_activities == 1) {
                    Toast.makeText(mActivity, R.string.only_one_activity, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mActivity, ListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ListActivity.USER_ID, uid);
                    bundle.putInt(ListActivity.TYPE, 1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            } else if(v.getId() == R.id.img_avatar) {
                Intent intent = new Intent(mActivity, ImageDetailActivity.class);
                intent.putExtra(ImageDetailActivity.KEY_IMAGE_URL, mUser.avatar);
                intent.putExtra(ImageDetailActivity.KEY_EXTRA_INFO, mUser.nickname);
                intent.putExtra(ImageDetailActivity.KEY_EXTRA_SEX, mUser.gender);
                startActivity(intent);
            } else if (v.getId() == R.id.clickable_follow) {
                Intent intent = new Intent(getActivity(), UserListActivity.class);
                intent.putExtra(UserListFragment.USER_LIST_TYPE, UserListFragment.FOLLOWERS);
                intent.putExtra(UserListFragment.USER_OR_ACTIVITY_ID, mUser.id);
                startActivity(intent);
            } else if (v.getId() == R.id.clickable_fans) {
                Intent intent = new Intent(getActivity(), UserListActivity.class);
                intent.putExtra(UserListFragment.USER_LIST_TYPE, UserListFragment.FANS);
                intent.putExtra(UserListFragment.USER_OR_ACTIVITY_ID, mUser.id);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onRefreshStarted(View view) {

    }

    private class UserActivityHandler implements Response.Listener<ActivityList.RequestData>,
            Response.ErrorListener {

        private View view;
        RelativeLayout rlActivityListItem;
        ImageView ivImage;
        TextView tvTitle;
        TextView tvTime;
        TextView tvLocation;
        TextView tvLike;
        TextView tvSummary;
        TextView tvTag;
        private ActivityList ac;

        public UserActivityHandler(View view) {
            this.view = view;
            rlActivityListItem = (RelativeLayout)view.findViewById(R.id.activity_list_item);
            ivImage = (ImageView)view.findViewById(R.id.activity_list_item_image);
            tvTitle = (TextView)view.findViewById(R.id.activity_list_item_title);
            tvTime = (TextView)view.findViewById(R.id.activity_list_item_time);
            tvLocation = (TextView)view.findViewById(R.id.activity_list_item_location);
            tvLike = (TextView)view.findViewById(R.id.activity_list_item_like);
            tvSummary = (TextView)view.findViewById(R.id.activity_list_item_summary);
            tvTag = (TextView)view.findViewById(R.id.activity_list_item_tag);
            rlActivityListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ActivityDetailActivity.class);
                    intent.putExtra(ActivityListFragment.ACTIVITY_ID, ac.id);
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(mActivity, R.string.get_join_activity_error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ActivityList.RequestData activityRequestData) {
            ac = activityRequestData.getObjects().get(0);
            rlActivityListItem.setVisibility(View.VISIBLE);
            tvAttendActivity.setVisibility(View.VISIBLE);
            if(mUser.count_of_join_activities > 1) {
                tvMoreActivity.setVisibility(View.VISIBLE);
            }
            tvTitle.setText(ac.title);
            tvTime.setText(DateUtil.getActivityTime(mActivity, ac.begin, ac.end));
            tvLocation.setText(ac.location);
            tvLike.setText(String.valueOf(ac.count_of_fans));
            tvTag.setText(ac.category);
            String color = mActivity.getString(R.string.default_category_color);
            if(colors.containsKey(ac.category)) {
                color = colors.get(ac.category);
            }
            Drawable drawable = new ColorDrawable(Color.parseColor(color));
            tvTag.setBackgroundDrawable(drawable);
            if(Constants.IMAGE_NOT_FOUND.equals(ac.image)) {
                ivImage.setVisibility(View.GONE);
                tvSummary.setVisibility(View.VISIBLE);
                tvSummary.setText(ac.summary);
            } else {
                ivImage.setVisibility(View.VISIBLE);
                tvSummary.setVisibility(View.GONE);
                WeCampusApi.requestImage(ac.image, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                        Bitmap data = imageContainer.getBitmap();
                        if(data != null) {
                            ivImage.setImageBitmap(data);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
            }
        }

        public void refreshUI() {
            String attend = getResources().getString(R.string.attend_activity_num);
            tvAttendActivity.setText(String.format(attend, mUser.count_of_join_activities));

            if(mUser.count_of_join_activities == 0) {
                rlActivityListItem.setVisibility(View.GONE);
                tvAttendActivity.setVisibility(View.VISIBLE);
                tvMoreActivity.setText(getResources().getString(R.string.no_attend_activity));
                tvMoreActivity.setVisibility(View.VISIBLE);
            } else {
                WeCampusApi.getUserJActivity(MyHomepageFragment.this, uid, this, this);
            }
        }

    }

    private class UserOrganizationHandler implements Response.Listener<Organization.OrganizationRequestData>, Response.ErrorListener {

        private View mView;
        private RelativeLayout rlLikeOrganization;
        private LinearLayout lyNoLikeOrganization;
        private TextView tvNumOrg;
        private TextView tvOrgName;
        private TextView tvLikeOrg;
        private ImageView ivIcon;

        public UserOrganizationHandler(View view) {
            this.mView = view;
            rlLikeOrganization = (RelativeLayout)mView.findViewById(R.id.user_like_organization);
            lyNoLikeOrganization = (LinearLayout)mView.findViewById(R.id.user_like_no_organization);
            tvNumOrg = (TextView)mView.findViewById(R.id.num_like_org);
            tvOrgName = (TextView)mView.findViewById(R.id.text_like_org_name);
            tvLikeOrg = (TextView)mView.findViewById(R.id.num_org_like_count);
            ivIcon = (ImageView)mView.findViewById(R.id.icon_like_org);
            rlLikeOrganization.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ListActivity.USER_ID, uid);
                    bundle.putInt(ListActivity.TYPE, 3);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void onResponse(Organization.OrganizationRequestData organizationRequestData) {
            Organization organization = organizationRequestData.getObjects().get(0);
            rlLikeOrganization.setVisibility(View.VISIBLE);
            tvOrgName.setText(organization.name);
            String orgNum = getResources().getString(R.string.homepage_like_org);
            tvNumOrg.setText(String.format(orgNum, mUser.count_of_follow_organizations));
            String orgFans = getResources().getString(R.string.num_people_like);
            tvLikeOrg.setText(String.format(orgFans, organization.count_of_fans));
            WeCampusApi.requestImage(organization.avatar, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    Bitmap data = imageContainer.getBitmap();
                    if(data != null) {
                        ivIcon.setImageBitmap(data);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
        }

        public void refreshUI() {
            if(mUser.count_of_follow_organizations == 0) {
                rlLikeOrganization.setVisibility(View.GONE);
                lyNoLikeOrganization.setVisibility(View.VISIBLE);
            } else {
                rlLikeOrganization.setVisibility(View.VISIBLE);
                String orgNum = getResources().getString(R.string.homepage_like_org);
                tvNumOrg.setText(String.format(orgNum, mUser.count_of_follow_organizations));
                WeCampusApi.getUserFOrganization(MyHomepageFragment.this, uid, this, this);
            }
        }
    }

    private class UserFActivityHandler implements Response.Listener<ActivityList.RequestData>, Response.ErrorListener {

        private View view;
        private RelativeLayout rlFavoriteActivity;
        private LinearLayout lyNoFavoriteActivity;
        private TextView tvNumActivity;
        private TextView tvActivityName;
        private TextView tvActivitySummary;
        private ImageView ivActivityIcon;

        public UserFActivityHandler(View view) {
            this.view = view;
            rlFavoriteActivity = (RelativeLayout)view.findViewById(R.id.user_like_activity);
            lyNoFavoriteActivity = (LinearLayout)view.findViewById(R.id.user_like_no_activity);
            tvNumActivity = (TextView)view.findViewById(R.id.num_like_activity);
            tvActivityName = (TextView)view.findViewById(R.id.text_like_activity_name);
            tvActivitySummary = (TextView)view.findViewById(R.id.text_like_activity_summary);
            ivActivityIcon = (ImageView)view.findViewById(R.id.icon_like_activity);
            rlFavoriteActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ListActivity.USER_ID, uid);
                    bundle.putInt(ListActivity.TYPE, 2);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void onResponse(ActivityList.RequestData requestData) {
            ActivityList ac = requestData.getObjects().get(0);
            rlFavoriteActivity.setVisibility(View.VISIBLE);
            String activityNum = getResources().getString(R.string.homepage_like_activity);
            tvNumActivity.setText(String.format(activityNum, mUser.count_of_follow_activities));
            tvActivityName.setText(ac.title);
            tvActivitySummary.setText(ac.summary);
            WeCampusApi.requestImage(ac.image, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    Bitmap data = imageContainer.getBitmap();
                    if(data != null) {
                        ivActivityIcon.setImageBitmap(data);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
        }

        public void refreshUI() {
            if(mUser.count_of_follow_activities == 0) {
                rlFavoriteActivity.setVisibility(View.GONE);
                lyNoFavoriteActivity.setVisibility(View.VISIBLE);
            } else {
                rlFavoriteActivity.setVisibility(View.VISIBLE);
                String activityNum = getResources().getString(R.string.homepage_like_activity);
                tvNumActivity.setText(String.format(activityNum, mUser.count_of_follow_activities));
                WeCampusApi.getUserFActivity(MyHomepageFragment.this, uid, this, this);
            }
        }

    }

    private class UserInfoHandler implements Response.Listener<User>, Response.ErrorListener {

        public UserInfoHandler() {

        }

        //Get the user information from network
        public void fetchUserInfo() {
            mPullToRefreshAttacher.setRefreshing(true);
            WeCampusApi.getProfile(MyHomepageFragment.this, this, this);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            mPullToRefreshAttacher.setRefreshComplete();
            bNetworkFinished = true;
        }

        @Override
        public void onResponse(User user) {
            mPullToRefreshAttacher.setRefreshComplete();
            mUser = user;
            bNetworkFinished = true;
            updateUI();
            mJActivityHandler.refreshUI();
            mOrganizationHandler.refreshUI();
            mFActivityHandler.refreshUI();
            updateUserToDb();
        }
    }
}
