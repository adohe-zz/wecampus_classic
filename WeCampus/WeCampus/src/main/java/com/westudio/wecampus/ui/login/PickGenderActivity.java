package com.westudio.wecampus.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.user.MyProfileActivity;
import com.westudio.wecampus.util.Constants;

/**
 * Created by jam on 13-11-5.
 */
public class PickGenderActivity extends SherlockFragmentActivity implements View.OnClickListener{

    private String gender;
    private int lastClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_gender);

        gender = getIntent().getStringExtra(MyProfileActivity.GENDER);
        setupActionBar();
        initWidget();
    }

    /**
     * Set the action bar style
     */
    private void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initWidget() {
        View male = findViewById(R.id.item_male);
        male.setOnClickListener(this);
        View female = findViewById(R.id.item_female);
        female.setOnClickListener(this);
        if(Constants.MALE.equals(gender)) {
            male.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            lastClick = R.id.item_male;
        } else if(Constants.FEMALE.equals(gender)) {
            female.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            lastClick = R.id.item_female;
        } else {
            lastClick = 0;
        }
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
        if(lastClick != 0) {
            findViewById(lastClick).findViewById(R.id.imageView).setVisibility(View.GONE);
        }
        lastClick = view.getId();
        //show the selected icon
        view.findViewById(R.id.imageView).setVisibility(View.VISIBLE);

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
        }

        Handler handler = new Handler();
        final int finalType = type;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra(AuthActivity.PICK_GENDER, finalType);
                setResult(AuthActivity.PICK_GENDER_RESULT, intent);
                finish();
            }
        };

        handler.postDelayed(runnable, 400);

    }
}
