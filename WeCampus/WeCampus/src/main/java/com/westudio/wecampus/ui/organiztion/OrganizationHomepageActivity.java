package com.westudio.wecampus.ui.organiztion;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.activity.ActivityAdapter;
import com.westudio.wecampus.ui.base.BaseGestureActivity;
import com.westudio.wecampus.ui.view.HeaderTabBar;
import com.westudio.wecampus.ui.view.LoadingFooter;
import com.westudio.wecampus.ui.view.PinnedHeaderListView;

/**
 * Created by martian on 13-11-22.
 */
public class OrganizationHomepageActivity extends BaseGestureActivity {

    private PinnedHeaderListView mListView;
    private ActivityAdapter activityAdapter;
    private LoadingFooter loadingFooter;
    private HeaderTabBar mPinnedHeader;
    private IntroAdapter mIntroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_homepage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPinnedHeader = (HeaderTabBar) findViewById(R.id.pinned_header);
        mListView = (PinnedHeaderListView) findViewById(R.id.listview);
        mIntroAdapter = new IntroAdapter(this);

        mListView.setmPinnedHeader(mPinnedHeader);
        mListView.setAdapter(new TestAdapter(this));
        mListView.setTabTexts(R.string.posted_activities, R.string.brief_introduction, 0);

        mListView.setHeaderOffScreenListener(new PinnedHeaderListView.OnHeaderOffScreenListener() {
            @Override
            public void onHeaderOffScreen() {
                mPinnedHeader.setVisibility(View.VISIBLE);
            }

            @Override
            public void onHeaderInScreen() {
                mPinnedHeader.setVisibility(View.GONE);
            }
        });

        mListView.setmTabSelectedListener(new PinnedHeaderListView.OnTabSelectedListener() {
            @Override
            public void onTabOneSelected() {

            }

            @Override
            public void onTabTwoSelected() {
                mListView.setAdapter(mIntroAdapter);
            }

            @Override
            public void onTabThreeSelecd() {
            }
        });

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
