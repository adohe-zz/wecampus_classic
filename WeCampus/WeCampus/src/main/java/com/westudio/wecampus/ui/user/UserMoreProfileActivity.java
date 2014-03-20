package com.westudio.wecampus.ui.user;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.base.BaseGestureActivity;

/**
 * Created by martian on 14-3-3.
 */
public class UserMoreProfileActivity extends BaseGestureActivity {
    public static final String EXTRA_USER = "extra_user";

    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_more_profile);
        setTitle(R.string.more_profile_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUser = (User) getIntent().getSerializableExtra(EXTRA_USER);

        setUpUI();

        registerSwipeToCloseListener(findViewById(R.id.content_frame));
    }

    private void setUpUI() {
        TextView nickname = (TextView) findViewById(R.id.nickname);
        nickname.setText(mUser.nickname);

        TextView gender = (TextView) findViewById(R.id.gender);
        gender.setText(mUser.gender);

        TextView school = (TextView) findViewById(R.id.school);
        school.setText(mUser.school.getName());

        TextView words = (TextView) findViewById(R.id.words);
        if (!TextUtils.isEmpty(mUser.words)) {
            words.setText(mUser.words);
        }

        TextView realname = (TextView) findViewById(R.id.really_name);
        if (!TextUtils.isEmpty(mUser.name)) {
            realname.setText(mUser.name);
        }

        TextView telephone = (TextView) findViewById(R.id.telephone);
        if (!TextUtils.isEmpty(mUser.phone)) {
            telephone.setText(mUser.phone);
        }

        TextView email = (TextView) findViewById(R.id.email);
        if (!TextUtils.isEmpty(mUser.contact_email)) {
            email.setText(mUser.email);
        } else {
            email.setText(mUser.contact_email);
        }

        TextView birthday = (TextView) findViewById(R.id.birthday);
        if (!TextUtils.isEmpty(mUser.birthday)) {
            birthday.setText(mUser.birthday);
        }

        TextView relationship = (TextView) findViewById(R.id.relationship);
        if (!TextUtils.isEmpty(mUser.emotion)) {
            relationship.setText(mUser.emotion);
        }

        TextView role = (TextView) findViewById(R.id.role);
        if (!TextUtils.isEmpty(mUser.stage)) {
            role.setText(mUser.stage);
        }

        findViewById(R.id.save_as_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAsContact(mUser);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveAsContact(User user) {
        ContentValues values = new ContentValues();
        // 向RawContacts.CONTENT_URI执行一个空值插入，获取系统返回的rawContactId
        Uri rawContactUri = getContentResolver().insert(
                ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);

        // 往data表入姓名数据
        values.clear();
        values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, (user.nickname + user.name));
        getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);

        // Email
        values.clear();
        values.put(
                android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID,
                rawContactId);
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Email.DATA, user.email);
        values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);

        // Phone
        values.clear();
        values.put(
                android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID,
                rawContactId);
        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, user.phone);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE);
        getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI,
                values);

        Toast.makeText(this, R.string.save_contact_complete, Toast.LENGTH_SHORT).show();
        finish();
    }
}
