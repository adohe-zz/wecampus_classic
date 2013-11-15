package com.westudio.wecampus.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.main.MainActivity;
import com.westudio.wecampus.util.ImageUtil;

/**
 * Created by nankonami on 13-9-20.
 */
public class UpdateProfileFragment extends BaseFragment implements View.OnClickListener, Response.Listener<Void>, Response.ErrorListener {

    private Activity activity;

    private TextView tvSkip;
    private ImageView ivAvatar;
    private ProgressDialog progressDialog;
    private String mStrImgLocalPath;

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
        ivAvatar = (ImageView) view.findViewById(R.id.rege_step_two_avatar);
        ivAvatar.setOnClickListener(this);

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
            case R.id.rege_step_two_avatar: {
                ((AuthActivity)getActivity()).doPickPhotoAction();
                break;
            }
        }
    }

    public void setCropedAvatarImage(Bundle bundle) {
        Bitmap bmAvatar = bundle.getParcelable("cropedImage");
        mStrImgLocalPath = bundle.getString("imagePath");
        ivAvatar.setImageBitmap(ImageUtil.getRoundedCornerBitmap(bmAvatar));

        // fire an upload image request
        WeCampusApi.postUpdateAvatar(getActivity(), mStrImgLocalPath, this, this);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), R.string.upload_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Void aVoid) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), R.string.upload_success, Toast.LENGTH_SHORT).show();
    }
}
