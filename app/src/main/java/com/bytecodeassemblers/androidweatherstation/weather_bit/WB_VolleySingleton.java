package com.bytecodeassemblers.androidweatherstation.weather_bit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bytecodeassemblers.androidweatherstation.MainActivity;

public class WB_VolleySingleton  {

    private static WB_VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Activity activity;
    //private WeatherBitController controller;

    private WB_VolleySingleton(){

        mRequestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);

            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url,bitmap);
            }

        });

    }

    public static synchronized WB_VolleySingleton getInstance(){
        if(mInstance == null){
            mInstance = new WB_VolleySingleton();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }


}
