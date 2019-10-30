package com.example.qsl.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Friend {
    private String firstChar;
    private String headImageUrl;
    @Id
    private Long id;
    private String userName;
    @Generated(hash = 797586881)
    public Friend(String firstChar, String headImageUrl, Long id, String userName) {
        this.firstChar = firstChar;
        this.headImageUrl = headImageUrl;
        this.id = id;
        this.userName = userName;
    }
    @Generated(hash = 287143722)
    public Friend() {
    }
    public String getFirstChar() {
        return this.firstChar;
    }
    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }
    public String getHeadImageUrl() {
        return this.headImageUrl;
    }
    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
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
    


}
