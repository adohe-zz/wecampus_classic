package com.westudio.wecampus.ui.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.user.UserMoreProfileActivity;

/**
 * Created by martian on 14-3-3.
 */
public class UserMoreMenuActivity extends BaseMenuActivity implements View.OnClickListener {
    public static final String EXTRA_USER = "extra_user";

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_menu);
        setWindowStyle();

        mUser = (User) getIntent().getSerializableExtra(EXTRA_USER);

        findViewById(R.id.save_as_contact).setOnClickListener(this);
        findViewById(R.id.more_profile_info).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save_as_contact) {
            saveAsContact(mUser);
        } else if (view.getId() == R.id.more_profile_info) {
            Intent intent = new Intent(this, UserMoreProfileActivity.class);
            intent.putExtra(UserMoreProfileActivity.EXTRA_USER, mUser);
            startActivity(intent);
            finish();
        }

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
