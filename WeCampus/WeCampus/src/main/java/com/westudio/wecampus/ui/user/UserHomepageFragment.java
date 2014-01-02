package com.westudio.wecampus.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
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
import com.westudio.wecampus.ui.view.FollowButton;
import com.westudio.wecampus.util.Constants;
import com.westudio.wecampus.util.DateUtil;
import com.westudio.wecampus.util.ImageUtil;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;


/**
 * Created by Martian on 13-10-24.
 */
public class UserHomepageFragment extends BaseFragment implements OnRefreshListener {

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
    private FollowUserHandler followUserHandler;
    private User mUser;

    //Widgets
    private TextView tvUserName;
    private TextView tvUserWords;
    private TextView tvUserFollow;
    private TextView tvUserFans;
    private ImageView ivUserAvatar;
    private TextView tvMoreActivity;
    private TextView tvAttendActivity;

    private FollowButton btnFollow;

    private boolean from_user_list = false;

    private Gson gson = new Gson();

    public static UserHomepageFragment newInstance(Bundle args) {
        UserHomepageFragment fragment = new UserHomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        mUser = (User)bundle.getSerializable(UserHomepageActivity.USER);
        uid = mUser.id;
        from_user_list = bundle.getBoolean(UserHomepageActivity.USER_LIST, false);
        mDataHelper = new UserDataHelper(mActivity);
        mInfoHandler = new UserInfoHandler();
        followUserHandler = new FollowUserHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user_homepage, container, false);

        mPullToRefreshAttacher = ((UserHomepageActivity)mActivity).getPullToRefreshAttacher();

        initWidget(mView);

        setupUI();
        mInfoHandler.fetchUserInfo();

        return mView;
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
        btnFollow = (FollowButton)view.findViewById(R.id.btn_follow);
        btnFollow.setVisibility(View.VISIBLE);
        btnFollow.setOnStateChangeListener(new FollowButton.OnStateChangeListener() {
            @Override
            public void onFollowListener() {
                followUserHandler.follow(true);
            }

            @Override
            public void onUnFollowListener() {
                followUserHandler.follow(false);
            }
        });

        mJActivityHandler = new UserActivityHandler(mView);
        mFActivityHandler = new UserFActivityHandler(mView);
        mOrganizationHandler = new UserOrganizationHandler(mView);
    }

    private void setupHeader() {
        tvUserName.setText(mUser.nickname);
        tvUserWords.setText(mUser.words);
        tvUserFollow.setText(String.valueOf(mUser.count_of_followers));
        tvUserFans.setText(String.valueOf(mUser.count_of_fans));
        if(BaseApplication.getInstance().hasAccount) {
            if(from_user_list) {
                btnFollow.setFollowState(FollowButton.FollowState.FOLLOWING);
            } else if(mUser.can_follow) {
                btnFollow.setFollowState(FollowButton.FollowState.UNFOLLOWED);
            } else {
                btnFollow.setFollowState(FollowButton.FollowState.FOLLOWING);
            }
        } else {
            btnFollow.setFollowState(FollowButton.FollowState.UNFOLLOWED);
        }
        if(Constants.IMAGE_NOT_FOUND.equals(mUser.avatar)) {
            if(Constants.MALE.equals(mUser.gender)) {
                ivUserAvatar.setImageBitmap(ImageUtil.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_male)));
            } else {
                ivUserAvatar.setImageBitmap(ImageUtil.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_female)));
            }
        } else {
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
                    if(Constants.MALE.equals(mUser.gender)) {
                        ivUserAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_male));
                    } else {
                        ivUserAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_female));
                    }
                }
            });
        }
    }

    private void setupUI() {
        setupHeader();

        if(mUser.attend_activity != null) {
            mJActivityHandler.setupUI(gson.fromJson(mUser.attend_activity, ActivityList.class));
        }
        if(mUser.like_organization != null) {
            mOrganizationHandler.setupUI(gson.fromJson(mUser.like_organization, Organization.class));
        }
        if(mUser.like_activity != null) {
            mFActivityHandler.setupUI(gson.fromJson(mUser.like_activity, ActivityList.class));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Update the ui
     */
    private void updateUI() {
        setupHeader();
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
            }
        }
    };

    @Override
    public void onRefreshStarted(View view) {
        mPullToRefreshAttacher.setRefreshComplete();
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
            setupUI(ac);
            if(from_user_list) {
                mUser.attend_activity = gson.toJson(ac);
                mDataHelper.update(mUser);
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
                WeCampusApi.getUserJActivity(UserHomepageFragment.this, uid, this, this);
            }
        }

        //Set up the joined activity part
        public void setupUI(ActivityList ac) {
            rlActivityListItem.setVisibility(View.VISIBLE);
            tvAttendActivity.setVisibility(View.VISIBLE);
            if(mUser.count_of_join_activities > 1) {
                tvMoreActivity.setVisibility(View.VISIBLE);
                tvMoreActivity.setText(getResources().getString(R.string.view_more_activities));
            }
            String attend = getResources().getString(R.string.attend_activity_num);
            tvAttendActivity.setText(String.format(attend, mUser.count_of_join_activities));
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
    }

    private class UserOrganizationHandler implements Response.Listener<Organization.OrganizationRequestData>, Response.ErrorListener {

        private View mView;
        private RelativeLayout rlLikeOrganization;
        private TextView tvNumOrg;
        private TextView tvOrgName;
        private TextView tvLikeOrg;
        private ImageView ivIcon;

        public UserOrganizationHandler(View view) {
            this.mView = view;
            rlLikeOrganization = (RelativeLayout)mView.findViewById(R.id.user_like_organization);
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
            rlLikeOrganization.setVisibility(View.GONE);
        }

        @Override
        public void onResponse(Organization.OrganizationRequestData organizationRequestData) {
            Organization organization = organizationRequestData.getObjects().get(0);
            setupUI(organization);
            if(from_user_list) {
                mUser.like_organization = gson.toJson(organization);
                mDataHelper.update(mUser);
            }
        }

        public void refreshUI() {
            if(mUser.count_of_follow_organizations != 0) {
                WeCampusApi.getOrganization(UserHomepageFragment.this, uid, this, this);
            }
        }

        public void setupUI(Organization organization) {
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
    }

    private class UserFActivityHandler implements Response.Listener<ActivityList.RequestData>, Response.ErrorListener {

        private View view;
        private RelativeLayout rlFavoriteActivity;
        private TextView tvNumActivity;
        private TextView tvActivityName;
        private TextView tvActivitySummary;
        private ImageView ivActivityIcon;

        public UserFActivityHandler(View view) {
            this.view = view;
            rlFavoriteActivity = (RelativeLayout)view.findViewById(R.id.user_like_activity);
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
            rlFavoriteActivity.setVisibility(View.GONE);
        }

        @Override
        public void onResponse(ActivityList.RequestData requestData) {
            ActivityList ac = requestData.getObjects().get(0);
            setupUI(ac);
            if(from_user_list) {
                mUser.like_activity = gson.toJson(ac);
                mDataHelper.update(mUser);
            }
        }

        public void refreshUI() {
            if(mUser.count_of_follow_activities != 0) {
                WeCampusApi.getUserFActivity(UserHomepageFragment.this, uid, this, this);
            }
        }

        public void setupUI(ActivityList ac) {
            rlFavoriteActivity.setVisibility(View.VISIBLE);
            tvActivityName.setText(ac.title);
            tvActivitySummary.setText(ac.summary);
            String activityNum = getResources().getString(R.string.homepage_like_activity);
            tvNumActivity.setText(String.format(activityNum, mUser.count_of_follow_activities));
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
    }

    private class UserInfoHandler implements Response.Listener<User>, Response.ErrorListener {

        public UserInfoHandler() {

        }

        //Get the user information from network
        public void fetchUserInfo() {
            mPullToRefreshAttacher.setRefreshing(true);
            WeCampusApi.getUserInfoById(UserHomepageFragment.this, uid, this, this);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //Toast notification
            Toast.makeText(mActivity, getString(R.string.get_info_error), Toast.LENGTH_SHORT).show();
            mPullToRefreshAttacher.setRefreshComplete();
        }

        @Override
        public void onResponse(User user) {
            mPullToRefreshAttacher.setRefreshComplete();
            mUser = user;
            updateUI();
            mJActivityHandler.refreshUI();
            mOrganizationHandler.refreshUI();
            mFActivityHandler.refreshUI();
            if(from_user_list) {
                mDataHelper.update(mUser);
            }
        }
    }

    private class FollowUserHandler implements Response.Listener<User>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if(mUser.can_follow) {
                btnFollow.setFollowState(FollowButton.FollowState.UNFOLLOWED);
            } else {
                btnFollow.setFollowState(FollowButton.FollowState.FOLLOWING);
            }
        }

        @Override
        public void onResponse(User user) {
            mUser = user;
            if(user.can_follow) {
                btnFollow.setFollowState(FollowButton.FollowState.UNFOLLOWED);
                tvUserFans.setText(String.valueOf(user.count_of_fans));
                Toast.makeText(mActivity, getResources().getString(R.string.unfollow_user_success), Toast.LENGTH_SHORT).show();
            } else {
                btnFollow.setFollowState(FollowButton.FollowState.FOLLOWING);
                tvUserFans.setText(String.valueOf(user.count_of_fans));
                Toast.makeText(mActivity, getResources().getString(R.string.follow_user_success), Toast.LENGTH_SHORT).show();
            }
        }

        public void follow(boolean can_follow) {
            if(can_follow) {
                WeCampusApi.followUser(UserHomepageFragment.this, uid, this, this);
            } else {
                WeCampusApi.unFollowUser(UserHomepageFragment.this, uid, this, this);
            }
        }
    }
}
