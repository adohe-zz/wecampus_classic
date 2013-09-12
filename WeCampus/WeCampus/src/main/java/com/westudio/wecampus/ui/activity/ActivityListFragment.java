package com.westudio.wecampus.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.main.MainActivity;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;

/**
 * Created by martian on 13-9-11.
 */
public class ActivityListFragment extends Fragment implements OnRefreshListener {

    private PullToRefreshAttacher mPullToRefreshAttacher;

    public static ActivityListFragment newInstance(Bundle args) {
        ActivityListFragment f = new ActivityListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        ListView listView = (ListView) view.findViewById(R.id.activity_list);
        listView.setAdapter(new ActivityAdapter(getActivity()));
        mPullToRefreshAttacher = ((MainActivity) getActivity()).getPullToRefreshAttacher();
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

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
}
