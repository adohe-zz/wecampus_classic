package com.westudio.wecampus.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;

/**
 * Created by nankonami on 13-9-13.
 */
public class CardsAnimationAdapter extends AnimationAdapter {

    public CardsAnimationAdapter(Context context, BaseAdapter baseAdapter) {
        super(baseAdapter);
    }
    
    @Override
    protected long getAnimationDelayMillis() {
        return 0;
    }

    @Override
    protected long getAnimationDurationMillis() {
        return 0;
    }

    @Override
    public Animator[] getAnimators(ViewGroup parent, View view) {
        return new Animator[0];
    }
}
