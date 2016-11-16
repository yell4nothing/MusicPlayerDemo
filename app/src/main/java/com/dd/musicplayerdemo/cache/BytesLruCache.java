package com.dd.musicplayerdemo.cache;

import android.util.LruCache;

/**
 * Created by my on 2016/11/5.
 */

public class BytesLruCache extends LruCache <String, byte[]>{
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public BytesLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, byte[] value) {
        return value.length;
    }
}
