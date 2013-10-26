package com.westudio.wecampus.ui.base;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.westudio.wecampus.R;
import com.westudio.wecampus.util.SwipeToCloseListener;

/**
 * Created by martian on 13-9-21.
 */
public class BaseDetailActivity extends SherlockFragmentActivity {

    protected GestureDetector gestureDector;
    private ViewStub vsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestureDector = new GestureDetector(this, new SwipeToCloseListener(this));
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(R.layout.base_detail_frame);
        vsContent = (ViewStub)findViewById(R.id.view_stub);
        vsContent.setLayoutResource(layoutResId);
        vsContent.inflate();
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
}
