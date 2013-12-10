package com.westudio.wecampus.ui.list;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.westudio.wecampus.R;

/**
 * Created by nankonami on 13-12-11.
 */
public class ListActivity extends SherlockFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
}
