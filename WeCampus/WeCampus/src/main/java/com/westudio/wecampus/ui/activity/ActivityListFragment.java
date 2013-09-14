package com.westudio.wecampus.ui.activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.ActivityDataHelper;
import com.westudio.wecampus.modle.Activity;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.util.Utility;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;

/**
 * Created by martian on 13-9-11.
 */
public class ActivityListFragment extends Fragment implements OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private PullToRefreshAttacher mPullToRefreshAttacher;
    private ActivityAdapter mAdapter;
    private ActivityDataHelper mDataHelper;

    public static ActivityListFragment newInstance(Bundle args) {
        ActivityListFragment f = new ActivityListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        ListView listView = (ListView) view.findViewById(R.id.activity_list);
        mAdapter = new ActivityAdapter(getActivity(), listView);
        listView.setAdapter(mAdapter);

        mPullToRefreshAttacher = ((MainActivity) getActivity()).getPullToRefreshAttacher();
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

        //****Test ActivityDataHelper
        String json = "{\"Id\":755,\"Image\":\"http://we.tongji.edu.cn\"}";
        mDataHelper = new ActivityDataHelper(BaseApplication.getContext());
        getLoaderManager().initLoader(0, null, this);
        Activity ac = Activity.fromJson(json);
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
}
