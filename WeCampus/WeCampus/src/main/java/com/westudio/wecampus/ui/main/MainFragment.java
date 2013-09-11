package com.westudio.wecampus.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseFragment;

/**
 * Created by nankonami on 13-9-11.
 * The main fragment that display the activity list
 */
public class MainFragment extends BaseFragment {

    private Activity activity;
    private ListView listView;

    /**
     * Static creation method to create a new main fragment instance
     * @return
     */
    private static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView)view.findViewById(R.id.main_listview);

        return view;
    }
}
