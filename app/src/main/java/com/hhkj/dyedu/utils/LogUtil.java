package com.hhkj.dyedu.utils;

import android.util.Log;

import com.hhkj.dyedu.BuildConfig;


public class LogUtil {

    private static String TAG = "ZANGYI3";

    public static void i(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.i(TAG + "," + tag, msg);
        }
    }

   

   
   
}
