package com.example.qsl.fragment.chat;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import java.util.List;

public class EmotionPagerAdapter extends PagerAdapter {
    private List<GridView> gvs;

    public EmotionPagerAdapter(List<GridView> gvs) {
        this.gvs = gvs;
    }

    public int getCount() {
        return this.gvs.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) this.gvs.get(position));
    }

    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView((View) this.gvs.get(position));
        return this.gvs.get(position);
    }
}
