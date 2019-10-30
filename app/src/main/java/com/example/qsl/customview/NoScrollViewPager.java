package com.example.qsl.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
    private boolean isScroll;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.isScroll) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.isScroll) {
            return super.onTouchEvent(ev);
        }
        return true;
    }

    public void setScroll(boolean scroll) {
        this.isScroll = scroll;
    }
}
