package com.example.qsl.database.option;

import com.example.qsl.base.QSLApplication;
import com.example.qsl.database.UserBeanDao;
import com.example.qsl.database.entity.UserBean;
import java.util.List;

public class UserOption {
    private static UserOption defaultInstance;

    private UserOption() {
    }

    public static UserOption getInstance() {
        UserOption userOption = defaultInstance;
        if (defaultInstance == null) {
            synchronized (UserOption.class) {
                if (defaultInstance == null) {
                    UserOption userOption2 = new UserOption();
                    defaultInstance = userOption2;
                    userOption = userOption2;
                }
            }
        }
        return userOption;
    }

    public void addUser(UserBean userBean) {
        UserBeanDao userBeanDao = QSLApplication.getApplication().getDaoSession().getUserBeanDao();
        if (querryUser() == null) {
            userBeanDao.insert(userBean);
        } else {
            userBeanDao.update(userBean);
        }
    }

    public UserBean querryUser() {
        List<UserBean> list = QSLApplication.getApplication().getDaoSession().getUserBeanDao().queryBuilder().list();
        if (list == null || list.size() <= 0) {
            return null;
        }
        return (UserBean) list.get(0);
    }

    public void updateUser(UserBean userBean) {
        QSLApplication.getApplication().getDaoSession().getUserBeanDao().update(userBean);
    }

    public void delteUser() {
        QSLApplication.getApplication().getDaoSession().getUserBeanDao().deleteAll();
    }
}
