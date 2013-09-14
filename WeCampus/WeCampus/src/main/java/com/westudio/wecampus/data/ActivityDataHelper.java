package com.westudio.wecampus.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.CursorLoader;

import com.westudio.wecampus.modle.Activity;
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
        values.put(ActivityDBInfo.CHANNEL_ID, activity.getChannel_Id());
        values.put(ActivityDBInfo.ORGANIZER, activity.getOrganizer());
        values.put(ActivityDBInfo.ORGANIZER_AVATAR, activity.getOrganizerAvatar());
        values.put(ActivityDBInfo.STATUS, activity.getStatus());
        values.put(ActivityDBInfo.IMAGE, activity.getImage());
        values.put(ActivityDBInfo.CRTEATE_AT, activity.getCreatedAt());
        values.put(ActivityDBInfo.DESCRIPTION, activity.getDescription());
        values.put(ActivityDBInfo.LIKE, activity.getLike());
        values.put(ActivityDBInfo.CAN_LIKE, activity.isCanLike());
        values.put(ActivityDBInfo.SCHEDULE, activity.getSchedule());
        values.put(ActivityDBInfo.CAN_SCHEDULE, activity.isCanSchedule());
        values.put(ActivityDBInfo.FRIEND_COUNT, activity.getFriendsCount());
        values.put(ActivityDBInfo.ACCOUNT_ID, activity.getAccountId());

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
        public static final String CHANNEL_ID = "channel_id";
        public static final String ORGANIZER = "organizer";
        public static final String ORGANIZER_AVATAR = "organizer_avatar";
        public static final String STATUS = "status";
        public static final String IMAGE = "image";
        public static final String CRTEATE_AT = "create_at";
        public static final String DESCRIPTION = "description";
        public static final String LIKE = "like";
        public static final String CAN_LIKE = "canlike";
        public static final String SCHEDULE = "schedule";
        public static final String CAN_SCHEDULE = "canschedule";
        public static final String FRIEND_COUNT = "firend_count";
        public static final String ACCOUNT_ID = "account_id";
        public static final String ACCOUNT_DETAIL = "account_detail";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(BEGIN, Column.DataType.TIMESTAMP)
                .addColumn(END, Column.DataType.TIMESTAMP)
                .addColumn(TITLE, Column.DataType.TEXT)
                .addColumn(LOCATION, Column.DataType.TEXT)
                .addColumn(CHANNEL_ID, Column.DataType.INTEGER)
                .addColumn(ORGANIZER, Column.DataType.TEXT)
                .addColumn(ORGANIZER_AVATAR, Column.DataType.TEXT)
                .addColumn(STATUS, Column.DataType.TEXT)
                .addColumn(IMAGE, Column.DataType.TEXT)
                .addColumn(CRTEATE_AT, Column.DataType.TIMESTAMP)
                .addColumn(DESCRIPTION, Column.DataType.TEXT)
                .addColumn(LIKE, Column.DataType.INTEGER)
                .addColumn(CAN_LIKE, Column.DataType.BOOLEAN)
                .addColumn(SCHEDULE, Column.DataType.INTEGER)
                .addColumn(CAN_SCHEDULE, Column.DataType.BOOLEAN)
                .addColumn(FRIEND_COUNT, Column.DataType.INTEGER)
                .addColumn(ACCOUNT_ID, Column.DataType.INTEGER);
    }
}
