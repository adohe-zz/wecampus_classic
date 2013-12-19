package com.westudio.wecampus.ui.base;

import com.actionbarsherlock.app.SherlockFragment;

import java.util.HashMap;

/**
 * Created by nankonami on 13-9-9.
 * Base Fragment and every fragment should extend this
 * class
 */
public class BaseFragment extends SherlockFragment {

    protected HashMap<String, String> colors;

    public BaseFragment() {
        super();
        colors = BaseApplication.getInstance().getCategoryColors();
    }
}
