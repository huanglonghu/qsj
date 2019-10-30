package com.example.qsl.fragment.chat;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.base.QSLApplication;
import com.example.qsl.databinding.ChatBottomEmojiBinding;
import com.example.qsl.util.RudenessScreenHelper;
import java.util.ArrayList;
import java.util.List;

public class ChatEmojiFragment extends BaseFragment {
    private ChatBottomEmojiBinding binding;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = (ChatBottomEmojiBinding) DataBindingUtil.inflate(inflater, R.layout.chat_bottom_emoji, container, false);
        initView();
        return this.binding.getRoot();
    }

    public void initData() {
    }

    public void initView() {
        initEmotion();
    }

    private void initEmotion() {
        int screenWidth = QSLApplication.getApplication().getWindownWidth();
        int spacing = (int) RudenessScreenHelper.dp2px(getActivity(), 12.0f);
        int itemWidth = (screenWidth - (spacing * 8)) / 7;
        int gvHeight = (itemWidth * 3) + (spacing * 6);
        List<GridView> emotionViews = new ArrayList();
        List<String> emotionNames = new ArrayList();
        for (String emojiName : EmotionUtils.getEmojiMap(1).keySet()) {
            emotionNames.add(emojiName);
            if (emotionNames.size() == 20) {
                emotionViews.add(createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight));
                emotionNames = new ArrayList();
            }
        }
        if (emotionNames.size() > 0) {
            emotionViews.add(createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight));
        }
        this.binding.llPointGroup.initIndicator(emotionViews.size());
        this.binding.vpComplateEmotionLayout.setAdapter(new EmotionPagerAdapter(emotionViews));
        this.binding.vpComplateEmotionLayout.setLayoutParams(new LayoutParams(screenWidth, gvHeight));
        this.binding.vpComplateEmotionLayout.addOnPageChangeListener(new OnPageChangeListener() {
            int oldPagerPos = 0;

            public void onPageScrolled(int i, float v, int i1) {
            }

            public void onPageSelected(int i) {
                ChatEmojiFragment.this.binding.llPointGroup.playByStartPointToNext(this.oldPagerPos, i);
                this.oldPagerPos = i;
            }

            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        GridView gv = new GridView(getActivity());
        gv.setSelector(android.R.color.transparent);
        gv.setNumColumns(7);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding * 2);
        gv.setLayoutParams(new ViewGroup.LayoutParams(gvWidth, gvHeight));
        gv.setAdapter(new EmotionGridViewAdapter(getActivity(), emotionNames, itemWidth, 1));
        gv.setOnItemClickListener(GlobalOnItemClickManagerUtils.getInstance(getActivity()).getOnItemClickListener(1));
        return gv;
    }

    public void initlisten() {
    }
}
