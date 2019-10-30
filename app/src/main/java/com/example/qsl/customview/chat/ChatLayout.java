package com.example.qsl.customview.chat;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ChatLayout extends RelativeLayout {
    public ChatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.parseColor("#00000000"));
    }
}
