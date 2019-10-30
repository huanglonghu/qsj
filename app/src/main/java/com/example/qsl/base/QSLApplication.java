package com.example.qsl.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import cn.jpush.im.android.api.JMessageClient;
import com.example.qsl.database.DaoMaster;
import com.example.qsl.database.DaoMaster.DevOpenHelper;
import com.example.qsl.database.DaoSession;
import com.example.qsl.util.RudenessScreenHelper;
import com.tencent.bugly.crashreport.CrashReport;

public class QSLApplication extends Application {
    private static QSLApplication application;
    private SQLiteDatabase db;
    private DaoSession mDaoSession;
    public Vibrator mVibrator;
    private int windowHeight;
    private int windownWidth;

    public void onCreate() {
        super.onCreate();
        application = this;
        this.mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        initWindowSize();
        new RudenessScreenHelper(this, (float) 750).activate();
        CrashReport.initCrashReport(this, "6876c53e79", true);
        JMessageClient.setDebugMode(false);
        JMessageClient.init(getApplicationContext(),true);
        setDatabase();
    }

    public void initWindowSize() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        windownWidth = metric.widthPixels;
        windowHeight = metric.heightPixels;
    }

    public int getWindownWidth() {
        return this.windownWidth;
    }

    public int getWindowHeight() {
        return this.windowHeight;
    }

    public static QSLApplication getApplication() {
        return application;
    }

    private void setDatabase() {
        this.db = new DevOpenHelper(this, "notes-db", null).getWritableDatabase();
        this.mDaoSession = new DaoMaster(this.db).newSession();
    }

    public DaoSession getDaoSession() {
        return this.mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }
}
