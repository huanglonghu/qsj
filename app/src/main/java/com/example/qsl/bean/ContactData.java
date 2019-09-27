package com.example.qsl.bean;

import java.util.ArrayList;

public class ContactData {


    private String charcter;

    private ArrayList<ContactsResponse.DataBean.ItemsBean> contacts;


    public String getCharcter() {
        return charcter;
    }

    public void setCharcter(String charcter) {
        this.charcter = charcter;
    }

    public ArrayList<ContactsResponse.DataBean.ItemsBean> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<ContactsResponse.DataBean.ItemsBean> contacts) {
        this.contacts = contacts;
    }
}
