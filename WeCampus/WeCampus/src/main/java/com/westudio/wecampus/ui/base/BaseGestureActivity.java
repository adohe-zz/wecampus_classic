package com.westudio.wecampus.ui.base;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.util.SwipeToCloseListener;

/**
 * Created by martian on 13-11-23.
 */
public class BaseGestureActivity extends SherlockFragmentActivity {

    protected GestureDetector gestureDector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestureDector = new GestureDetector(this, new SwipeToCloseListener(this));
    }

    /** register a listener on the top view of activity to enable swipe to close */
    protected void registerSwipeToCloseListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDector.onTouchEvent(motionEvent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
