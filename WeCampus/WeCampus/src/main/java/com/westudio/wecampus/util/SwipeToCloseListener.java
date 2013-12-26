package com.westudio.wecampus.util;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by jam on 13-9-30.
 */
public class SwipeToCloseListener extends GestureDetector.SimpleOnGestureListener {

    Activity mActivity;
    public SwipeToCloseListener(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1 == null || e2 == null) {
            return false;
        }
        if (e2.getX() - e1.getX() > Constants.SWIPE_MIN_DISTANCE && Math.abs(velocityX) > Constants.SWIPE_MIN_VELOCITY) {
            mActivity.finish();
            return true;
        }
        return false;
    }

}
