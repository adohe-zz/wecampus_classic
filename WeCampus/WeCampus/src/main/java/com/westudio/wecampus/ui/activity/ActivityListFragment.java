package com.westudio.wecampus.ui.activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.ActivityDataHelper;
import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.ui.adapter.CardsAnimationAdapter;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.util.Utility;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;

/**
 * Created by martian on 13-9-11.
 * Fragment that display the activity list
 */
public class ActivityListFragment extends BaseFragment implements OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor>, Response.ErrorListener, Response.Listener<Activity> {

    private PullToRefreshAttacher mPullToRefreshAttacher;
    private ActivityAdapter mAdapter;
    private ActivityDataHelper mDataHelper;

    private android.app.Activity activity;
    private ListView listView;

    public static ActivityListFragment newInstance(Bundle args) {
        ActivityListFragment f = new ActivityListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(android.app.Activity activity) {
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
        mAdapter = new ActivityAdapter(activity, listView);
        CardsAnimationAdapter animationAdapter = new CardsAnimationAdapter(activity, mAdapter);
        animationAdapter.setListView(listView);
        listView.setAdapter(animationAdapter);
        mPullToRefreshAttacher = ((MainActivity)activity).getPullToRefreshAttacher();
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

        //TODO****Test ActivityDataHelper
        String json = "{\"Id\":755,\"Image\":\"http://we.tongji.edu.cn\"}";
        mDataHelper = new ActivityDataHelper(BaseApplication.getContext());
        getLoaderManager().initLoader(0, null, this);
        com.westudio.wecampus.data.model.Activity ac = com.westudio.wecampus.data.model.Activity.fromJson(json);
        final List<Activity> lstAc = new ArrayList<Activity>();
        for (int i = 0; i < 12; i++) {
            lstAc.add(ac);
        }
        Utility.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                mDataHelper.bulkInsert(lstAc);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Toast.makeText(getActivity(), "InsertSuccess", Toast.LENGTH_SHORT).show();
            }
        });
        //*****Test ActivityDataHelper

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
        return mDataHelper.getCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.changeCursor(null);

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
    public void onResponse(Activity activities) {

    }
}
