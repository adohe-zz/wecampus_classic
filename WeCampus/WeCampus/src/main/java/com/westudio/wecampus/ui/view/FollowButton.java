package com.westudio.wecampus.ui.view;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.util.Utility;


/**
 * Created by martian on 13-10-26.
 */
public class FollowButton extends Button {

    private FollowState mFollowState;

    private OnStateChangeListener mOnStateChangeListener;

    public enum FollowState {
        FOLLOWING, // 已关注
        FOLLOW_EACH_OTHER, // 相互关注
        UNFOLLOWED, // 未关注
        BE_FOLLOWED // 被关注
    }

    public interface OnStateChangeListener {
        public void onFollowListener();

        public void onUnFollowListener();

    }

    public FollowButton(Context context) {
        super(context);
        setFollowState(FollowState.UNFOLLOWED);
        setOnClickListener(mOnClickListener);
    }

    public FollowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFollowState(FollowState.UNFOLLOWED);
        setOnClickListener(mOnClickListener);
    }

    public FollowButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFollowState(FollowState.UNFOLLOWED);
        setOnClickListener(mOnClickListener);
    }

    public void setFollowState(FollowState state) {
        Drawable drawable = null;
        switch (state) {
            case FOLLOWING: {
                setBackgroundResource(R.drawable.selector_followed);
                setText(R.string.followed);
                drawable = getContext().getResources().getDrawable(R.drawable.icon_follow_eachother);
                break;
            }
            case FOLLOW_EACH_OTHER: {
                setBackgroundResource(R.drawable.selector_followed);
                setText(R.string.follow_earch_other);
                drawable = getContext().getResources().getDrawable(R.drawable.icon_follow_eachother);
                break;
            }
            case UNFOLLOWED: {
                setBackgroundResource(R.drawable.selector_unfollowed);
                setText(R.string.follow);
                drawable = getContext().getResources().getDrawable(R.drawable.icon_follow_eachother);
                break;
            }
            case BE_FOLLOWED: {
                setBackgroundResource(R.drawable.selector_unfollowed);
                setText(R.string.follow);
                drawable = getContext().getResources().getDrawable(R.drawable.icon_follow_eachother);
                break;
            }
        }
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        setCompoundDrawablePadding(10);
        mFollowState = state;
    }

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        mOnStateChangeListener = listener;
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            if(BaseApplication.getInstance().hasAccount) {
                handleFollowState();
            } else {
                Toast.makeText(BaseApplication.getContext(), getResources().getString(R.string.please_login),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void handleFollowState() {
        switch (mFollowState) {
            case FOLLOWING: {
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onUnFollowListener();
                }
                break;
            }
            case FOLLOW_EACH_OTHER: {
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onUnFollowListener();
                }
                break;
            }
            case UNFOLLOWED: {
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onFollowListener();
                }
                break;
            }
            case BE_FOLLOWED: {
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onFollowListener();
                }
                break;
            }
        }
    }

}
