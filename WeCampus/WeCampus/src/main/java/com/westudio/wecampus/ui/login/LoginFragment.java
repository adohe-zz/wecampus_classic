package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseFragment;

/**
 * Created by nankonami on 13-9-18.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private Activity activity;

    private EditText edtEmail;
    private EditText edtPwd;
    private Button btnReg;
    private Button btnLogin;
    private TextView tvForgetPwd;

    public static LoginFragment newInstance(Bundle bundle) {
        LoginFragment loginFragment = new LoginFragment();
        if(bundle != null) {
            loginFragment.setArguments(bundle);
        }

        return loginFragment;
    }

    private LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        edtEmail = (EditText)view.findViewById(R.id.login_edt_email);
        edtPwd = (EditText)view.findViewById(R.id.login_edt_pwd);
        btnReg = (Button)view.findViewById(R.id.btn_register);
        btnReg.setOnClickListener(this);
        btnLogin = (Button)view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        tvForgetPwd = (TextView)view.findViewById(R.id.login_forget_pwd);
        tvForgetPwd.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_register) {

        } else if(v.getId() == R.id.btn_login) {
            String email = edtEmail.getText().toString();
            String pwd = edtPwd.getText().toString();
            if(checkValidation(email, pwd)) {

            }
        } else if(v.getId() == R.id.login_forget_pwd) {

        }
    }

    private boolean checkValidation(String email, String pwd) {
        boolean result = true;

        if(TextUtils.isEmpty(email)) {
            result = false;
        } else if(TextUtils.isEmpty(pwd)) {
            result = false;
        }

        return result;
    }
}
