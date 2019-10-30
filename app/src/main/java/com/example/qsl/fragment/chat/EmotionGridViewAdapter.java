package com.example.qsl.fragment.chat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.qsl.R;
import java.util.List;

public class EmotionGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> emotionNames;
    private int emotion_map_type;
    private int itemWidth;

    public EmotionGridViewAdapter(Context context, List<String> emotionNames, int itemWidth, int emotion_map_type) {
        this.context = context;
        this.emotionNames = emotionNames;
        this.itemWidth = itemWidth;
        this.emotion_map_type = emotion_map_type;
    }

    public int getCount() {
        return this.emotionNames.size() + 1;
    }

    public String getItem(int position) {
        return (String) this.emotionNames.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv_emotion = new ImageView(this.context);
        iv_emotion.setPadding(this.itemWidth / 8, this.itemWidth / 8, this.itemWidth / 8, this.itemWidth / 8);
        iv_emotion.setLayoutParams(new LayoutParams(this.itemWidth, this.itemWidth));
        if (position == getCount() - 1) {
            iv_emotion.setImageResource(R.drawable.compose_emotion_delete);
        } else {
            iv_emotion.setImageResource(EmotionUtils.getImgByName(this.emotion_map_type, (String) this.emotionNames.get(position)));
        }
        return iv_emotion;
    }
}
