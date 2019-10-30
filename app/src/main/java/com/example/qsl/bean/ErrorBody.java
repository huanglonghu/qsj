package com.example.qsl.bean;

public class ErrorBody {
    private int Errcode;
    private String Message;

    public int getErrcode() {
        return this.Errcode;
    }

    public void setErrcode(int Errcode) {
        this.Errcode = Errcode;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
