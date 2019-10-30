package com.example.qsl.fragment.chat;

import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Toast;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import com.example.qsl.R;
import com.example.qsl.adapter.ChatListAdapter;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.JGRegisterBody;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.ChatBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.LogUtil;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Chat extends BaseFragment {
    private List<Message> allMessage;
    private ChatBinding binding;
    private ChatListAdapter chatListAdapter;
    private String converationId;
    private String converstaionName;
    private Conversation mConversation;
    private OnBottomCancleListener onBottomCancleListener;

    public interface OnBottomCancleListener {
        void bottomCancle();
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.binding == null) {
            JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_DISABLE);
            this.binding = (ChatBinding) DataBindingUtil.inflate(inflater, R.layout.chat, container, false);
            initlisten();
            initView();
            if (JMessageClient.getMyInfo() != null) {
                initData();
            } else {
                jgLogin();
            }
        }
        this.activity.showStatusBar();
        return this.binding.getRoot();
    }

    private void jgLogin() {
        final UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            JMessageClient.login(userBean.getUserType() + "__" + userBean.getId(), "123456", new BasicCallback() {
                public void gotResult(int i, String s) {
                    Toast.makeText(Chat.this.getContext(), "登录" + s, Toast.LENGTH_SHORT).show();
                    if (i == 801003) {
                        JGRegisterBody jgRegisterBody = new JGRegisterBody();
                        jgRegisterBody.setUserId(userBean.getId().intValue());
                        jgRegisterBody.setUserName(userBean.getUserName());
                        jgRegisterBody.setUserType(userBean.getUserType());
                        HttpUtil.getInstance().registerJG(jgRegisterBody).subscribe(str -> {
                            jgLogin();
                        });
                    } else if (i == 0) {
                        initData();
                    }
                }
            });
        }
    }

    public void initData() {
        Bundle bundle = getArguments();
        this.converstaionName = bundle.getString("converstaionName");
        this.converationId = bundle.getString("converationId");
        this.binding.salePersonName.setText(this.converstaionName);
        this.mConversation = Conversation.createSingleConversation(this.converationId, "c705845f376feb3803e7eb36");
        initConvListAdapter();
    }

    public void onEventMainThread(MessageEvent event) {
        this.allMessage.clear();
        this.allMessage.addAll(this.mConversation.getAllMessage());
        this.chatListAdapter.notifyDataSetChanged();
    }

    public void sendTextMessage(String sendContent) {
        Message message = mConversation.createSendTextMessage(sendContent);
        allMessage.add(message);
        chatListAdapter.notifyDataSetChanged();
        JMessageClient.sendMessage(message);
    }

    public void sendImgMessage(String filePath) {
        try {
            Message message = mConversation.createSendImageMessage(new File(filePath));
            allMessage.add(message);
            chatListAdapter.notifyDataSetChanged();
            JMessageClient.sendMessage(message);
        } catch (Exception e) {
            LogUtil.log("====eeeeeeeeeeeeeeeee============"+e.toString());
        }
    }

    public void initView() {
        ChatBottomMainFragment chatBottomMainFragment = new ChatBottomMainFragment();
        chatBottomMainFragment.bindToContentView(this.binding.contentView);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.bottom_container, chatBottomMainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void initlisten() {
        this.binding.back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });
        JMessageClient.registerEventReceiver(this);
        this.binding.lvChat.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (Chat.this.onBottomCancleListener != null) {
                    Chat.this.onBottomCancleListener.bottomCancle();
                }
                return false;
            }
        });
    }

    public void setOnBottomCancleListener(OnBottomCancleListener onBottomCancleListener) {
        this.onBottomCancleListener = onBottomCancleListener;
    }

    private void initConvListAdapter() {
        HashMap<Integer, Long> map = new HashMap();
        this.allMessage = this.mConversation.getAllMessage();
        for (int i = 0; i < this.allMessage.size(); i++) {
            map.put(Integer.valueOf(i), Long.valueOf(((Message) this.allMessage.get(i)).getCreateTime()));
        }
        chatListAdapter = new ChatListAdapter(getContext(), allMessage, 0, map);
        if (allMessage != null) {
            binding.lvChat.setAdapter(this.chatListAdapter);
            binding.lvChat.setSelection(this.chatListAdapter.getCount() - 1);
        }
        this.chatListAdapter.registerDataSetObserver(new DataSetObserver() {
            public void onChanged() {
                super.onChanged();
                binding.lvChat.setSelection(chatListAdapter.getCount() - 1);
            }
        });
    }

    public void onKeyDown() {
        if (this.onBottomCancleListener != null) {
            this.onBottomCancleListener.bottomCancle();
        }
        UserObservable.getInstance().notifyObservers(new EventData(5));
        if (this.mConversation != null) {
            this.mConversation.resetUnreadCount();
        }
        Presenter.getInstance().back();
    }

    public void onDestroy() {
        super.onDestroy();
        this.activity.hideStatusBar();
    }
}
