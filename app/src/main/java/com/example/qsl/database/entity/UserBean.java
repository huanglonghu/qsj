package com.example.qsl.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {
    private String address;
    private String avatar;
    private String commanyName;
    private String email;
    @Id
    private Long id;
    private String phoneNumber;
    private String sex;
    private String token;
    private String userName;
    private int userType;
    @Generated(hash = 325997867)
    public UserBean(String address, String avatar, String commanyName, String email,
            Long id, String phoneNumber, String sex, String token, String userName,
            int userType) {
        this.address = address;
        this.avatar = avatar;
        this.commanyName = commanyName;
        this.email = email;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.token = token;
        this.userName = userName;
        this.userType = userType;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getCommanyName() {
        return this.commanyName;
    }
    public void setCommanyName(String commanyName) {
        this.commanyName = commanyName;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getUserType() {
        return this.userType;
    }
    public void setUserType(int userType) {
        this.userType = userType;
    }



}
