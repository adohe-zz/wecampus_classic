package com.westudio.wecampus.ui.square;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityCategory;
import com.westudio.wecampus.ui.activity.ActivityListActivity;

/**
 * Created by martian on 13-11-27.
 */
public class ActivityCategoryView extends LinearLayout implements View.OnClickListener {
    private TextView tvSectionName;
    private View colorMark;

    private ActivityCategory category;

    public ActivityCategoryView(Context context) {
        this(context, null);
    }

    public ActivityCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.row_activity_category, this);

        tvSectionName = (TextView) findViewById(R.id.section_name);
        colorMark = findViewById(R.id.color_mark);
        ViewGroup section = (ViewGroup) findViewById(R.id.section);
        section.setOnClickListener(this);
    }

    public void setCategory(ActivityCategory category) {
        this.category = category;
        tvSectionName.setText(category.name);

        colorMark.setBackgroundColor(Color.parseColor(category.color));

    }

    public ActivityCategory getCategory() {
        return category;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ActivityListActivity.class);
        intent.putExtra(ActivityListActivity.EXTRA_CATEGORY, category.name);
        getContext().startActivity(intent);

        MobclickAgent.onEvent(getContext(), "explore_categories");
    }
}
