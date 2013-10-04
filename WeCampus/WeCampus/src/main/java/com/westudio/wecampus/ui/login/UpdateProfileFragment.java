package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.main.MainActivity;

/**
 * Created by nankonami on 13-9-20.
 */
public class UpdateProfileFragment extends BaseFragment implements View.OnClickListener {

    private Activity activity;

    private TextView tvSkip;

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

        tvSkip = (TextView)view.findViewById(R.id.rege_step_two_skip);
        tvSkip.setOnClickListener(this);

        return view;
    }

    /**
     * Update the Action Bar Title
     */
    private void updateActionBar() {
        ActionBar actionBar = ((AuthActivity)activity).getSupportActionBar();
        actionBar.setTitle(R.string.rege_actionbar_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rege_step_two_skip: {
                startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
                break;
            }
        }
    }
}
