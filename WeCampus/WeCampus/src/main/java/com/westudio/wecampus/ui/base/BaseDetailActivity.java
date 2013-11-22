package com.westudio.wecampus.ui.base;

import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.westudio.wecampus.R;

/**
 * Created by martian on 13-9-21.
 */
public class BaseDetailActivity extends BaseGestureActivity {

    private ViewStub vsContent;

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(R.layout.base_detail_frame);
        vsContent = (ViewStub)findViewById(R.id.view_stub);
        vsContent.setLayoutResource(layoutResId);
        vsContent.inflate();
    }

    public void showBottomActionBar() {
        RelativeLayout bottomActionBar = (RelativeLayout)this.findViewById(R.id.detail_bottom_bar);
        bottomActionBar.setVisibility(View.VISIBLE);
    }
}
