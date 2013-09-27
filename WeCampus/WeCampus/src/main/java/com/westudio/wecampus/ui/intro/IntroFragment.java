package com.westudio.wecampus.ui.intro;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.viewpagerindicator.UnderlinePageIndicator;
import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nankonami on 13-9-18.
 */
public class IntroFragment extends BaseFragment {

    private static final int[] BG_IMAGE_IDS = {
            R.drawable.intro_pager_01, R.drawable.intro_pager_02, R.drawable.intro_pager_03
    };

    private View view;
    private ViewPager viewPager;
    private UnderlinePageIndicator pageIndicator;

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

        for(int i = 0; i < BG_IMAGE_IDS.length; i++) {
            ImageView imageView = new ImageView(activity);
            imageView = (ImageView)inflater.inflate(R.layout.page_intro, null);
            Drawable drawable = getResources().getDrawable(BG_IMAGE_IDS[i]);
            imageView.setBackgroundResource(BG_IMAGE_IDS[i]);
            viewList.add(imageView);
        }

        viewPager = (ViewPager)view.findViewById(R.id.intro_viewpager);
        IntroImageAdapter adapter = new IntroImageAdapter(viewList);
        viewPager.setAdapter(adapter);
        pageIndicator = (UnderlinePageIndicator)view.findViewById(R.id.intro_viewpager_indicator);
        pageIndicator.setViewPager(viewPager, 0);
        pageIndicator.setFades(false);

        return view;
    }
}
