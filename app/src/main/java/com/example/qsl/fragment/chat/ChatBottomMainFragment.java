package com.example.qsl.fragment.chat;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.databinding.ChatBottomMainBinding;
import java.util.ArrayList;

public class ChatBottomMainFragment extends BaseFragment {
    private ChatBottomMainBinding binding;
    private EmotionKeyboard mEmotionKeyboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.chat_bottom_main, container, false);
            Chat chat = (Chat) getParentFragment();
            binding.conversation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        binding.add.setVisibility(View.VISIBLE);
                        binding.send.setVisibility(View.GONE);
                    } else {
                        binding.add.setVisibility(View.GONE);
                        binding.send.setVisibility(View.VISIBLE);
                    }
                }
            });
            binding.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sendContent = binding.conversation.getText().toString();
                    if (TextUtils.isEmpty(sendContent)) {
                        Toast.makeText(getContext(), "请输入聊天内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    binding.conversation.setText("");
                    chat.sendTextMessage(sendContent);
                }
            });



            GlobalOnItemClickManagerUtils.getInstance(getContext()).attachToEditText(binding.conversation);
            FragmentManager cfm = getChildFragmentManager();
            ArrayList<BaseFragment> fragments = new ArrayList<>();
            fragments.add(new ChatEmojiFragment());
            ChatAddFragment chatAddFragment = new ChatAddFragment();
            chatAddFragment.chat(chat);
            fragments.add(chatAddFragment);
            mEmotionKeyboard = EmotionKeyboard.with(getActivity())
                    .setEmotionView(binding.botoomLyoutContainer)//绑定表情面板
                    .bindToContent(contentView)//绑定内容view
                    .fragments(fragments, cfm)
                    .bindToEditText(binding.conversation)//判断绑定那种EditView
                    .bindToEmotionButton(binding.emoji, 0)//绑定表情按钮
                    .bindToEmotionButton(binding.add, 1)
                    .build();
            chat.setOnBottomCancleListener(new Chat.OnBottomCancleListener() {
                @Override
                public void bottomCancle() {
                    mEmotionKeyboard.interceptBackPress();
                }
            });

        }
        return binding.getRoot();
    }

    public void setCyy(String string) {
        binding.conversation.setText(string);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {

    }

    private View contentView;

    public void bindToContentView(View contentView) {
        this.contentView = contentView;
    }


}
