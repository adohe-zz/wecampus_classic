package com.westudio.wecampus.ui.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.ImageDetailActivity;
import com.westudio.wecampus.util.ImageUtil;
import com.westudio.wecampus.util.Utility;

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

    private ImageView mAvatar;
    private TextView mName;
    private TextView mLike;

    private String avatarUrl;
    private String name;

    private Context mContext;

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
        mContext = context;
        this.setOnScrollListener(mOnScrollListener);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        this.setOnScrollListener(mOnScrollListener);
        addHeaderView();
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
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

        //隐藏一些不需要的widget
        header.findViewById(R.id.edit_button).setVisibility(GONE);
        header.findViewById(R.id.text_user_words).setVisibility(GONE);
        mLike = (TextView)header.findViewById(R.id.user_like);
        mLike.setVisibility(VISIBLE);
        mAvatar = (ImageView) header.findViewById(R.id.img_avatar);
        mAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putExtra(ImageDetailActivity.KEY_IMAGE_URL, avatarUrl);
                intent.putExtra(ImageDetailActivity.KEY_EXTRA_INFO, name);
                mContext.startActivity(intent);
            }
        });
        mName = (TextView) header.findViewById(R.id.text_user_name);

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

    public void setName(String name) {
        this.name = name;
        mName.setText(name);
    }

    public void setAvatar(String url) {
        avatarUrl = url;
        WeCampusApi.requestImage(url, new ImageLoader.ImageListener() {
            Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.detail_organization);
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                Bitmap data = imageContainer.getBitmap();
                if (data != null) {
                    mAvatar.setImageBitmap(data);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mAvatar.setImageBitmap(defaultBitmap);
            }
        });
    }
}
