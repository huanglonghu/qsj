package com.example.qsl.fragment.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.qsl.R;
import com.example.qsl.adapter.ConservationAdapter;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.JGRegisterBody;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.ConversationBinding;
import com.example.qsl.fragment.chat.Chat;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.EventType;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.observable.UserObserver;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.LogUtil;
import java.util.ArrayList;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class Converstation extends BaseFragment {

    private ConversationBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.conversation, container, false);
            conversationList = new ArrayList<>();
            jgLogin();
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void initlisten() {
        binding.lvConversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat chat = new Chat();
                Presenter.getInstance().step2fragment(chat, "chat");
            }
        });

        UserObserver<EventData> userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {
                switch (data.getEventType()) {
                    case EventType.EVENTTYPE_LOGIN_SUCCESS:
                        jgLogin();
                        break;
                }

            }
        };
        UserObservable.getInstance().register(userObserver);
        JMessageClient.registerEventReceiver(this);

    }

    private void jgLogin() {
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            JMessageClient.login(userBean.getUserType() + "__" + userBean.getId(), "123456", new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    LogUtil.log(i + "==============极光登录===============" + s);
                    Toast.makeText(getContext(), "登录" + s, Toast.LENGTH_SHORT).show();
                    if (i == 801003) {
                        JGRegisterBody jgRegisterBody = new JGRegisterBody();
                        jgRegisterBody.setUserId(userBean.getId().intValue());
                        jgRegisterBody.setUserName(userBean.getUserName());
                        jgRegisterBody.setUserType(userBean.getUserType());
                        HttpUtil.getInstance().registerJG(jgRegisterBody).subscribe(
                                str -> {
                                    jgLogin();
                                }
                        );

                    } else if (i == 0) {
                        initConversation();
                    }
                }
            });
        }
    }

    private ConservationAdapter conservationAdapter;
    private ArrayList<Conversation> conversationList;

    private void initConversation() {
        conversationList.clear();
        conversationList.addAll(JMessageClient.getConversationList());
        LogUtil.log(conservationAdapter + "==============converationList==============" + conversationList.size());
        if (conservationAdapter == null) {
            conservationAdapter = new ConservationAdapter(getContext(), conversationList, R.layout.list_conversation_item);
            binding.lvConversation.setAdapter(conservationAdapter);
            binding.setCount(conversationList.size());
            binding.lvConversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Conversation conversation = conversationList.get(position);
                    Chat chat = new Chat();
                    Bundle bundle = new Bundle();
                    bundle.putString("converstaionName", conversation.getTitle());
                    bundle.putString("converationId", conversation.getTargetId());
                    chat.setArguments(bundle);
                    Presenter.getInstance().step2fragment(chat, "chat");
                }
            });
        } else {
            conservationAdapter.clearView();
            conservationAdapter.notifyDataSetChanged();
        }
    }


    public void onEventMainThread(MessageEvent event) {
        Message msg = event.getMessage();
        UserInfo userInfo = (UserInfo) msg.getTargetInfo();
        String targetId = userInfo.getUserName();
        Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
        if (conv != null) {
            boolean conversationExit = conservationAdapter.isConversationExit(conv.getTargetId());
            LogUtil.log(conservationAdapter + "==============会话更新==============" + conversationExit);
            if (conversationExit) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int index = conservationAdapter.getIndexByTargetId(targetId);
                        conversationList.set(index, conv);
                        conservationAdapter.refreshView(index, conv);
                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        conversationList.add(conv);
                        conservationAdapter.notifyDataSetChanged();
                    }
                });
            }

        }


    }


}
