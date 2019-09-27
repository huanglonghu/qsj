package com.example.qsl.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class UserBean {
    @Id
    private Long id;
    private String phoneNumber;
    private String userName;
    private String avatar;
    private int userType;
    private String commanyName;
    private String email;
    private String address;
    private String sex;
    private String token;
    @Generated(hash = 1713736712)
    public UserBean(Long id, String phoneNumber, String userName, String avatar,
            int userType, String commanyName, String email, String address,
            String sex, String token) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.avatar = avatar;
        this.userType = userType;
        this.commanyName = commanyName;
        this.email = email;
        this.address = address;
        this.sex = sex;
        this.token = token;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
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
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public int getUserType() {
        return this.userType;
    }
    public void setUserType(int userType) {
        this.userType = userType;
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
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
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


    



}
