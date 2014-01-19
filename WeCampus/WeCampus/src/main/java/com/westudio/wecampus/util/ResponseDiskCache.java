package com.westudio.wecampus.util;

import com.android.volley.Cache;
import com.android.volley.toolbox.DiskBasedCache;

import java.io.File;

/**
 * Created by nankonami on 13-10-4.
 * Custom Disk Cache to change the default cache-control
 * setting
 */
public class ResponseDiskCache extends DiskBasedCache {

    public ResponseDiskCache(File rootDirectory, int maxCacheSizeInBytes) {
        super(rootDirectory, maxCacheSizeInBytes);
    }

    @Override
    public synchronized void put(String key, Entry entry) {

        if(entry != null) {
            entry.etag = null;
            entry.softTtl = Long.MAX_VALUE;
            entry.ttl = Long.MAX_VALUE;
        }

        super.put(key, entry);
    }

    @Override
    public File getFileForKey(String key) {
        return super.getFileForKey(key);
    }

    @Override
    public synchronized Entry get(String key) {
        return super.get(key);
    }
}
