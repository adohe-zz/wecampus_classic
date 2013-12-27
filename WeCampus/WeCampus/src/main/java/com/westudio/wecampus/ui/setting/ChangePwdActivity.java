package com.westudio.wecampus.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseGestureActivity;

/**
 * Created by nankonami on 13-12-4.
 */
public class ChangePwdActivity extends BaseGestureActivity {

    private EditText edtOldPwd;
    private EditText edtNewPwd;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        updateActionBar();
        initWidget();
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
                WeCampusApi.updatePwd(ChangePwdActivity.this, edtOldPwd.getText().toString(),
                        edtNewPwd.getText().toString(), new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        Toast.makeText(ChangePwdActivity.this, R.string.change_success,
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(ChangePwdActivity.this, R.string.change_fail,
                                        Toast.LENGTH_SHORT).show();
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
}
