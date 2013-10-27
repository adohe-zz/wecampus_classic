package com.westudio.wecampus.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.School;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.Utility;

import java.util.List;

/**
 * Created by nankonami on 13-10-19.
 */
public class PickSchoolActivity extends SherlockFragmentActivity
        implements Response.ErrorListener, Response.Listener<List<School>>{

    private GridView gridView;
    private PickSchoolAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_school);

        setupActionBar();
        initWidget();
        requestSchool();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Set the action bar style
     */
    private void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Request the school list
     */
    private void requestSchool() {
        WeCampusApi.getSchoolList(this, 1, this, this);
    }

    /**
     * Init the view
     */
    private void initWidget() {
        gridView = (GridView)findViewById(R.id.school_table);
        adapter = new PickSchoolAdapter(this);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            this.setResult(1, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Utility.log("error", "error");
    }

    @Override
    public void onResponse(List<School> schools) {
        Utility.log("schools size: ", schools.size());
        Utility.log("schools name", schools.get(0).getName());
        //adapter.setSchoolList(schools);
    }
}
