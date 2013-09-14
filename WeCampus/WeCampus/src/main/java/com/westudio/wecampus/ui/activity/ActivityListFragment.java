package com.westudio.wecampus.ui.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.Activities;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.adapter.ActivityAdapter;
import com.westudio.wecampus.ui.adapter.CardsAnimationAdapter;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.main.MainActivity;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;

/**
 * Created by martian on 13-9-11.
 * Fragment that display the activity list
 */
public class ActivityListFragment extends BaseFragment implements OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor>, Response.ErrorListener, Response.Listener<Activities> {

    private PullToRefreshAttacher mPullToRefreshAttacher;

    private Activity activity;
    private ListView listView;

    private ActivityAdapter adapter;

    public static ActivityListFragment newInstance(Bundle args) {
        ActivityListFragment f = new ActivityListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        listView = (ListView) view.findViewById(R.id.activity_list);
        adapter = new ActivityAdapter(activity, listView);
        CardsAnimationAdapter animationAdapter = new CardsAnimationAdapter(activity, adapter);
        listView.setAdapter(animationAdapter);
        mPullToRefreshAttacher = ((MainActivity)activity).getPullToRefreshAttacher();
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRefreshStarted(View view) {
        //TODO
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                // Notify PullToRefreshAttacher that the refresh has finished
                mPullToRefreshAttacher.setRefreshComplete();
            }
        }.execute();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    /**
     * Request activity for each page
     * @param page
     */
    private void requestActivity(final int page) {
        WeCampusApi.getActivityList(page, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void onResponse(Activities activities) {

    }
}
