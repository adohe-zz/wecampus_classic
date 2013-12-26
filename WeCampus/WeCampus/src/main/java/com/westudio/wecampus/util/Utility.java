package com.westudio.wecampus.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.westudio.wecampus.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nankonami on 13-9-7.
 * A common util class
 */
public class Utility {

    private static boolean IS_DEBUG = true;

    /**
     * Use this method to output log messages
     * @param tag
     * @param message
     */
    public static void log(String tag, Object message) {
        if(IS_DEBUG) {
            Log.d(tag, message.toString());
        }
    }

    /**
     * Check whether the net type is wifi
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the net type is gprs
     * @param context
     * @return
     */
    public static boolean isGprs(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the network type
     * @param context
     * @return
     */
    public static int getNetType(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    /**
     * Check whether the network is connected
     * @param context
     * @return
     */
    public static boolean isConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * Copy file
     * @param src
     * @param dst
     * @throws IOException
     */
    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     * Execute the async task
     * @param task
     * @param params
     * @param <Params>
     * @param <Progress>
     * @param <Result>
     */
    public static <Params, Progress, Result> void executeAsyncTask(
            AsyncTask<Params, Progress, Result> task, Params... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    /**
     * Get The Screen Width
     * @param activity
     * @return
     */
    public static int getScreentWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * Get The Screen Height
     * @param activity
     * @return
     */
    public static int getScreentHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * Convert dp to px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Convert px to dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Get the emotion by type
     * @param context
     * @param type
     * @return
     */
    public static String getEmotionByType(Context context, int type) {
        String emotion = null;

        switch (type) {
            case 1:
                emotion = context.getResources().getString(R.string.love_sercert);
                break;
            case 2:
                emotion = context.getResources().getString(R.string.love_first);
                break;
            case 3:
                emotion = context.getResources().getString(R.string.love_single);
                break;
            case 4:
                emotion = context.getResources().getString(R.string.love_fuck);
                break;
            case 5:
                emotion = context.getResources().getString(R.string.love_going);
                break;
            case 6:
                emotion = context.getResources().getString(R.string.love_break);
                break;
        }

        return emotion;
    }

    /**
     * Get stage by type
     * @param context
     * @param type
     * @return
     */
    public static String getStageByType(Context context, int type) {
        String stage = null;

        switch (type) {
            case 1:
                stage = context.getResources().getString(R.string.small_sister);
                break;
            case 2:
                stage = context.getResources().getString(R.string.small_brother);
                break;
            case 3:
                stage = context.getResources().getString(R.string.big_brother);
                break;
            case 4:
                stage = context.getResources().getString(R.string.big_sister);
                break;
        }

        return stage;
    }
}
