package com.westudio.wecampus.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.westudio.wecampus.R;

/**
 * Created by martian on 13-9-25.
 */
public class HeaderTabBar extends LinearLayout implements View.OnClickListener {
    private TextView mTab1;
    private TextView mTab2;
    private TextView mTab3;


    // Implement a default OnTabSelectedListener
    public OnTabSelectedListener mOnTabSelectedListener = new OnTabSelectedListener() {

        @Override
        public void onFirstTabSelected() {
        }

        @Override
        public void onSecondTabSelected() {
        }

        @Override
        public void onThirdTabSelected() {
        }
    };

    public interface OnTabSelectedListener {

        public void onFirstTabSelected();

        public void onSecondTabSelected();

        public void onThirdTabSelected();
    }

    public HeaderTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_tabbar, this);

        mTab1 = (TextView) findViewById(R.id.tab1);
        mTab2 = (TextView) findViewById(R.id.tab2);
        mTab3 = (TextView) findViewById(R.id.tab3);
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        mTab1.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        view.setSelected(true);
        switch (view.getId()) {
            case R.id.tab1: {
                mTab2.setSelected(false);
                mTab3.setSelected(false);
                mOnTabSelectedListener.onFirstTabSelected();
                break;
            }
            case R.id.tab2: {
                mTab1.setSelected(false);
                mTab3.setSelected(false);
                mOnTabSelectedListener.onSecondTabSelected();
                break;
            }
            case R.id.tab3: {
                mTab2.setSelected(false);
                mTab1.setSelected(false);
                mOnTabSelectedListener.onThirdTabSelected();
                break;
            }
            default: {
                break;
            }
        }
    }


    public void setmOnTabSelectedListener(OnTabSelectedListener mOnTabSelectedListener) {
        this.mOnTabSelectedListener = mOnTabSelectedListener;
    }

    public void setSelected(int position, boolean selected) {
        switch(position) {
            case 0:
                mTab1.setSelected(selected);
                mTab2.setSelected(!selected);
                mTab3.setSelected(!selected);
                break;
            case 1:
                mTab2.setSelected(selected);
                mTab1.setSelected(!selected);
                mTab3.setSelected(!selected);
                break;
            case 2:
                mTab3.setSelected(selected);
                mTab2.setSelected(!selected);
                mTab1.setSelected(!selected);
                break;
        }
    }

    /**
     * Set the texts on the three tabs
     */
    public void setTexts(int res1, int res2, int res3) {
        mTab1.setText(res1);
        mTab2.setText(res2);
        mTab3.setText(res3);
    }
}
