package com.example.qsl.customview;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends AppCompatEditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @BindingAdapter({"android:text"})
    public static void setText(MyEditText view, String text) {
        view.setText(text);
    }

    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart == selEnd) {
            setSelection(getText().length());
        }
    }
}
