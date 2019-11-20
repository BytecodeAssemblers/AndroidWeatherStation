package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MimageLoader {


    public MimageLoader(Activity activity) {
        this.activity = activity;
    }

    public RequestQueue mRequestQueue;
    public ImageLoader mImageLoader;
    public  Activity activity;


    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }

    public  void setImageLoader() {
        mRequestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        this.mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

    }

}
