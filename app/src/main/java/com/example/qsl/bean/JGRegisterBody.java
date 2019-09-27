package com.example.qsl.bean;

public class JGRegisterBody {


    /**
     * userId : 0
     * userType : 0
     * userName : string
     */

    private int userId;
    private int userType;
    private String userName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
