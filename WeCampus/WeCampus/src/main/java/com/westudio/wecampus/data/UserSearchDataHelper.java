package com.westudio.wecampus.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.westudio.wecampus.data.model.SearchHistory;
import com.westudio.wecampus.util.database.Column;
import com.westudio.wecampus.util.database.SQLiteTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martian on 13-12-13.
 */
public class UserSearchDataHelper extends BaseDataHelper {

    public UserSearchDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.SEARCH_USER_CONTENT_URI;
    }

    private ContentValues getContentValue(SearchHistory searchHistory) {
        ContentValues values = new ContentValues();
        values.put(SearchHistoryDBInfo.KEYWORDS, searchHistory.keywords);
        return values;
    }

    public List<SearchHistory> queryAll() {
        List<SearchHistory> result = new ArrayList<SearchHistory>();
        Cursor cursor = query(null, null, null, "BaseColumns._ID DESC");
        SearchHistory history = null;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            history = new SearchHistory();
            history.keywords = cursor.getColumnName(cursor.getColumnIndex(SearchHistoryDBInfo.KEYWORDS));
            result.add(history);
        }

        return result;
    }

    public void insert(String keywords) {
        SearchHistory history = new SearchHistory();
        history.keywords = keywords;
        ContentValues values = getContentValue(history);
        insert(values);
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DataProvider.DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int row = db.delete(SearchHistoryDBInfo.TABLE_NAME, "", new String[] {});
            return row;
        }
    }

    public static class SearchHistoryDBInfo {

        private SearchHistoryDBInfo() {};

        public static final String TABLE_NAME = "searchHistory";

        public static final String KEYWORDS = "keywords";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(KEYWORDS, Column.Constraint.UNIQUE, Column.DataType.INTEGER);
    }
}
