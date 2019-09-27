package com.example.qsl.database.option;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.example.qsl.base.QSLApplication;
import com.example.qsl.bean.ContactData;
import com.example.qsl.constant.Constants;
import com.example.qsl.database.FriendDao;
import com.example.qsl.database.entity.Friend;
import com.example.qsl.util.LogUtil;
import com.example.qsl.util.StringUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.subjects.Subject;

public class FriendOption {

    private Subject<Object, Boolean> friendUpdate;

    private FriendOption() {
    }

    public rx.Observable<Boolean> friendUpdateListener() {
        return friendUpdate;
    }

    private static FriendOption defaultInstane;

    public static FriendOption getInstance() {
        FriendOption friendOption = defaultInstane;
        if (defaultInstane == null) {
            synchronized (FriendOption.class) {
                if (defaultInstane == null) {
                    friendOption = new FriendOption();
                    defaultInstane = friendOption;
                }
            }
        }
        return friendOption;
    }


    private String[] names = {"李逵", "黎明", "雷锋", "包拯", "宋江", "武松", "张飞", "刘备", "关羽", "林冲", "曹操", "黄盖", "孙悟空", "唐僧", "猪八戒"
            , "钢铁侠", "灭霸", "蜘蛛侠", "超人", "哪吒", "姜子牙"
    };

    public void initData() {
        FriendDao friendDao = QSLApplication.getApplication().getDaoSession().getFriendDao();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            Friend friend = new Friend();
            friend.setUserName(name);
            String firstChar = StringUtil.getPingYin(name).substring(0, 1);
            friend.setFirstChar(firstChar);
            friendDao.insert(friend);
        }
    }




    public int getFriendNum() {
        FriendDao friendDao = QSLApplication.getApplication().getDaoSession().getFriendDao();
        List<Friend> list = friendDao.queryBuilder().orderAsc(FriendDao.Properties.FirstChar).list();
        return list == null ? 0 : list.size();
    }


}
