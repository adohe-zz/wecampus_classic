package com.westudio.wecampus.ui.setting;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.ShareMenuActivity;
import com.westudio.wecampus.ui.main.MainActivity;

/**
 * Created by nankonami on 13-12-2.
 */
public class SettingFragment extends SherlockFragment {

    private Activity mActivity;

    //Widgets
    private RelativeLayout rlLogin;
    private RelativeLayout rlChangePwd;
    private RelativeLayout rlClearCache;
    private RelativeLayout rlShare;
    private RelativeLayout rlFeedback;
    private RelativeLayout rlAbout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        initWidget(view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initWidget(final View view) {
        rlLogin = (RelativeLayout)view.findViewById(R.id.ly_setting_login);
        if(BaseApplication.getInstance().hasAccount) {
            rlLogin.setVisibility(View.GONE);
        }
        rlLogin.setOnClickListener(clickListener);
        rlChangePwd = (RelativeLayout)view.findViewById(R.id.ly_setting_change_pwd);
        rlChangePwd.setOnClickListener(clickListener);
        rlClearCache = (RelativeLayout)view.findViewById(R.id.ly_setting_clear_cache);
        rlClearCache.setOnClickListener(clickListener);
        rlShare = (RelativeLayout)view.findViewById(R.id.ly_setting_share_wecampus);
        rlShare.setOnClickListener(clickListener);
        rlFeedback = (RelativeLayout)view.findViewById(R.id.ly_setting_feedback);
        rlFeedback.setOnClickListener(clickListener);
        rlAbout = (RelativeLayout)view.findViewById(R.id.ly_setting_about_us);
        rlAbout.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ly_setting_change_pwd: {
                    if(BaseApplication.getInstance().hasAccount) {
                        Intent intent = new Intent(mActivity, ChangePwdActivity.class);
                        startActivity(intent);
                        mActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    } else {
                        Toast.makeText(mActivity, R.string.please_login, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case R.id.ly_setting_about_us: {
                    Intent intent = new Intent(mActivity, AboutUsActivity.class);
                    startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    break;
                }
                case R.id.ly_setting_clear_cache:
                    break;
                case R.id.ly_setting_feedback: {
                    Uri emailUri = Uri.parse("mailto:wetongji2012@gmail.com");
                    Intent intent = new Intent(Intent.ACTION_SENDTO, emailUri);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "WeCampus Android Feedback");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case R.id.ly_setting_share_wecampus: {
                    Intent intent = new Intent(mActivity, ShareMenuActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        }
    };
}
