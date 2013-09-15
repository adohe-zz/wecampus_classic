/*
package com.westudio.wecampus.data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.j256.ormlite.dao.Dao;
import com.westudio.wecampus.ui.base.BaseApplication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by nankonami on 13-9-7.
 * Load data from database
 *//*

public class DBListLoader<T, ID> extends AsyncTaskLoader<List<T>> {

    private Dao<T, ID> mDao = null;
    private List<T> mData = null;
    private DBHelper dbHelper = null;

    public DBListLoader(Context context, Class<T> clazz) {
        super(context);
        dbHelper = BaseApplication.getInstance().getDbHelper();
        try {
            mDao = dbHelper.getDao(clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> loadInBackground() {
        List<T> result = new ArrayList<T>();

        try {
            result = mDao.queryForAll();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    */
/**
     * Handles a request to start the loader
     *//*

    @Override
    protected void onStartLoading() {
        if(mData != null) {
            //If we currently have data available, deliver it immediately
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    */
/**
     * Called when there is new data to delivered to the client.
     * @param data
     *//*

    @Override
    public void deliverResult(List<T> data) {
        if(isReset()) {
            //An async query came when the loader is stopped.
            //We don't need the result
            if(data != null) {
                onReleaseResource(data);
            }
        }

        List<T> oldData = data;
        mData = data;

        if(isStarted()) {
            //If the loader is currently started, we
            //immediately deliver the result
            super.deliverResult(mData);
        }

        // At this point we can release the resources associated with
        // 'oldData' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if(oldData != null) {
            onReleaseResource(oldData);
        }
    }

    */
/**
     * Handles a request to completely reset the Loader
     *//*

    @Override
    protected void onReset() {
        super.onReset();

        //Ensure the loader is stopped
        onStopLoading();

        //At this point we can release the resources associated with the mData
        //at all
        if(mData != null) {
            onReleaseResource(mData);
            mData = null;
        }
    }

    */
/**
     * Handles a request to stop the loader
     *//*

    @Override
    protected void onStopLoading() {
        //Attempt to cancel the current loader task
        cancelLoad();
    }

    */
/**
     * Handles a request to cancel the loader
     * @param data
     *//*

    @Override
    public void onCanceled(List<T> data) {
        super.onCanceled(data);

        onReleaseResource(data);
    }

    */
/**
     * Helper function to take care of releasing resources associated
     * with an actively data set
     * @param data
     *//*

    protected void onReleaseResource(List<T> data) {

    }
}
*/
