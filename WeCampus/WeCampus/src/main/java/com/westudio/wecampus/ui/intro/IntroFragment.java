package com.westudio.wecampus.ui.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.UnderlinePageIndicator;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.login.AuthActivity;
import com.westudio.wecampus.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nankonami on 13-9-18.
 */
public class IntroFragment extends BaseFragment implements View.OnClickListener{

    private static final int[] BG_IMAGE_IDS = {
            R.drawable.intro_pager_01, R.drawable.intro_pager_02, R.drawable.intro_pager_03
    };

    private View view;
    private ViewPager viewPager;
    private UnderlinePageIndicator pageIndicator;
    private Button btnNotNow;
    private Button btLogin;

    private Activity activity;

    public static IntroFragment newInstance(Bundle bundle) {
        IntroFragment fragment = new IntroFragment();
        if(bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    public IntroFragment() {

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
        view = inflater.inflate(R.layout.fragment_intro, container, false);
        List<View> viewList = new ArrayList<View>();
        String[] words = getResources().getStringArray(R.array.intro_words);

        for(int i = 0; i < BG_IMAGE_IDS.length; i++) {
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.page_intro, null);
            ImageView imageView = (ImageView) linearLayout.findViewById(R.id.picture);
            imageView.setImageResource(BG_IMAGE_IDS[i]);
            TextView tvIntro = (TextView) linearLayout.findViewById(R.id.words);
            tvIntro.setText(words[i]);
            viewList.add(linearLayout);
        }

        viewPager = (ViewPager)view.findViewById(R.id.intro_viewpager);
        IntroImageAdapter adapter = new IntroImageAdapter(viewList);
        viewPager.setAdapter(adapter);
        pageIndicator = (UnderlinePageIndicator)view.findViewById(R.id.intro_viewpager_indicator);
        pageIndicator.setViewPager(viewPager, 0);
        pageIndicator.setFades(false);

        btnNotNow = (Button) view.findViewById(R.id.intro_no_login);
        btnNotNow.setOnClickListener(this);
        btLogin = (Button) view.findViewById(R.id.intro_login_sign);
        btLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.intro_no_login: {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                break;
            }
            case R.id.intro_login_sign : {
                startActivity(new Intent(getActivity(), AuthActivity.class));
                getActivity().finish();
                break;
            }
        }
    }
}