package com.example.qsl.customview;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @BindingAdapter("android:text")
    public static void setText(MyEditText view, String text) {
        view.setText(text);
    }


    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart == selEnd) { // 防止不能多选
            setSelection(getText().length()); // 保证光标始终在最后面
        }

    }

}
