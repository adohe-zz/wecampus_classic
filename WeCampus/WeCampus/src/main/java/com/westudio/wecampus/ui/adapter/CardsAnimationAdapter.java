package com.westudio.wecampus.ui.adapter;

import android.R;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;

/**
 * Created by nankonami on 13-9-13.
 */
public class CardsAnimationAdapter extends AnimationAdapter {

    private final int mTranslationY = 150;
    private final int mRotationX = 8;

    private long mDuration;

    public CardsAnimationAdapter(Context context, BaseAdapter baseAdapter) {
        super(baseAdapter);
        mDuration = context.getResources().getInteger(R.integer.config_mediumAnimTime);
    }

    @Override
    protected void prepareAnimation(View view) {
        view.setTranslationY(mTranslationY);
        view.setRotationX(mRotationX);
    }

    @Override
    protected long getAnimationDelayMillis() {
        return 30;
    }

    @Override
    protected long getAnimationDurationMillis() {
        return mDuration;
    }

    @Override
    public Animator[] getAnimators(ViewGroup parent, View view) {
        return new Animator[] {
                ObjectAnimator.ofFloat(view, "translationY", mTranslationY, 0),
                ObjectAnimator.ofFloat(view, "rotationX", mRotationX, 0)
        };
    }


}
