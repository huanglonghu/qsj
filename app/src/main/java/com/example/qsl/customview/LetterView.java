package com.example.qsl.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.qsl.R;

public class LetterView extends LinearLayout {
    private Context mContext;
    private CharacterClickListener mListener;

    public interface CharacterClickListener {
        void clickArrow();

        void clickCharacter(String str);
    }

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        initView();
    }

    private void initView() {
        addView(buildImageLayout());
        for (char i = 'A'; i <= 'Z'; i = (char) (i + 1)) {
            addView(buildTextLayout(i + ""));
        }
        addView(buildTextLayout("#"));
    }

    private TextView buildTextLayout(final String character) {
        LayoutParams layoutParams = new LayoutParams(-1, -1, 1.0f);
        TextView tv = (TextView) LayoutInflater.from(this.mContext).inflate(R.layout.letterview_tv, this, false);
        tv.setClickable(true);
        tv.setText(character);
        tv.setLayoutParams(layoutParams);
        tv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (LetterView.this.mListener != null) {
                    LetterView.this.mListener.clickCharacter(character);
                }
            }
        });
        return tv;
    }

    private ImageView buildImageLayout() {
        LayoutParams layoutParams = new LayoutParams(-1, -1, 1.0f);
        ImageView iv = new ImageView(this.mContext);
        iv.setLayoutParams(layoutParams);
        iv.setBackgroundResource(R.mipmap.arrow);
        iv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (LetterView.this.mListener != null) {
                    LetterView.this.mListener.clickArrow();
                }
            }
        });
        return iv;
    }

    public void setCharacterListener(CharacterClickListener listener) {
        this.mListener = listener;
    }
}
