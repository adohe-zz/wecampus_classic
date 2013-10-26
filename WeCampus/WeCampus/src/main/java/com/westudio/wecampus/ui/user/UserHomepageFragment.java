package com.westudio.wecampus.ui.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.westudio.wecampus.R;

import couk.jenxsol.parallaxscrollview.views.ParallaxScrollView;


/**
 * Created by Martian on 13-10-24.
 */
public class UserHomepageFragment extends Fragment {
    private ParallaxScrollView mScrollview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_homepage, container, false);

        mScrollview = (ParallaxScrollView) view.findViewById(R.id.scroll_view);
        mScrollview.setParallaxOffset(0.3f);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
