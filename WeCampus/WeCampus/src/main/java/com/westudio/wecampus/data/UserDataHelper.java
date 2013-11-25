package com.westudio.wecampus.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.util.database.Column;
import com.westudio.wecampus.util.database.SQLiteTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martian on 13-11-24.
 */
public class UserDataHelper extends BaseDataHelper {

    public UserDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.USERS_CONTENT_URI;
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDBInfo.ID, user.id);
        values.put(UserDBInfo.AVATAR, user.avatar);
        values.put(UserDBInfo.BIRTHDAY, user.birthday);
        values.put(UserDBInfo.CONT_OF_FANS, user.count_of_fans);
        values.put(UserDBInfo.CONT_OF_FOLLOW_ACTIVITIES, user.count_of_follow_activities);
        values.put(UserDBInfo.CONT_OF_FOLLOW_ORGANIZATIONS, user.count_of_follow_organizations);
        values.put(UserDBInfo.CONT_OF_FOLLOWER, user.count_of_follower);
        values.put(UserDBInfo.CONTANT_EMAIL, user.contact_email);
        values.put(UserDBInfo.EMAIL, user.email);
        values.put(UserDBInfo.EMOTION, user.emotion);
        values.put(UserDBInfo.GENDER, user.gender);
        values.put(UserDBInfo.NAME, user.name);
        values.put(UserDBInfo.NICKNAME, user.nickname);
        values.put(UserDBInfo.PHONE, user.phone);
        values.put(UserDBInfo.SCHOOL_ID, user.school_id);
        values.put(UserDBInfo.STAGE, user.stage);
        values.put(UserDBInfo.WORDS, user.words);
        return values;
    }

    public User query(int id) {
        User user = null;
        Cursor cursor = query(null, UserDBInfo.ID + "= ?", new String[] {
                String.valueOf(id)
        }, null);
        if (cursor.moveToFirst()) {
            user = User.fromCursor(cursor);
        }
        cursor.close();
        return user;
    }

    public void insert(User user) {
        ContentValues values = getContentValues(user);
        insert(values);
    }

    public void bulkInsert(List<User> userList) {
        ArrayList<ContentValues> contentValues = new ArrayList<ContentValues>();
        for (User user : userList) {
            ContentValues values = getContentValues(user);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    public int update(User user) {
        ContentValues values = getContentValues(user);
        return update(values, UserDBInfo.ID + "= ?", new String[] {
                String.valueOf(user.id)
        });
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DataProvider.DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int row = db.delete(UserDBInfo.TABLE_NAME, "", new String[] {});
            return row;
        }
    }

    public static class UserDBInfo implements BaseColumns {
        private UserDBInfo() {
        }

        public static final String TABLE_NAME = "user";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String BIRTHDAY = "birthday";
        public static final String AVATAR = "avatar";
        public static final String CONTANT_EMAIL = "contant_email";
        public static final String CONT_OF_FANS = "cont_of_fans";
        public static final String CONT_OF_FOLLOWER = "cont_of_follower";
        public static final String CONT_OF_FOLLOW_ORGANIZATIONS = "cont_of_follow_organizations";
        public static final String CONT_OF_FOLLOW_ACTIVITIES = "count_of_follow_activities";
        public static final String EMAIL = "email";
        public static final String EMOTION = "emotion";
        public static final String NICKNAME = "nickname";
        public static final String SCHOOL_ID = "school_id";
        public static final String GENDER = "gender";
        public static final String PHONE = "phone";
        public static final String STAGE = "stage";
        public static final String WORDS = "words";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(NAME, Column.DataType.TEXT)
                .addColumn(BIRTHDAY, Column.DataType.TEXT)
                .addColumn(AVATAR, Column.DataType.TEXT)
                .addColumn(CONTANT_EMAIL, Column.DataType.TEXT)
                .addColumn(CONT_OF_FANS, Column.DataType.INTEGER)
                .addColumn(CONT_OF_FOLLOWER, Column.DataType.INTEGER)
                .addColumn(CONT_OF_FOLLOW_ORGANIZATIONS, Column.DataType.INTEGER)
                .addColumn(CONT_OF_FOLLOW_ACTIVITIES, Column.DataType.INTEGER)
                .addColumn(EMAIL, Column.DataType.TEXT)
                .addColumn(EMOTION, Column.DataType.TEXT)
                .addColumn(NICKNAME, Column.DataType.TEXT)
                .addColumn(SCHOOL_ID, Column.DataType.INTEGER)
                .addColumn(GENDER, Column.DataType.TEXT)
                .addColumn(PHONE, Column.DataType.TEXT)
                .addColumn(STAGE, Column.DataType.TEXT)
                .addColumn(WORDS, Column.DataType.TEXT);
    }
}