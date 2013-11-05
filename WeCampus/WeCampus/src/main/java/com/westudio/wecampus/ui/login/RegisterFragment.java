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
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.about.TermsOfUseActivity;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.util.Utility;

/**
 * Created by nankonami on 13-9-20.
 * Register Fragment
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener,
        Response.ErrorListener, Response.Listener<User>{

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

    private int schoolId = -1;
    private Gender gender;

    public enum Gender {
        MALE("male"), FEMALE("female"), SECRET("secret");

        public String genderMark;
        private Gender(String gender) {
            this.genderMark = gender;
        }
    }

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
//        tvSchool.setText("学        校");
        tvEmail = (TextView)view.findViewById(R.id.rege_tv_email);
//        tvEmail.setText("邮        箱");
        tvPwd = (TextView)view.findViewById(R.id.rege_tv_pwd);
//        tvPwd.setText("密        码");
        tvNickName = (TextView)view.findViewById(R.id.rege_tv_nickname);
//        tvNickName.setText("昵        称");
        tvSex = (TextView)view.findViewById(R.id.rege_tv_sex);
//        tvSex.setText("性        别");
        btnSubmit = (Button)view.findViewById(R.id.rege_btn_register);
        btnSubmit.setOnClickListener(this);
        tvTipsTwo = (TextView)view.findViewById(R.id.rege_tips_two);
        tvTipsTwo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rege_btn_register && checkValidation()) {
            WeCampusApi.postRegister(this, edtEmail.getText().toString(),
                    edtNickName.getText().toString(), edtPwd.getText().toString(),
                    "male", String.valueOf(schoolId), this, this);
        } else if(v.getId() == R.id.rege_tips_two) {
            startActivity(new Intent(activity, TermsOfUseActivity.class));
        } else if(v.getId() == R.id.rege_edt_school) {
            startActivityForResult(new Intent(activity, PickSchoolActivity.class), AuthActivity.PICK_SCHOOL_REQUEST);
        } else if(v.getId() == R.id.rege_edt_sex) {
            //startActivityForResult(new Intent(activity, PickSchoolActivity.class), AuthActivity.PICK_GENDER_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == AuthActivity.PICK_SCHOOL_RESULT) {
            edtSchool.setText(data.getStringExtra(AuthActivity.PICK_SCHOOL_NAME));
            schoolId = data.getIntExtra(AuthActivity.PICK_SCHOOL_ID, -1);
            Utility.log("school_id", schoolId);
        } else if (resultCode == AuthActivity.PICK_GENDER_RESULT) {
            int type = data.getIntExtra(AuthActivity.PICK_GENDER, 2);
            gender = (type == 0 ? Gender.MALE : (type == 1 ? Gender.FEMALE : Gender.SECRET));
            edtSex.setText(gender.genderMark);
        }
    }

    /**
     * Check the parameters validation
     * @return
     */
    private boolean checkValidation() {
        boolean result = false;
        if (schoolId == -1) {
            Toast.makeText(getActivity(), R.string.msg_school_not_selected, Toast.LENGTH_SHORT).show();
            return result;
        } else if (!edtEmail.getText().toString().matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            Toast.makeText(getActivity(), R.string.msg_error_email_format, Toast.LENGTH_SHORT).show();
            return result;
        } else if (edtPwd.getText().length() == 0) {
            Toast.makeText(getActivity(), R.string.msg_please_input_pwd, Toast.LENGTH_SHORT).show();
            return result;
        } else if (edtNickName.getText().length() == 0) {
            Toast.makeText(getActivity(), R.string.msg_please_input_nickname, Toast.LENGTH_SHORT).show();
            return result;
        } else if (gender == null) {
            //Toast.makeText(getActivity(), R.string.msg_please_input_gender, Toast.LENGTH_SHORT).show();
            result = true;
            return result;
        }

        result = true;
        return result;
    }


    /**
     * Update the action bar title
     */
    private void updateActionBar() {
        ActionBar actionBar = ((AuthActivity)activity).getSupportActionBar();
        actionBar.setTitle(R.string.rege_actionbar_title);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(getActivity(), "Register error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(User user) {
        Toast.makeText(getActivity(), "Regitster success", Toast.LENGTH_SHORT).show();
        FragmentTransaction fragmentTransaction =
                ((AuthActivity)activity).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
        fragmentTransaction.replace(R.id.auth_container, UpdateProfileFragment.newInstance(null),
                AuthActivity.UPDATE_PROFILE_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
