package com.westudio.wecampus.ui.user;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.UserDataHelper;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseApplication;
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
