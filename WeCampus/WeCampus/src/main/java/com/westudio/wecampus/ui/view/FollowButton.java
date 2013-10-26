package com.westudio.wecampus.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.westudio.wecampus.R;


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
        switch (state) {
            case FOLLOWING: {
                setBackgroundResource(R.drawable.selector_followed);
                setText(R.string.followed);
                break;
            }
            case FOLLOW_EACH_OTHER: {
                setBackgroundResource(R.drawable.selector_followed);
                setText(R.string.follow_earch_other);
                break;
            }
            case UNFOLLOWED: {
                setBackgroundResource(R.drawable.selector_unfollowed);
                setText(R.string.follow);
                break;
            }
            case BE_FOLLOWED: {
                setBackgroundResource(R.drawable.selector_unfollowed);
                setText(R.string.follow);
                break;
            }
        }
        mFollowState = state;
    }

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        mOnStateChangeListener = listener;
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            handleFollowState();
        }
    };

    private void handleFollowState() {
        switch (mFollowState) {
            case FOLLOWING: {
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onUnFollowListener();
                }
                setFollowState(FollowState.UNFOLLOWED);
                break;
            }
            case FOLLOW_EACH_OTHER: {
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onUnFollowListener();
                }
                setFollowState(FollowState.UNFOLLOWED);
                break;
            }
            case UNFOLLOWED: {
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onFollowListener();
                }
                setFollowState(FollowState.FOLLOWING);

                break;
            }
            case BE_FOLLOWED: {
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onFollowListener();
                }
                setFollowState(FollowState.FOLLOW_EACH_OTHER);
                break;
            }
        }
    }

}
