package com.example.qsl.bean;

public class CustomerResponse {

    /**
     * code : 0
     * msg : success
     * data : {"mobile":"18288888888","avatar":"/images/face.jpg","nickName":"京京","id":3}
     */

    private int code;
    private String msg;
    private ContactBean data;

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

    public ContactBean getData() {
        return data;
    }

    public void setData(ContactBean data) {
        this.data = data;
    }

}
