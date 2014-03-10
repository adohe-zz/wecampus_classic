package com.westudio.wecampus.ui.setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.BaseGestureActivity;
import com.westudio.wecampus.ui.login.AuthActivity;

/**
 * Created by nankonami on 13-12-4.
 */
public class ChangePwdActivity extends BaseGestureActivity {

    private EditText edtOldPwd;
    private EditText edtNewPwd;
    private Button btnSubmit;
    private ProgressDialog progressDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        updateActionBar();
        initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ChangePwdActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ChangePwdActivity");
        MobclickAgent.onPause(this);
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.setting_change_pwd);
    }

    private void initWidget() {
        edtOldPwd = (EditText)findViewById(R.id.change_old_pwd);
        edtNewPwd = (EditText)findViewById(R.id.change_new_pwd);
        btnSubmit = (Button)findViewById(R.id.change_submit);
        btnSubmit.setOnClickListener(clickListener);
    }



    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(checkValidation()) {
                if (progressDailog == null) {
                    progressDailog = new ProgressDialog(ChangePwdActivity.this);
                    progressDailog.setMessage(getString(R.string.please_wait));
                }
                progressDailog.show();

                WeCampusApi.updatePwd(ChangePwdActivity.this, edtOldPwd.getText().toString(),
                        edtNewPwd.getText().toString(), new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        progressDailog.dismiss();
                        Toast.makeText(ChangePwdActivity.this, R.string.change_success,
                                Toast.LENGTH_SHORT).show();
                        clearAccountAndReturnToMain();
                        finish();

                    }
                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                progressDailog.dismiss();

                                //如果为networkResponse为空,默认为成功
                                if (volleyError.networkResponse == null) {
                                    Toast.makeText(ChangePwdActivity.this, R.string.change_success,
                                            Toast.LENGTH_SHORT).show();
                                    clearAccountAndReturnToMain();
                                    finish();
                                } else {
                                    Toast.makeText(ChangePwdActivity.this, R.string.msg_change_pwd_fail,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    };

    private boolean checkValidation() {
        boolean result = true;
        if(edtOldPwd.getText().toString().length() == 0) {
            result = false;
            Toast.makeText(this, getResources().getString(R.string.input_old_pwd), Toast.LENGTH_SHORT).show();
        }
        if(edtNewPwd.getText().toString().length() == 0) {
            result = false;
            Toast.makeText(this, getResources().getString(R.string.input_new_pwd), Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void clearAccountAndReturnToMain() {
        BaseApplication app = BaseApplication.getInstance();
        app.hasAccount = false;
        app.getAccountMgr().clearAccountInfo();
        Intent intent = new Intent(ChangePwdActivity.this, AuthActivity.class);
        intent.putExtra(AuthActivity.FLAG_CHANGE_PWD, true);
        startActivity(intent);
    }
}
