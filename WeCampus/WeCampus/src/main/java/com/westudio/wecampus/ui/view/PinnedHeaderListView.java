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
    private OnTabSelectedListener mTabSelectedListener;

    private boolean isHeaderOffScreen;

    // The distance from top of the ListView to the top of the header
    private int mHeaderTabBarOffset;

    private HeaderTabBar mPinnedHeader;
    private HeaderTabBar mHeader;

    public interface OnHeaderOffScreenListener {

        /** Callback when the list header is move off screen */
        public void onHeaderOffScreen();

        /** Callback when the list header is move in screen */
        public void onHeaderInScreen();
    }

    public interface OnTabSelectedListener {
        public void onTabOneSelected();
        public void onTabTwoSelected();
        public void onTabThreeSelecd();
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


    public void setmTabSelectedListener(OnTabSelectedListener mTabSelectedListener) {
        this.mTabSelectedListener = mTabSelectedListener;
    }

    // Add the
    private void addHeaderView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.my_profile_header, this, false);
        this.addHeaderView(header, null , false);
        final LinearLayout headerFrame = (LinearLayout) header.findViewById(R.id.profile_header);
        mHeader = (HeaderTabBar) header.findViewById(R.id.header_tab_bar);

        headerFrame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderTabBarOffset = headerFrame.getHeight() - mHeader.getHeight();
            }
        });

        mHeader.setmOnTabSelectedListener(new HeaderTabBar.OnTabSelectedListener() {
            @Override
            public void onFirstTabSelected() {
                mPinnedHeader.setSelected(0, true);
                mTabSelectedListener.onTabOneSelected();
            }

            @Override
            public void onSecondTabSelected() {
                mPinnedHeader.setSelected(1, true);
                mTabSelectedListener.onTabTwoSelected();
            }

            @Override
            public void onThirdTabSelected() {
                mPinnedHeader.setSelected(2, true);
                mTabSelectedListener.onTabThreeSelecd();
            }
        });

    }

    public void setmPinnedHeader(HeaderTabBar mPinnedHeader) {
        this.mPinnedHeader = mPinnedHeader;
        mPinnedHeader.setmOnTabSelectedListener(new HeaderTabBar.OnTabSelectedListener() {
            @Override
            public void onFirstTabSelected() {
                mHeader.setSelected(0, true);
                mTabSelectedListener.onTabOneSelected();
            }

            @Override
            public void onSecondTabSelected() {
                mHeader.setSelected(1, true);
                mTabSelectedListener.onTabTwoSelected();
            }

            @Override
            public void onThirdTabSelected() {
                mHeader.setSelected(2, true);
                mTabSelectedListener.onTabThreeSelecd();
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

    public void setTabTexts(int res1, int res2, int res3) {
        if (mPinnedHeader != null) {
            mPinnedHeader.setTexts(res1, res2, res3);
        }
        mHeader.setTexts(res1, res2, res3);
    }
}
