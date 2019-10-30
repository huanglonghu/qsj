package com.example.qsl.fragment.chat;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

public class GlobalOnItemClickManagerUtils {
    private static GlobalOnItemClickManagerUtils instance;
    private static Context mContext;
    private EditText mEditText;

    public static GlobalOnItemClickManagerUtils getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (GlobalOnItemClickManagerUtils.class) {
                if (instance == null) {
                    instance = new GlobalOnItemClickManagerUtils();
                }
            }
        }
        return instance;
    }

    public void attachToEditText(EditText editText) {
        this.mEditText = editText;
    }

    public OnItemClickListener getOnItemClickListener(final int emotion_map_type) {
        return new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adapter itemAdapter = parent.getAdapter();
                if (itemAdapter instanceof EmotionGridViewAdapter) {
                    EmotionGridViewAdapter emotionGvAdapter = (EmotionGridViewAdapter) itemAdapter;
                    if (position == emotionGvAdapter.getCount() - 1) {
                        GlobalOnItemClickManagerUtils.this.mEditText.dispatchKeyEvent(new KeyEvent(0, 67));
                        return;
                    }
                    String item = emotionGvAdapter.getItem(position);
                    int curPosition = GlobalOnItemClickManagerUtils.this.mEditText.getSelectionStart();
                    StringBuilder sb = new StringBuilder(GlobalOnItemClickManagerUtils.this.mEditText.getText().toString());
                    sb.insert(curPosition, item);
                    GlobalOnItemClickManagerUtils.this.mEditText.setText(SpanStringUtils.getEmotionContent(emotion_map_type, GlobalOnItemClickManagerUtils.mContext, GlobalOnItemClickManagerUtils.this.mEditText, sb.toString()));
                    GlobalOnItemClickManagerUtils.this.mEditText.setSelection(item.length() + curPosition);
                }
            }
        };
    }
}
