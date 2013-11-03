package com.westudio.wecampus.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.westudio.wecampus.data.model.Activity;
import com.westudio.wecampus.util.Utility;
import com.westudio.wecampus.util.database.Column;
import com.westudio.wecampus.util.database.SQLiteTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martian on 13-9-13.
 */
public class ActivityDataHelper extends BaseDataHelper{

    public ActivityDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.ACTIVITIES_CONTENT_URI;
    }

    private ContentValues getContentValues(Activity activity) {
        ContentValues values = new ContentValues();
        values.put(ActivityDBInfo.ID, activity.getId());
        values.put(ActivityDBInfo.BEGIN, activity.getBegin());
        values.put(ActivityDBInfo.END, activity.getEnd());
        values.put(ActivityDBInfo.TITLE, activity.getTitle());
        values.put(ActivityDBInfo.LOCATION, activity.getLocation());
        values.put(ActivityDBInfo.ORGANIZATION_ID, activity.getOrganization_id());
        values.put(ActivityDBInfo.CATEGORY, activity.getCategory());
        values.put(ActivityDBInfo.SPONSOR_NAME, activity.getSponsor_name());
        values.put(ActivityDBInfo.SPONSOR_URL, activity.getSponsor_url());
        values.put(ActivityDBInfo.IMAGE, activity.getImage());
        values.put(ActivityDBInfo.TICKET_SERVICE, activity.getTicket_service());
        values.put(ActivityDBInfo.DESCRIPTION, activity.getDescription());
        values.put(ActivityDBInfo.COUNT_OF_FANS, activity.getCount_of_fans());
        values.put(ActivityDBInfo.PROVIDE_TICKET, activity.isProvide_ticket());
        values.put(ActivityDBInfo.HAVE_SPONSOR, activity.isHave_sponsor());

        return values;
    }

    public Activity query(int id) {
        Activity activity = null;
        Cursor cursor = query(null, ActivityDBInfo.ID + "= ?", new String[] {
                String.valueOf(id)
        }, null);
        if (cursor.moveToFirst()) {
            activity = Activity.fromCursor(cursor);
        }
        cursor.close();
        return activity;
    }

    public void bulkInsert(List<Activity> activities) {
        ArrayList<ContentValues> contentValues = new ArrayList<ContentValues>();
        for (Activity activity : activities) {
            ContentValues values = getContentValues(activity);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DataProvider.DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int row = db.delete(ActivityDBInfo.TABLE_NAME, "", new String[] {});

            return row;
        }
    }

    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, new String[]{},
                ActivityDBInfo.ID + " DESC");
    }

    public static class ActivityDBInfo implements BaseColumns {
        private ActivityDBInfo() {
        }

        public static final String TABLE_NAME = "activity";

        public static final String ID = "id";
        public static final String BEGIN = "begin";
        public static final String END = "end";
        public static final String TITLE = "title";
        public static final String LOCATION = "location";
        public static final String ORGANIZATION_ID = "organization_id";
        public static final String CATEGORY = "category";
        public static final String SPONSOR_NAME = "sponsor_name";
        public static final String SPONSOR_URL = "sponsor_url";
        public static final String IMAGE = "image";
        public static final String TICKET_SERVICE = "ticket_service";
        public static final String DESCRIPTION = "description";
        public static final String COUNT_OF_FANS = "count_of_fans";
        public static final String PROVIDE_TICKET = "provide_ticket";
        public static final String HAVE_SPONSOR = "have_sponsor";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(BEGIN, Column.DataType.TIMESTAMP)
                .addColumn(END, Column.DataType.TIMESTAMP)
                .addColumn(TITLE, Column.DataType.TEXT)
                .addColumn(LOCATION, Column.DataType.TEXT)
                .addColumn(ORGANIZATION_ID, Column.DataType.INTEGER)
                .addColumn(CATEGORY, Column.DataType.TEXT)
                .addColumn(SPONSOR_NAME, Column.DataType.TEXT)
                .addColumn(SPONSOR_URL, Column.DataType.TEXT)
                .addColumn(IMAGE, Column.DataType.TEXT)
                .addColumn(TICKET_SERVICE, Column.DataType.TEXT)
                .addColumn(DESCRIPTION, Column.DataType.TEXT)
                .addColumn(COUNT_OF_FANS, Column.DataType.INTEGER)
                .addColumn(PROVIDE_TICKET, Column.DataType.BOOLEAN)
                .addColumn(HAVE_SPONSOR, Column.DataType.BOOLEAN);
    }
}
