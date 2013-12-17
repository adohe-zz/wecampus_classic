package com.westudio.wecampus.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);

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
                        intent.putExtra(MyProfileActivity.NICK_NAME, etNickName.getText().toString());
                        setResult(MyProfileActivity.UPDATE_NICK_RESULT, intent);
                        finish();
                    }
                };
                handler.postDelayed(runnable, 400);
            } else {

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initWidget() {
        etNickName = (EditText)findViewById(R.id.update_nick_name);
        etNickName.setText(getIntent().getStringExtra(MyProfileActivity.NICK_NAME));
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.rege_nickname);
    }

    private boolean isValidate() {
        boolean result = true;

        if(etNickName.getText().toString().length() == 0) {
            result = false;
        } else if(etNickName.getText().toString().length() > 8) {
            result = false;
        }

        return result;
    }
}
