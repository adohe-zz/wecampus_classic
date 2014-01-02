package com.westudio.wecampus.ui.organiztion;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.westudio.wecampus.R;
import com.westudio.wecampus.util.Utility;

/**
 * Created by martian on 13-11-22.
 */
public class IntroAdapter extends BaseAdapter {

    private Context mContext;
    private String mName;
    private String mIntroduction;
    private String mAdminUrl;

    public IntroAdapter(Context context) {
        mContext = context;
    }

    public void setData(String adminName, String intro, String adminUrl) {
        this.mName = adminName;
        this.mIntroduction = intro;
        this.mAdminUrl = adminUrl;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View mView = view;
        if(mView == null) {
            mView = inflater.inflate(R.layout.row_organization_brief, viewGroup, false);
        }

        TextView name = (TextView)mView.findViewById(R.id.org_name);
        TextView intro = (TextView)mView.findViewById(R.id.org_intro);
        Button btn = (Button)mView.findViewById(R.id.btn_contact_with_mail);

        name.setText(mName);
        intro.setText(mIntroduction);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdminUrl != null) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("mailto:"));
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mAdminUrl});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "WeCampus Feedback");
                    try {
                        mContext.startActivity(Intent.createChooser(intent, "please choose..."));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.no_email_client), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, R.string.org_no_email, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return mView;
    }
}
