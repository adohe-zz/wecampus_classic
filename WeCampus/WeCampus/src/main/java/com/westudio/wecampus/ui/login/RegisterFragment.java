package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseFragment;

/**
 * Created by nankonami on 13-9-20.
 * Register Fragment
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener{

    private Activity activity;

    private EditText edtSchool;
    private EditText edtEmail;
    private EditText edtPwd;
    private EditText edtNickName;
    private EditText edtSex;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        edtSchool = (EditText)view.findViewById(R.id.rege_edt_school);
        edtEmail = (EditText)view.findViewById(R.id.rege_edt_email);
        edtPwd = (EditText)view.findViewById(R.id.rege_edt_pwd);
        edtNickName = (EditText)view.findViewById(R.id.rege_edt_nickname);
        edtSex = (EditText)view.findViewById(R.id.rege_edt_sex);
        btnSubmit = (Button)view.findViewById(R.id.rege_btn_register);
        btnSubmit.setOnClickListener(this);
        tvTipsTwo = (TextView)view.findViewById(R.id.rege_tips_two);
        tvTipsTwo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rege_btn_register) {

        } else if(v.getId() == R.id.rege_tips_two) {
            
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
}
