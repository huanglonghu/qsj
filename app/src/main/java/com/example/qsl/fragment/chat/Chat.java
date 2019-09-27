package com.example.qsl.fragment.chat;

import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.qsl.R;
import com.example.qsl.adapter.ChatListAdapter;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.ChatBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.LogUtil;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

public class Chat extends BaseFragment {

    private ChatBinding binding;
    private Conversation mConversation;
    private ChatListAdapter chatListAdapter;
    private List<Message> allMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.chat, container, false);
            initlisten();
            initView();
            initData();
        }
        return binding.getRoot();
    }

    private String converationId;

    private String converstaionName;


    @Override
    public void initData() {
        Bundle bundle = getArguments();
        converstaionName = bundle.getString("converstaionName");
        converationId = bundle.getString("converationId");
        binding.salePersonName.setText(converstaionName);
        UserBean userBean = UserOption.getInstance().querryUser();
        HttpUtil.getInstance().createChat(userBean.getId().intValue()).subscribe();
        mConversation = Conversation.createSingleConversation(converationId);
        mConversation.resetUnreadCount();
        initConvListAdapter();
    }

    public void onEventMainThread(MessageEvent event) {
        allMessage.clear();
        allMessage.addAll(mConversation.getAllMessage());
        chatListAdapter.notifyDataSetChanged();
    }


    public void sendTextMessage(String sendContent) {
        Message message = mConversation.createSendTextMessage(sendContent);
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int status, String desc) {
                if (status == 0) {
                    allMessage.add(mConversation.getLatestMessage());
                    chatListAdapter.notifyDataSetChanged();
                    LogUtil.log("==========success===========" + allMessage.size());
                } else {
                    // 消息发送失败
                    LogUtil.log(status + "============发送失败==============" + desc);
                    Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
                }
            }
        });
        JMessageClient.sendMessage(message);
    }


    public void sendImgMessage(String filePath) {
        try {
            File file = new File(filePath);
            Message message = mConversation.createSendImageMessage(file);
            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        allMessage.add(mConversation.getLatestMessage());
                        chatListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "图片发送失败", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            JMessageClient.sendMessage(message);

        } catch (Exception e) {

        }


    }


    @Override

    public void initView() {
//        ChatBottomMainFragment chatBottomMainFragment = new ChatBottomMainFragment();
//        chatBottomMainFragment.bindToContentView(binding.contentView);
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.replace(R.id.bottom_container, chatBottomMainFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

    @Override
    public void initlisten() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });
        JMessageClient.registerEventReceiver(this);
        binding.lvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //判断底部视图是否开启
                if (onBottomCancleListener != null) {
                    onBottomCancleListener.bottomCancle();
                }
                return false;
            }
        });

    }


    public interface OnBottomCancleListener {

        void bottomCancle();
    }

    private OnBottomCancleListener onBottomCancleListener;


    public void setOnBottomCancleListener(OnBottomCancleListener onBottomCancleListener) {
        this.onBottomCancleListener = onBottomCancleListener;
    }




    private void initConvListAdapter() {
        HashMap<Integer, Long> map = new HashMap<>();
        allMessage = mConversation.getAllMessage();
        for (int i = 0; i < allMessage.size(); i++) {
            Message message = allMessage.get(i);
            long createTime = message.getCreateTime();
            map.put(i, createTime);
        }
        chatListAdapter = new ChatListAdapter(getContext(), allMessage, 0, map);
        if (allMessage != null) {
            binding.lvChat.setAdapter(chatListAdapter);
            binding.lvChat.setSelection(chatListAdapter.getCount() - 1);
        }
        chatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                binding.lvChat.setSelection(chatListAdapter.getCount() - 1);
            }
        });
    }

    @Override
    public void onKeyDown() {
        if (onBottomCancleListener != null) {
            onBottomCancleListener.bottomCancle();
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Boolean isSelectSalePerson = getArguments().getBoolean("isSelectSalePerson");
        if (isSelectSalePerson != null && isSelectSalePerson) {
            fm.popBackStack("selectSaleMan", 1);
        } else {
            fm.popBackStack();
        }
    }



}

