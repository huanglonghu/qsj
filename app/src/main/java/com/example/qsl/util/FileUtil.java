package com.example.qsl.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import java.io.File;

public class FileUtil {
    public static String getRealFilePathFromUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if ("file".equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if ("content".equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex("_data");
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (dir.exists()) {
            return dirPath;
        }
        dir.mkdirs();
        return dirPath;
    }
}
