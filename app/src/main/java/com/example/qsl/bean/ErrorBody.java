package com.example.qsl.bean;

public class ErrorBody {


    /**
     * Errcode : 400
     * Message : 参数错误
     * Data : ["字段 Password 必须是一个字符串，其最小长度为 6，最大长度为 15。"]
     */

    private int Errcode;
    private String Message;



    public int getErrcode() {
        return Errcode;
    }

    public void setErrcode(int Errcode) {
        this.Errcode = Errcode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

}
