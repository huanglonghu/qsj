package com.example.qsl.bean;

import java.util.List;

public class UserResponse {


    /**
     * code : 0
     * msg : success
     * data : {"items":[{"mobile":"13076737863","avatar":"/User/4/fd9bbecd464c4c91b84a70df84502579.jpg","nickName":"QQ","id":4}]}
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
        private List<ContactBean> items;

        public List<ContactBean> getItems() {
            return items;
        }

        public void setItems(List<ContactBean> items) {
            this.items = items;
        }

    }
}
