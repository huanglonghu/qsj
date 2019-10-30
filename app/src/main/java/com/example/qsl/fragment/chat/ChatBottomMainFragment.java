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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.databinding.ChatBottomMainBinding;
import com.example.qsl.fragment.chat.Chat.OnBottomCancleListener;
import java.util.ArrayList;

public class ChatBottomMainFragment extends BaseFragment {
    private ChatBottomMainBinding binding;
    private View contentView;
    private EmotionKeyboard mEmotionKeyboard;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.binding == null) {
            this.binding = (ChatBottomMainBinding) DataBindingUtil.inflate(inflater, R.layout.chat_bottom_main, container, false);
            final Chat chat = (Chat) getParentFragment();
            this.binding.conversation.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        ChatBottomMainFragment.this.binding.add.setVisibility(View.VISIBLE);
                        ChatBottomMainFragment.this.binding.send.setVisibility(View.GONE);
                        return;
                    }
                    ChatBottomMainFragment.this.binding.add.setVisibility(View.GONE);
                    ChatBottomMainFragment.this.binding.send.setVisibility(View.VISIBLE);
                }
            });
            this.binding.send.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String sendContent = ChatBottomMainFragment.this.binding.conversation.getText().toString();
                    if (TextUtils.isEmpty(sendContent)) {
                        Toast.makeText(ChatBottomMainFragment.this.getContext(), "请输入聊天内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ChatBottomMainFragment.this.binding.conversation.setText("");
                    chat.sendTextMessage(sendContent);
                }
            });
            GlobalOnItemClickManagerUtils.getInstance(getContext()).attachToEditText(this.binding.conversation);
            FragmentManager cfm = getChildFragmentManager();
            ArrayList<BaseFragment> fragments = new ArrayList();
            fragments.add(new ChatEmojiFragment());
            ChatAddFragment chatAddFragment = new ChatAddFragment();
            chatAddFragment.chat(chat);
            fragments.add(chatAddFragment);
            this.mEmotionKeyboard = EmotionKeyboard.with(getActivity()).setEmotionView(this.binding.botoomLyoutContainer).bindToContent(this.contentView).fragments(fragments, cfm).bindToEditText(this.binding.conversation).bindToEmotionButton(this.binding.emoji, 0).bindToEmotionButton(this.binding.add, 1).build();
            chat.setOnBottomCancleListener(new OnBottomCancleListener() {
                public void bottomCancle() {
                    ChatBottomMainFragment.this.mEmotionKeyboard.interceptBackPress();
                }
            });
        }
        return this.binding.getRoot();
    }

    public void setCyy(String string) {
        this.binding.conversation.setText(string);
    }

    public void initData() {
    }

    public void initView() {
    }

    public void initlisten() {
    }

    public void bindToContentView(View contentView) {
        this.contentView = contentView;
    }
}
