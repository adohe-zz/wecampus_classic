package com.westudio.wecampus.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseDetailActivity;
import com.westudio.wecampus.ui.view.HeaderTabBar;
import com.westudio.wecampus.ui.view.PinnedHeaderListView;

/**
 * Created by martian on 13-9-21.
 * This Activity is used to display the profile of current user
 */
public class MyProfileActivity extends BaseDetailActivity {

    private HeaderTabBar mPinnedHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myprofile);

        mPinnedHeader = (HeaderTabBar) findViewById(R.id.pinned_header);

        PinnedHeaderListView lv = (PinnedHeaderListView) findViewById(R.id.listview);
        lv.setmPinnedHeader(mPinnedHeader);
        lv.setAdapter(new TestAdapter(this));
        lv.setHeaderOffScreenListener(new PinnedHeaderListView.OnHeaderOffScreenListener() {
            @Override
            public void onHeaderOffScreen() {
                mPinnedHeader.setVisibility(View.VISIBLE);
            }

            @Override
            public void onHeaderInScreen() {
                mPinnedHeader.setVisibility(View.GONE);
            }
        });

        registerSwipeToCloseListener(lv);

    }

    public class TestAdapter extends BaseAdapter {

        private Context mContext;

        public TestAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_user_list, viewGroup, false);
            TextView tv = (TextView) view.findViewById(R.id.user_name);
            tv.setText("NameHahahahhahaah");
            return view;
        }
    }

}
