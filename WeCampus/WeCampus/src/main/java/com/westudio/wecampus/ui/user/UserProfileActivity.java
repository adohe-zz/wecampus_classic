package com.westudio.wecampus.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.activity.ActivityAdapter;
import com.westudio.wecampus.ui.adapter.CardsAnimationAdapter;
import com.westudio.wecampus.ui.base.BaseDetailActivity;
import com.westudio.wecampus.ui.view.LoadingFooter;

/**
 * Created by nankonami on 13-10-6.
 * User Profile Activity
 */
public class UserProfileActivity extends BaseDetailActivity {

    private ListView listView;
    private ActivityAdapter activityAdapter;
    private LoadingFooter loadingFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        listView = (ListView)findViewById(R.id.user_profile_listview);
        View header = new View(this);
        loadingFooter = new LoadingFooter(this);
        activityAdapter = new ActivityAdapter(this, listView);
        CardsAnimationAdapter animationAdapter = new CardsAnimationAdapter(this, activityAdapter);
        animationAdapter.setListView(listView);

        listView.addHeaderView(header);
        listView.addFooterView(loadingFooter.getView());
        listView.setAdapter(animationAdapter);

        updateActionBar();
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
