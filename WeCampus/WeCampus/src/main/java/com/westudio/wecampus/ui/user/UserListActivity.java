package com.westudio.wecampus.ui.user;

import android.os.Bundle;
import android.view.KeyEvent;

import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseGestureActivity;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

/**
 * Created by nankonami on 13-12-11.<br/>
 * 用于展示用户列表的界面
 * 传入key为UserListFragment.USER_LIST_TYPE的int表示要展示的用户列表类型
 * <ul>
 *     <li>0 代表活动参与者</li>
 *     <li>1 代表粉丝</li>
 *     <li>2 代表关注的人</li>
 * <ul/>
 */
public class UserListActivity extends BaseGestureActivity {

    private PullToRefreshAttacher mPullToRefreshAttacher;
    private int titleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
        updateActionBar();

        getSupportFragmentManager().beginTransaction().replace(R.id.user_list_frame, UserListFragment.newInstance(
                getIntent().getExtras()), null).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getPageName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getPageName());
        MobclickAgent.onPause(this);
    }

    private String getPageName() {
        String pageName = "";
        switch (getIntent().getIntExtra(UserListFragment.USER_LIST_TYPE, 0)) {
            case UserListFragment.PARTICIPATES:
                pageName = "UserListActivity-Participates";
                break;
            case UserListFragment.FOLLOWERS:
                pageName = "UserListActivity-Followers";
                break;
            case UserListFragment.FANS:
                pageName = "UserListActivity-Fans";
                break;
        }
        return pageName;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // 设置标题
        titleId = 0;
        switch (getIntent().getIntExtra(UserListFragment.USER_LIST_TYPE, 0)) {
            case UserListFragment.PARTICIPATES:
                titleId = R.string.user_list_title_participates;
                break;
            case UserListFragment.FOLLOWERS:
                titleId = R.string.user_list_title_followers;
                break;
            case UserListFragment.FANS:
                titleId = R.string.user_list_title_fans;
                break;
        }
        getSupportActionBar().setTitle(titleId);
    }

    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }


}
