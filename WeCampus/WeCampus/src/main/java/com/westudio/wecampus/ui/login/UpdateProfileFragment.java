package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseFragment;

/**
 * Created by nankonami on 13-9-20.
 */
public class UpdateProfileFragment extends BaseFragment {

    private Activity activity;

    private TextView tvRegTitle;

    public static UpdateProfileFragment newInstance(Bundle bundle) {
        UpdateProfileFragment fragment = new UpdateProfileFragment();

        if(bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    public UpdateProfileFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_two, container, false);

        tvRegTitle = (TextView)view.findViewById(R.id.rege_step_two_title);
        tvRegTitle.setText("注册成功!你可以先完善一些个人资料\n\t这些信息会对你的好友开放");

        return view;
    }

    /**
     * Update the Action Bar Title
     */
    private void updateActionBar() {
        ActionBar actionBar = ((AuthActivity)activity).getSupportActionBar();
        actionBar.setTitle(R.string.rege_actionbar_title);
    }
}