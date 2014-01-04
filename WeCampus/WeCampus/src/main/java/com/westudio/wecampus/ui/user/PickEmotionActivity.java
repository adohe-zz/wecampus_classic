package com.westudio.wecampus.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.util.Constants;

/**
 * Created by nankonami on 13-12-15.
 */
public class PickEmotionActivity extends SherlockFragmentActivity implements View.OnClickListener {

    private String emotion;
    private int lastClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_emotion);

        emotion = getIntent().getStringExtra(MyProfileActivity.PICK_EMOTION);
        updateActionBar();
        initWidget();
    }

    @Override
    public void onClick(View v) {
        if(lastClick != 0) {
            findViewById(lastClick).findViewById(R.id.imageView).setVisibility(View.GONE);
        }
        lastClick = v.getId();
        v.findViewById(R.id.imageView).setVisibility(View.VISIBLE);

        int emotion = 0;
        switch (v.getId()) {
            case R.id.item_love_secret:
                emotion = 1;
                break;
            case R.id.item_love_first:
                emotion = 2;
                break;
            case R.id.item_love_single:
                emotion = 3;
                break;
            case R.id.item_love_fuck:
                emotion = 4;
                break;
            case R.id.item_love_going:
                emotion = 5;
                break;
            case R.id.item_love_break:
                emotion = 6;
                break;
        }

        Handler handler = new Handler();
        final int finalType = emotion;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra(MyProfileActivity.PICK_EMOTION, finalType);
                setResult(MyProfileActivity.PICK_EMOTION_RESULT, intent);
                finish();
            }
        };

        handler.postDelayed(runnable, 400);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initWidget() {
        View sercet = findViewById(R.id.item_love_secret);
        sercet.setOnClickListener(this);
        View first = findViewById(R.id.item_love_first);
        first.setOnClickListener(this);
        View single = findViewById(R.id.item_love_single);
        single.setOnClickListener(this);
        View going = findViewById(R.id.item_love_going);
        going.setOnClickListener(this);
        View fuck = findViewById(R.id.item_love_fuck);
        fuck.setOnClickListener(this);
        View broke = findViewById(R.id.item_love_break);
        broke.setOnClickListener(this);
        if(Constants.LOVE_SECRET.equals(emotion)) {
            sercet.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            lastClick = R.id.item_love_secret;
        } else if(Constants.LOVE_FIRST.equals(emotion)) {
            first.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            lastClick = R.id.item_love_first;
        } else if(Constants.LOVE_SINGLE.equals(emotion)) {
            single.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            lastClick = R.id.item_love_single;
        } else if(Constants.LOVE_GOING.equals(emotion)) {
            going.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            lastClick = R.id.item_love_going;
        } else if(Constants.LOVE_FUCK.equals(emotion)) {
            fuck.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            lastClick = R.id.item_love_fuck;
        } else if(Constants.LOVE_BREAK.equals(emotion)) {
            broke.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            lastClick = R.id.item_love_break;
        } else {
            lastClick = 0;
        }
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.relationship));
    }
}
