package com.westudio.wecampus.ui.intro;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by nankonami on 13-9-18.
 */
public class IntroImageAdapter extends PagerAdapter {

    private List<View> viewList;

    public IntroImageAdapter(List<View> viewList) {
        super();
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }


}
