package com.westudio.wecampus.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.westudio.wecampus.data.model.Organization;
import com.westudio.wecampus.util.database.Column;
import com.westudio.wecampus.util.database.SQLiteTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martian on 13-11-23.
 */
public class OrgDataHelper extends BaseDataHelper {

    public OrgDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.ORGANIZATIONS_CONTENT_URI;
    }

    private ContentValues getContentValues(Organization org) {
        ContentValues values = new ContentValues();
        values.put(OrganiztionDBInfo.ID, org.id);
        values.put(OrganiztionDBInfo.ADMIN_NAME, org.admin_name);
        values.put(OrganiztionDBInfo.ADMIN_EMAIL, org.admin_email);
        values.put(OrganiztionDBInfo.AVATAR, org.avatar);
        values.put(OrganiztionDBInfo.COUNT_OF_FANS, org.count_of_fans);
        values.put(OrganiztionDBInfo.DESCRIPTION, org.description);
        values.put(OrganiztionDBInfo.NAME, org.name);
        values.put(OrganiztionDBInfo.CAN_FOLLOW, org.can_follow);
        return values;
    }

    public Organization query(int id) {
        Organization org = null;
        Cursor cursor = query(null, OrganiztionDBInfo.ID + "= ?", new String[] {
                String.valueOf(id)
        }, null);
        if (cursor.moveToFirst()) {
            org = Organization.fromCursor(cursor);
        }
        cursor.close();
        return org;
    }

    public void insert(Organization org) {
        ContentValues values = getContentValues(org);
        insert(values);
    }

    public void bulkInsert(List<Organization> orgs) {
        ArrayList<ContentValues> contentValues = new ArrayList<ContentValues>();
        for (Organization org : orgs) {
            ContentValues values = getContentValues(org);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    public int update(Organization org) {
        ContentValues values = getContentValues(org);
        return update(values, OrganiztionDBInfo.ID + "= ?", new String[] {
                String.valueOf(org.id)
        });
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DataProvider.DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int row = db.delete(OrganiztionDBInfo.TABLE_NAME, "", new String[] {});

            return row;
        }
    }

    public static class OrganiztionDBInfo implements BaseColumns {
        private OrganiztionDBInfo() {
        }

        public static final String TABLE_NAME = "organization";

        public static final String ID = "id";
        public static final String ADMIN_EMAIL = "admin_email";
        public static final String ADMIN_NAME = "admin_name";
        public static final String AVATAR = "avatar";
        public static final String COUNT_OF_FANS = "count_of_fans";
        public static final String DESCRIPTION = "description";
        public static final String NAME = "name";
        public static final String CAN_FOLLOW = "can_follow";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.Constraint.UNIQUE, Column.DataType.INTEGER)
                .addColumn(ADMIN_EMAIL, Column.DataType.TEXT)
                .addColumn(ADMIN_NAME, Column.DataType.TEXT)
                .addColumn(AVATAR, Column.DataType.TEXT)
                .addColumn(COUNT_OF_FANS, Column.DataType.INTEGER)
                .addColumn(DESCRIPTION, Column.DataType.TEXT)
                .addColumn(NAME, Column.DataType.TEXT)
                .addColumn(CAN_FOLLOW, Column.DataType.BOOLEAN);
    }
}
