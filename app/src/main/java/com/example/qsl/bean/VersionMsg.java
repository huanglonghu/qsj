package com.example.qsl.bean;

public class VersionMsg {
    private String app;
    private String update_version;
    private double versionName;

    public double getVersionName() {
        return this.versionName;
    }

    public void setVersionName(double versionName) {
        this.versionName = versionName;
    }

    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUpdate_version() {
        return this.update_version;
    }

    public void setUpdate_version(String update_version) {
        this.update_version = update_version;
    }
}
