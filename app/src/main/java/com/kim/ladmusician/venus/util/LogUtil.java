package com.kim.ladmusician.venus.util;

import android.support.graphics.drawable.BuildConfig;
import android.util.Log;

/**
 * Created by ladmusician on 4/21/16.
 */
public class LogUtil {
    public static void print(String tag, String msg) {
        if(BuildConfig.DEBUG) Log.e(tag, msg);
    }
}
