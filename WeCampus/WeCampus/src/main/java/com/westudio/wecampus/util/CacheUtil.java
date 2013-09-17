package com.westudio.wecampus.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by nankonami on 13-9-17.
 */
public class CacheUtil {

    /**
     * Get the external cache directory
     * @param context
     * @return
     */
    public static File getExternalCacheDir(final Context context) {
        if(hasExternalCacheDir())
            return context.getExternalCacheDir();

        //Before the Froyo we have to construct the external cache dir by ourself
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * Detect whether the SDK version larger than Froyo
     * @return
     */
    private static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }
}
