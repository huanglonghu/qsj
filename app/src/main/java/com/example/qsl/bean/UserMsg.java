package com.example.qsl.bean;

public class UserMsg {
    /**
     * code : 0
     * msg : success
     * data : {"mobile":"18688888888","avatar":"/Admin/1/d60b69e77064491ebef5ed2a5ba57427.jpg","nickName":"欢欢","isEnable":true,"profile":"客服3","creationTime":"2019-09-25T18:55:09.0788001","id":4}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * mobile : 18688888888
         * avatar : /Admin/1/d60b69e77064491ebef5ed2a5ba57427.jpg
         * nickName : 欢欢
         * isEnable : true
         * profile : 客服3
         * creationTime : 2019-09-25T18:55:09.0788001
         * id : 4
         */

        private String mobile;
        private String avatar;
        private String nickName;
        private boolean isEnable;
        private String profile;
        private String creationTime;
        private int id;

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

        public boolean isIsEnable() {
            return isEnable;
        }

        public void setIsEnable(boolean isEnable) {
            this.isEnable = isEnable;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
