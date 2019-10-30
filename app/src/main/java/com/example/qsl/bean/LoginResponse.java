package com.example.qsl.bean;

public class LoginResponse {
    private int code;
    private DataBean data;
    private String msg;

    public static class DataBean {
        private String avatar;
        private long id;
        private String token;
        private String userName;
        private int userType;

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

        public String getToken() {
            return this.token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getId() {
            return this.id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
