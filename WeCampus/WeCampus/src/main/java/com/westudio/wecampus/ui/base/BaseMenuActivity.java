package com.westudio.wecampus.ui.base;

import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Created by martian on 14-3-3.
 */
public class BaseMenuActivity extends SherlockFragmentActivity {

    protected void setWindowStyle() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = new WindowManager.LayoutParams();;
        wlp.copyFrom(window.getAttributes());
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }
}
