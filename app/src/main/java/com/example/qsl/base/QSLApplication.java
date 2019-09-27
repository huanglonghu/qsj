package com.example.qsl.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.qsl.database.DaoMaster;
import com.example.qsl.database.DaoSession;
import com.example.qsl.util.RudenessScreenHelper;

import cn.jpush.im.android.api.JMessageClient;

public class QSLApplication extends Application {
    private static QSLApplication application;

    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        initWindowSize();
        int designWidth = 750;
        new RudenessScreenHelper(this, designWidth).activate();

        JMessageClient.setDebugMode(false);
        JMessageClient.init(getApplicationContext());
        setDatabase();
        //注册全局事件监听类

    }

    public void initWindowSize() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        windownWidth = metric.widthPixels;
        windowHeight = metric.heightPixels;

    }

    private int windownWidth;
    private int windowHeight;

    public int getWindownWidth() {
        return windownWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    /**获取上下文*/
    public static QSLApplication getApplication() {
        return application;
    }




    private DaoSession mDaoSession;
    private SQLiteDatabase db;
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);

        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }



}
