package com.westudio.wecampus.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.login.AuthActivity;
import com.westudio.wecampus.ui.main.MainActivity;

/**
 * Created by nankonami on 13-9-11.
 * The Introduction activity
 */
public class IntroActivity extends SherlockFragmentActivity implements View.OnClickListener{

    private Button btnNotNow;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_intro);

        setUpActionBar();

        initWidget();
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initWidget() {
        btnNotNow = (Button)findViewById(R.id.intro_no_login);
        btnNotNow.setOnClickListener(this);
        btnLogin = (Button)findViewById(R.id.intro_login_sign);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v.getId() == R.id.intro_no_login) {
            intent.setClass(this, MainActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up_in, R.anim.push_out);
        } else if(v.getId() == R.id.intro_login_sign) {
            intent.setClass(this, AuthActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up_in, R.anim.push_out);
        }
    }
}
