package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.UserDataHelper;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.util.Utility;

/**
 * Created by nankonami on 13-9-18.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener,
        Response.ErrorListener, Response.Listener<User> {

    private Activity activity;

    private EditText edtEmail;
    private EditText edtPwd;
    private Button btnReg;
    private Button btnLogin;
    private TextView tvForgetPwd;
    private TextView tvPwd;
    private TextView tvEmail;
    private ProgressDialog progressDialog;
    private int toastStringId = R.string.login_error;

    public static LoginFragment newInstance(Bundle bundle) {
        LoginFragment loginFragment = new LoginFragment();

        if(bundle != null) {
            loginFragment.setArguments(bundle);
        }

        return loginFragment;
    }

    public LoginFragment() {
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
        tvPwd = (TextView)view.findViewById(R.id.login_tv_pwd);
        tvEmail = (TextView)view.findViewById(R.id.login_tv_email);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_register) {
            FragmentTransaction fragmentTransaction = ((AuthActivity)activity).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
            fragmentTransaction.replace(R.id.auth_container, RegisterFragment.newInstance(null), AuthActivity.REGISTER_FRAGMENT_TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if(v.getId() == R.id.btn_login) {
            String email = edtEmail.getText().toString();
            String pwd = edtPwd.getText().toString();
            if(checkValidation(email, pwd)) {
                handleLogin(email, pwd);
            } else {
                Toast.makeText(getActivity(), toastStringId, Toast.LENGTH_SHORT).show();
            }
        } else if(v.getId() == R.id.login_forget_pwd) {
            Intent intent = new Intent(getActivity(), ForgetPwdActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Check the input parameters validation
     * @param email
     * @param pwd
     * @return
     */
    private boolean checkValidation(String email, String pwd) {
        boolean result = true;

        if(TextUtils.isEmpty(email)) {
            result = false;
            toastStringId = R.string.msg_please_input_email;
        } else if (!email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            result = false;
            toastStringId = R.string.msg_error_email_format;
        } else if(TextUtils.isEmpty(pwd)) {
            result = false;
            toastStringId = R.string.msg_please_input_pwd;
        }

        return result;
    }

    private void handleLogin(final String email, final String pwd) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        WeCampusApi.postLogin(getActivity(), email, pwd, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(final User user) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        // Save account info to XML
        BaseApplication app = (BaseApplication)getActivity().getApplication();
        app.getAccountMgr().saveAccountInfo(user.id, user.token);
        app.hasAccount = true;

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
