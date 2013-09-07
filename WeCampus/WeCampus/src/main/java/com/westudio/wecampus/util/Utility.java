package com.westudio.wecampus.util;

import android.util.Log;

/**
 * Created by nankonami on 13-9-7.
 */
public class Utility {

    private static boolean IS_DEBUG = true;

    /**
     * Use this method to output log messages
     * @param tag
     * @param message
     */
    public static void log(String tag, String message) {
        if(IS_DEBUG) {
            Log.d(tag, message);
        }
    }
}
