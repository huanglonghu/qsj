package com.example.qsl.bean;

public class ContactBean {

    /**
     * mobile : 13076737863
     * avatar : /User/4/fd9bbecd464c4c91b84a70df84502579.jpg
     * nickName : QQ
     * id : 4
     */

    private String mobile;
    private String avatar;
    private String nickName;
    private int id;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
