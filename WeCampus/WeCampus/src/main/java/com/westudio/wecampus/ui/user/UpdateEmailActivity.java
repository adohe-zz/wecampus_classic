package com.westudio.wecampus.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;

/**
 * Created by nankonami on 13-12-17.
 */
public class UpdateEmailActivity extends SherlockFragmentActivity {

    private EditText etNickName;
    private String email;
    private int toastStringId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        email = getIntent().getStringExtra(MyProfileActivity.EMAIL);
        initWidget();
        updateActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.update_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.update_profile) {
            if(isValidate()) {
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.putExtra(MyProfileActivity.EMAIL, etNickName.getText().toString());
                        setResult(MyProfileActivity.UPDATE_EMAIL_RESULT, intent);
                        finish();
                    }
                };
                handler.postDelayed(runnable, 400);
            } else {
                Toast.makeText(this, toastStringId, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra(MyProfileActivity.EMAIL, email);
            setResult(MyProfileActivity.UPDATE_EMAIL_RESULT, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra(MyProfileActivity.EMAIL, email);
            setResult(MyProfileActivity.UPDATE_EMAIL_RESULT, intent);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    private void initWidget() {
        etNickName = (EditText)findViewById(R.id.update_nick_name);
        etNickName.setText(email);
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.email);
    }

    private boolean isValidate() {
        boolean result = true;

        if(TextUtils.isEmpty(etNickName.getText().toString())) {
            result = false;
            toastStringId = R.string.msg_please_input_email;
        } else if(etNickName.getText().toString().matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            result = false;
            toastStringId = R.string.msg_error_email_format;
        }

        return result;
    }
}
