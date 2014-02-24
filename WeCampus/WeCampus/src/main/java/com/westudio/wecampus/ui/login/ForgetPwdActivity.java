package com.westudio.wecampus.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.WeCampusApi;

/**
 * Created by nankonami on 13-12-8.
 */
public class ForgetPwdActivity extends SherlockFragmentActivity {

    private EditText etEmail;
    private Button btnSubmit;

    private ProgressDialog pb;

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
                if(checkValidation()) {
                    pb = new ProgressDialog(ForgetPwdActivity.this);
                    pb.setMessage(getResources().getString(R.string.please_wait));
                    pb.show();
                    WeCampusApi.forgetPwd(ForgetPwdActivity.this, etEmail.getText().toString(), new Response.Listener() {
                        @Override
                        public void onResponse(Object o) {
                            pb.dismiss();
                            Toast.makeText(ForgetPwdActivity.this, R.string.forget_pwd_rec, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    pb.dismiss();
                                    Toast.makeText(ForgetPwdActivity.this, R.string.forget_pwd_rec, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(ForgetPwdActivity.this, R.string.please_input_email_reg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkValidation() {
        boolean result = true;

        if(TextUtils.isEmpty(etEmail.getText().toString())) {
            result = false;
        }

        return result;
    }
}
