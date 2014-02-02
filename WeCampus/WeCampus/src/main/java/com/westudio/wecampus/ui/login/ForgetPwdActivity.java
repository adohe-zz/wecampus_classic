package com.westudio.wecampus.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.R;

/**
 * Created by nankonami on 13-12-8.
 */
public class ForgetPwdActivity extends SherlockFragmentActivity {

    private EditText etEmail;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        updateActionBar();
        initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ForgetPwdActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ForgetPwdActivity");
        MobclickAgent.onPause(this);
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.forget_pwd_title);
    }

    private void initWidget() {
        etEmail = (EditText)findViewById(R.id.forget_email);
        btnSubmit = (Button)findViewById(R.id.forget_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
