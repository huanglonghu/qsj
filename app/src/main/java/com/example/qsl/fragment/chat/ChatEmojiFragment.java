package com.example.qsl.fragment.chat;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_bottom_emoji, container, false);
        initView();
        return binding.getRoot();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initEmotion();
    }

    private void initEmotion() {
        // 获取屏幕宽度
        int screenWidth = QSLApplication.getApplication().getWindownWidth();
        // item的间距
        int spacing = (int) RudenessScreenHelper.dp2px(getActivity(), 12);
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - spacing * 8) / 7;
        //动态计算gridview的总高度
        int gvHeight = itemWidth * 3 + spacing * 6;
        List<GridView> emotionViews = new ArrayList<>();
        // 遍历所有的表情的key
        List<String> emotionNames = new ArrayList<>();
        // 遍历所有的表情的key
        for (String emojiName : EmotionUtils.getEmojiMap(EmotionUtils.EMOTION_CLASSIC_TYPE).keySet()) {
            emotionNames.add(emojiName);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 20) {
                GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                emotionViews.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<>();
            }
        }

        // 判断最后是否有不足20个表情的剩余情况
        if (emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
            emotionViews.add(gv);
        }



        //初始化指示器
        binding.llPointGroup.initIndicator(emotionViews.size());
        // 将多个GridView添加显示到ViewPager中
        EmotionPagerAdapter emotionPagerGvAdapter = new EmotionPagerAdapter(emotionViews);
        binding.vpComplateEmotionLayout.setAdapter(emotionPagerGvAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        binding.vpComplateEmotionLayout.setLayoutParams(params);
        binding.vpComplateEmotionLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos=0;
            @Override
            public void onPageScrolled(int i, float v, int i1){
            }

            @Override
            public void onPageSelected(int i) {
                binding.llPointGroup.playByStartPointToNext(oldPagerPos,i);
                oldPagerPos=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }



    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(getActivity());
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置7列
        gv.setNumColumns(7);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding * 2);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        EmotionGridViewAdapter adapter = new EmotionGridViewAdapter(getActivity(), emotionNames, itemWidth,EmotionUtils.EMOTION_CLASSIC_TYPE);
        gv.setAdapter(adapter);
        //设置全局点击事件
        gv.setOnItemClickListener(GlobalOnItemClickManagerUtils.getInstance(getActivity()).getOnItemClickListener(EmotionUtils.EMOTION_CLASSIC_TYPE));
        return gv;
    }



    @Override
    public void initlisten() {

    }
}
