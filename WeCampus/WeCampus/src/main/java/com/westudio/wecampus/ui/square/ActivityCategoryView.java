package com.westudio.wecampus.ui.square;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityCategory;

/**
 * Created by martian on 13-11-27.
 */
public class ActivityCategoryView extends LinearLayout implements View.OnClickListener {
    private TextView tvSectionName;
    private View colorMark;

    private ActivityCategory category;

    public ActivityCategoryView(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.row_activity_category, this);

        tvSectionName = (TextView) findViewById(R.id.section_name);
        colorMark = findViewById(R.id.color_mark);
        setOnClickListener(this);
    }

    public ActivityCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.row_activity_category, this);

        tvSectionName = (TextView) findViewById(R.id.section_name);
        colorMark = findViewById(R.id.color_mark);
        setOnClickListener(this);
    }

    public void setCategory(ActivityCategory category) {
        this.category = category;
        tvSectionName.setText(category.name);

        colorMark.setBackgroundColor(Color.parseColor(category.color));

    }


    @Override
    public void onClick(View view) {

    }
}
