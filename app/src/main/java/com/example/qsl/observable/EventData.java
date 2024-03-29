package com.example.qsl.observable;

import android.os.Bundle;

public class EventData {
    private Bundle data;
    private int eventType;

    public EventData(int eventType) {
        this.eventType = eventType;
    }

    public Bundle getData() {
        return this.data;
    }

    public void setData(Bundle data) {
        this.data = data;
    }

    public int getEventType() {
        return this.eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
