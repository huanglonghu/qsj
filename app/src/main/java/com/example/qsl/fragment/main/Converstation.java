package com.example.qsl.fragment.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MyInfoUpdatedEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import com.example.qsl.R;
import com.example.qsl.adapter.ConservationAdapter;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.base.LazyLoadFragment;
import com.example.qsl.base.QSLApplication;
import com.example.qsl.bean.JGRegisterBody;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.ConversationBinding;
import com.example.qsl.fragment.chat.Chat;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.observable.UserObserver;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.LogUtil;
import java.util.ArrayList;
import java.util.List;

public class Converstation extends LazyLoadFragment {
    private ConversationBinding binding;
    private ConservationAdapter conservationAdapter;
    private ArrayList<Conversation> conversationList;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.conversation, container, false);
            setNeedReloadData(true);
            initView();
            jgLogin();
            initlisten();
        }
        if (JMessageClient.getMyInfo() != null) {
            initConversation();
        }
        return this.binding.getRoot();
    }

    public void initData() {
    }

    public void initView() {
        conversationList = new ArrayList();
        conservationAdapter = new ConservationAdapter(getContext(), conversationList, R.layout.list_conversation_item);
        binding.lvConversation.setAdapter(conservationAdapter);
        binding.lvConversation.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Conversation conversation = conversationList.get(position);
                Chat chat = new Chat();
                Bundle bundle = new Bundle();
                bundle.putString("converstaionName", conversation.getTitle());
                bundle.putString("converationId", conversation.getTargetId());
                chat.setArguments(bundle);
                Presenter.getInstance().step2fragment(chat, "chat");
            }
        });
    }

    public void initlisten() {
        JMessageClient.registerEventReceiver(this);
        UserObservable.getInstance().register(new UserObserver<EventData>() {
            public void onUpdate(UserObservable<EventData> userObservable, EventData data) {
                switch (data.getEventType()) {
                    case 1:
                        LogUtil.log("==============会话界面收到登录成功信息============");
                        jgLogin();
                        return;
                    case 4:
                        conservationAdapter.clearView();
                        conversationList.clear();
                        return;
                    case 5:
                        initConversation();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void jgLogin() {
        final UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            JMessageClient.login(userBean.getUserType() + "__" + userBean.getId(), "123456", new BasicCallback() {
                public void gotResult(int i, String s) {
                    LogUtil.log(i + "==============极光登录===============" + s);
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

    private void initConversation() {
        conversationList.clear();
        List<Conversation> list = JMessageClient.getConversationList();


        if (list != null) {
            conversationList.addAll(list);
            conservationAdapter.clearView();
        }
        if (conversationList.size() > 0) {
            binding.setIsConversationExit(true);
        }
        conservationAdapter.clearView();
        conservationAdapter.notifyDataSetChanged();
    }

    public void onEventMainThread(MessageEvent event) {
        LogUtil.log("=========会话列表收到消息=========");
        UserInfo userInfo = (UserInfo) event.getMessage().getTargetInfo();
        final String targetId = userInfo.getUserName();
        final Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
        if (conv != null) {
            binding.setIsConversationExit(true);
            boolean conversationExit = conservationAdapter.isConversationExit(conv.getTargetId());
            LogUtil.log(this.conservationAdapter + "==============会话更新==============" + conversationExit);
            if (conversationExit) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        int index = Converstation.this.conservationAdapter.getIndexByTargetId(targetId);
                        conversationList.set(index, conv);
                        conservationAdapter.refreshView(index, conv);
                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        conversationList.add(conv);
                        conservationAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }


    public void onEvent(ConversationRefreshEvent event){

        LogUtil.log("QQQ===========会话更新============="+event.getConversation().toJson());

    }


    @Override
    protected void loadData() {
        JMessageClient.init(QSLApplication.getApplication(),true);
        initConversation();
    }
}
