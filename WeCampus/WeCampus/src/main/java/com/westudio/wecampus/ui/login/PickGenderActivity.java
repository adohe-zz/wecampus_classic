package com.westudio.wecampus.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;

/**
 * Created by jam on 13-11-5.
 */
public class PickGenderActivity extends SherlockFragmentActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        setContentView(R.layout.activity_pick_gender);

        View male = findViewById(R.id.item_male);
        male.setOnClickListener(this);
        View female = findViewById(R.id.item_female);
        male.setOnClickListener(this);
        View secret = findViewById(R.id.item_secret);
        male.setOnClickListener(this);

    }

    /**
     * Set the action bar style
     */
    private void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int type = 0;
        switch (view.getId()) {
            case R.id.item_male: {
                type = 0;
                break;
            }
            case R.id.item_female: {
                type = 1;
                break;
            }
            case R.id.item_secret: {
                type = 2;
                break;
            }
        }
        Intent intent = new Intent();
        intent.putExtra(AuthActivity.PICK_GENDER, type);
        setResult(AuthActivity.PICK_GENDER_RESULT, intent);
        finish();
    }
}
