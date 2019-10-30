package com.example.qsl.database.option;

import com.example.qsl.base.QSLApplication;
import com.example.qsl.database.FriendDao;
import com.example.qsl.database.FriendDao.Properties;
import com.example.qsl.database.entity.Friend;
import com.example.qsl.util.StringUtil;

import java.util.List;

import rx.Observable;
import rx.subjects.Subject;

public class FriendOption {
    private static FriendOption defaultInstane;
    private Subject<Object, Boolean> friendUpdate;
    private String[] names = new String[]{"李逵", "黎明", "雷锋", "包拯", "宋江", "武松", "张飞", "刘备", "关羽", "林冲", "曹操", "黄盖", "孙悟空", "唐僧", "猪八戒", "钢铁侠", "灭霸", "蜘蛛侠", "超人", "哪吒", "姜子牙"};

    private FriendOption() {
    }

    public Observable<Boolean> friendUpdateListener() {
        return this.friendUpdate;
    }

    public static FriendOption getInstance() {
        FriendOption friendOption = defaultInstane;
        if (defaultInstane == null) {
            synchronized (FriendOption.class) {
                if (defaultInstane == null) {
                    FriendOption friendOption2 = new FriendOption();
                    defaultInstane = friendOption2;
                    friendOption = friendOption2;
                }

            }
        }
        return friendOption;
    }

    public void initData() {
        FriendDao friendDao = QSLApplication.getApplication().getDaoSession().getFriendDao();
        for (String name : this.names) {
            Friend friend = new Friend();
            friend.setUserName(name);
            friend.setFirstChar(StringUtil.getPingYin(name).substring(0, 1));
            friendDao.insert(friend);
        }
    }

    public int getFriendNum() {
        List<Friend> list = QSLApplication.getApplication().getDaoSession().getFriendDao().queryBuilder().orderAsc(Properties.FirstChar).list();
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
