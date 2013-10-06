package com.westudio.wecampus.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.westudio.wecampus.R;

/**
 * Created by jam on 13-9-23.
 */
public class PinnedHeaderListView extends ListView {

    private OnHeaderOffScreenListener mHeaderOffScreenListener;

    private boolean isHeaderOffScreen;

    // The distance from top of the ListView to the top of the header
    private int mHeaderTabBarOffset;

    public interface OnHeaderOffScreenListener {

        /** Callback when the list header is move off screen */
        public void onHeaderOffScreen();

        /** Callback when the list header is move in screen */
        public void onHeaderInScreen();
    }

    public PinnedHeaderListView(Context context) {
        super(context);
        this.setOnScrollListener(mOnScrollListener);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(mOnScrollListener);
        addHeaderView();
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(mOnScrollListener);
        addHeaderView();
    }

    public void setHeaderOffScreenListener(OnHeaderOffScreenListener headerOffScreenListener) {
        this.mHeaderOffScreenListener = headerOffScreenListener;
    }

    // Add the
    private void addHeaderView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.my_profile_header, this, false);
        this.addHeaderView(header, null , false);
        final LinearLayout headerFrame = (LinearLayout) header.findViewById(R.id.profile_header);
        final HeaderTabBar tabBar = (HeaderTabBar) header.findViewById(R.id.header_tab_bar);

        headerFrame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderTabBarOffset = headerFrame.getHeight() - tabBar.getHeight();
            }
        });

    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0) {
                View child0 = getChildAt(0);
                Rect rectTemp = new Rect();
                if (child0 != null) {
                    child0.getDrawingRect(rectTemp);
                    if (child0.getTop() <= (mHeaderTabBarOffset * -1) && !isHeaderOffScreen) {
                        isHeaderOffScreen = true;
                        mHeaderOffScreenListener.onHeaderOffScreen();
                    } else if (child0.getTop() >= (mHeaderTabBarOffset * -1) && isHeaderOffScreen) {
                        isHeaderOffScreen = false;
                        mHeaderOffScreenListener.onHeaderInScreen();
                    }
                }
            }
        }
    };
}
