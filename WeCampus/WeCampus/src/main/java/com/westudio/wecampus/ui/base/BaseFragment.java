package com.westudio.wecampus.ui.base;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;
import com.westudio.wecampus.data.BaseDataHelper;

/**
 * Created by nankonami on 13-9-9.
 * Base Fragment and every fragment should extend this
 * class
 */
public class BaseFragment extends SherlockFragment {

    public static final String TAG = "FRAGMENT";

    protected boolean isFirstTimeStartFlag = true;
    public static final int FIRST_TIME_START = 0; //when activity is first time start
    public static final int SCREEN_ROTATE = 1;    //when activity is destroyed and recreated because a configuration change, see setRetainInstance(boolean retain)
    public static final int ACTIVITY_DESTROY_AND_CREATE = 2;

    public StartMode startMode;

    protected int getCurrentState(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            isFirstTimeStartFlag = false;
            return ACTIVITY_DESTROY_AND_CREATE;
        }

        if (!isFirstTimeStartFlag) {
            return SCREEN_ROTATE;
        }

        isFirstTimeStartFlag = false;
        return FIRST_TIME_START;
    }

    public static enum StartMode {
        BASIC
    }
}
