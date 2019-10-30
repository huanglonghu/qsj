package com.example.qsl.fragment.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.example.qsl.R;
import com.example.qsl.util.RudenessScreenHelper;
import java.util.ArrayList;

public class EmojiIndicatorView extends LinearLayout {
    private Context mContext;
    private ArrayList<View> mImageViews;
    private int marginLeft;
    private int marginSize;
    private int pointSize;
    private int size;

    public EmojiIndicatorView(Context context) {
        this(context, null);
    }

    public EmojiIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.size = 6;
        this.marginSize = 15;
        this.mContext = context;
        this.pointSize = (int) RudenessScreenHelper.dp2px(context, (float) this.size);
        this.marginLeft = (int) RudenessScreenHelper.dp2px(context, (float) this.marginSize);
    }

    public void initIndicator(int count) {
        this.mImageViews = new ArrayList();
        removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = new View(this.mContext);
            LayoutParams lp = new LayoutParams(this.pointSize, this.pointSize);
            if (i != 0) {
                lp.leftMargin = this.marginLeft;
            }
            v.setLayoutParams(lp);
            if (i == 0) {
                v.setBackgroundResource(R.drawable.shape_bg_indicator_point_select);
            } else {
                v.setBackgroundResource(R.drawable.shape_bg_indicator_point_nomal);
            }
            this.mImageViews.add(v);
            addView(v);
        }
    }

    public void playByStartPointToNext(int startPosition, int nextPosition) {
        if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
            nextPosition = 0;
            startPosition = 0;
        }
        View ViewStrat = (View) this.mImageViews.get(startPosition);
        ((View) this.mImageViews.get(nextPosition)).setBackgroundResource(R.drawable.shape_bg_indicator_point_select);
        ViewStrat.setBackgroundResource(R.drawable.shape_bg_indicator_point_nomal);
    }
}
