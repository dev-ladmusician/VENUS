package com.kim.ladmusician.venus.util;

import android.util.Log;

import com.kim.ladmusician.venus.BuildConfig;

/**
 * Created by ladmusician on 4/21/16.
 */
public class LogUtil {
    public static void print(String tag, String msg) {
        if(BuildConfig.isDebug) Log.e(tag, msg);
    }
}
