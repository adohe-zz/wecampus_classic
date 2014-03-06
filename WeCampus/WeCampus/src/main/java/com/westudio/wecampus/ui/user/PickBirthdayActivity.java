package com.westudio.wecampus.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nankonami on 14-2-13.
 */
public class PickBirthdayActivity extends SherlockFragmentActivity {

    private TextView tvAge;
    private String age;
    private DatePicker datePicker;

    private String birthday;
    private Date dateBirthday;
    private int year;
    private int month;
    private int day;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.update_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.update_profile) {
            if(isValidate()) {
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.putExtra(MyProfileActivity.AGE, tvAge.getText().toString());
                        intent.putExtra(MyProfileActivity.BIRTHDAY, birthday);
                        setResult(MyProfileActivity.PICK_AGE_RESULT, intent);
                        finish();
                    }
                };
                handler.postDelayed(runnable, 400);
            } else {
                Toast.makeText(this, R.string.input_validate_age, Toast.LENGTH_SHORT).show();
            }
        } else if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra(MyProfileActivity.AGE, age);
            setResult(MyProfileActivity.PICK_AGE_RESULT, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_birthday);

        age = getIntent().getStringExtra(MyProfileActivity.AGE);
        dateBirthday = (Date) getIntent().getSerializableExtra(MyProfileActivity.DATE_BIRTHDAY);
        initWidget();
        updateActionBar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void initWidget() {
        tvAge = (TextView)findViewById(R.id.age);
        tvAge.setText(age);
        datePicker = (DatePicker)findViewById(R.id.datepicker);

        int intAge = 18;
        Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(age)) {
            age = age.substring(0, age.length() - 1);
            intAge = Integer.parseInt(age);
            year = calendar.get(Calendar.YEAR) - intAge;
            calendar.setTime(dateBirthday);
        } else {
            year = calendar.get(Calendar.YEAR) - intAge;
        }
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                PickBirthdayActivity.this.year = year;
                updateDate(year, monthOfYear, dayOfMonth);
            }
        });
    }

    private void updateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.birthday_date);
    }

    private void updateDate(int year, int month, int day) {
        String num = getResources().getString(R.string.age_num);
        tvAge.setText(String.format(num, Calendar.getInstance().get(Calendar.YEAR) - year));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        birthday = sdf.format(calendar.getTime());
    }

    private boolean isValidate() {
        boolean result = true;

        if(year >= Calendar.getInstance().get(Calendar.YEAR)) {
            result = false;
        }
        if(TextUtils.isEmpty(tvAge.getText().toString())) {
            result = false;
        }
        return result;
    }
}
