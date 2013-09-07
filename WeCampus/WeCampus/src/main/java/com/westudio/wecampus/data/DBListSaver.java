package com.westudio.wecampus.data;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.j256.ormlite.dao.Dao;
import com.westudio.wecampus.ui.base.BaseApplication;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by nankonami on 13-9-7.
 */
public class DBListSaver<T, ID> extends AsyncTaskLoader<Void> implements Callable<Void> {

    private Dao<T, ID> mDao = null;
    private DBHelper dbHelper;
    private List<T> mData = null;
    private Class<T> mClass;
    private Bundle args;

    public DBListSaver(Context context, Class<T> classType, List<T> data, Bundle args) {
        super(context);
        this.mClass = classType;
        this.mData = data;
        this.args = args;
        dbHelper = BaseApplication.getInstance().getDbHelper();
        try {
            mDao = dbHelper.getDao(classType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Background task
     * @return
     */
    @Override
    public Void loadInBackground() {
        try {
            mDao.callBatchTasks(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onCanceled(Void data) {
        super.onCanceled(data);
    }

    /**
     * Handles a request to start the loader
     */
    @Override
    protected void onStartLoading() {
        super.onStopLoading();

        forceLoad();
    }

    /**
     * Handles a request to stop the loader
     */
    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    /**
     * Handles a request to completely reset the loader
     */
    @Override
    protected void onReset() {
        super.onReset();

        //Ensure the loader is stopped
        onStopLoading();

        onReleaseResource(mData);
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively data set
     * @param data
     */
    protected void onReleaseResource(List<T> data) {

    }

    @Override
    public Void call() throws Exception {
        for(T data : mData) {
            mDao.createOrUpdate(data);
        }

        return null;
    }
}
