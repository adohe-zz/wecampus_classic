package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.about.TermsOfUseActivity;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.util.Utility;

/**
 * Created by nankonami on 13-9-20.
 * Register Fragment
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener{

    private Activity activity;

    private TextView edtSchool;
    private EditText edtEmail;
    private EditText edtPwd;
    private EditText edtNickName;
    private TextView edtSex;
    private TextView tvSchool;
    private TextView tvEmail;
    private TextView tvPwd;
    private TextView tvNickName;
    private TextView tvSex;
    private Button btnSubmit;
    private TextView tvTipsTwo;

    public static RegisterFragment newInstance(Bundle bundle) {
        RegisterFragment fragment = new RegisterFragment();

        if(bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    public RegisterFragment() {

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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        edtSchool = (TextView)view.findViewById(R.id.rege_edt_school);
        edtSchool.setOnClickListener(this);
        edtEmail = (EditText)view.findViewById(R.id.rege_edt_email);
        edtPwd = (EditText)view.findViewById(R.id.rege_edt_pwd);
        edtNickName = (EditText)view.findViewById(R.id.rege_edt_nickname);
        edtSex = (TextView)view.findViewById(R.id.rege_edt_sex);
        edtSex.setOnClickListener(this);
        tvSchool = (TextView)view.findViewById(R.id.rege_tv_school);
        tvSchool.setText("学        校");
        tvEmail = (TextView)view.findViewById(R.id.rege_tv_email);
        tvEmail.setText("邮        箱");
        tvPwd = (TextView)view.findViewById(R.id.rege_tv_pwd);
        tvPwd.setText("密        码");
        tvNickName = (TextView)view.findViewById(R.id.rege_tv_nickname);
        tvNickName.setText("昵        称");
        tvSex = (TextView)view.findViewById(R.id.rege_tv_sex);
        tvSex.setText("性        别");
        btnSubmit = (Button)view.findViewById(R.id.rege_btn_register);
        btnSubmit.setOnClickListener(this);
        tvTipsTwo = (TextView)view.findViewById(R.id.rege_tips_two);
        tvTipsTwo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rege_btn_register) {
            //TODO:Handle Register Event
            FragmentTransaction fragmentTransaction = ((AuthActivity)activity).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
            fragmentTransaction.replace(R.id.auth_container, UpdateProfileFragment.newInstance(null), AuthActivity.UPDATE_PROFILE_TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if(v.getId() == R.id.rege_tips_two) {
            startActivity(new Intent(activity, TermsOfUseActivity.class));
        } else if(v.getId() == R.id.rege_edt_school) {
            startActivityForResult(new Intent(activity, PickSchoolActivity.class), AuthActivity.PICK_SCHOOL_REQUEST);
        } else if(v.getId() == R.id.rege_edt_sex) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == AuthActivity.PICK_SCHOOL_RESULT) {
            edtSchool.setText(data.getStringExtra(AuthActivity.PICK_SCHOOL_NAME));
        }
    }

    /**
     * Check the parameters validation
     * @return
     */
    private boolean checkValidation() {
        boolean result = true;

        return result;
    }

    /**
     * Update the action bar title
     */
    private void updateActionBar() {
        ActionBar actionBar = ((AuthActivity)activity).getSupportActionBar();
        actionBar.setTitle(R.string.rege_actionbar_title);
    }
}
