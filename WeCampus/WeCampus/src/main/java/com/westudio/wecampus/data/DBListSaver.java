package com.westudio.wecampus.data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.j256.ormlite.dao.Dao;
import com.westudio.wecampus.ui.base.BaseApplication;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by nankonami on 13-9-7.
 */
public class DBListSaver<T, ID> extends AsyncTaskLoader<Void> {

    private Dao<T, ID> mDao = null;
    private DBHelper dbHelper;
    private List<T> mData = null;
    private Class<T> mClass;

    public DBListSaver(Context context, Class<T> classType, List<T> data) {
        super(context);
        this.mClass = classType;
        this.mData = data;
        dbHelper = BaseApplication.getInstance().getDbHelper();
        try {
            mDao = dbHelper.getDao(classType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Void loadInBackground() {
        return null;
    }
}
