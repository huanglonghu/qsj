package com.example.qsl.util;

import android.util.Log;

public class LogUtil {
    private static int LOG_MAXLENGTH = 2048;
    private static boolean isPrintLog = true;

    public static void log(String msg) {
        if (isPrintLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            int i = 0;
            while (i < 100) {
                if (strLength > end) {
                    Log.e("part" + i, msg.substring(start, end));
                    start = end;
                    end += LOG_MAXLENGTH;
                    i++;
                } else {
                    Log.e("part" + i, msg.substring(start, strLength));
                    return;
                }
            }
        }
    }
}
