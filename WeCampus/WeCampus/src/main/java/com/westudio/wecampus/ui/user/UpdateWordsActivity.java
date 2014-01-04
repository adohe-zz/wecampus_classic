package com.westudio.wecampus.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
public class UpdateWordsActivity extends SherlockFragmentActivity {
    private EditText etNickName;
    private String words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_words);

        words = getIntent().getStringExtra(MyProfileActivity.WORDS);
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
                        intent.putExtra(MyProfileActivity.WORDS, etNickName.getText().toString());
                        setResult(MyProfileActivity.UPDATE_WORDS_RESULT, intent);
                        finish();
                    }
                };
                handler.postDelayed(runnable, 400);
            } else {
                Toast.makeText(this, R.string.msg_please_input_words, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra(MyProfileActivity.WORDS, words);
            setResult(MyProfileActivity.UPDATE_WORDS_RESULT, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra(MyProfileActivity.WORDS, words);
            setResult(MyProfileActivity.UPDATE_WORDS_RESULT, intent);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    private void initWidget() {
        etNickName = (EditText)findViewById(R.id.update_nick_name);
        etNickName.setText(words);
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.rege_step_two_description);
    }

    private boolean isValidate() {
        boolean result = true;

        if(etNickName.getText().toString().length() == 0) {
            result = false;
        }

        return result;
    }
}
