package com.example.qsl.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Friend {

    @Id(autoincrement = true)
    private Long id;//主键  自增长
    private String userName;
    private String headImageUrl;
    private String firstChar;
    @Generated(hash = 481658893)
    public Friend(Long id, String userName, String headImageUrl, String firstChar) {
        this.id = id;
        this.userName = userName;
        this.headImageUrl = headImageUrl;
        this.firstChar = firstChar;
    }
    @Generated(hash = 287143722)
    public Friend() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getHeadImageUrl() {
        return this.headImageUrl;
    }
    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }
    public String getFirstChar() {
        return this.firstChar;
    }
    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }


}
