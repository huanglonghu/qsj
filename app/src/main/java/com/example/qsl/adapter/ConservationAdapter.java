package com.example.qsl.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

import com.example.qsl.R;
import com.example.qsl.bean.ImageBean;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.databinding.ListConversationItemBinding;
import com.example.qsl.util.ImagUtil;
import com.example.qsl.util.LogUtil;
import com.example.qsl.util.TimeUtil;

import java.util.HashMap;
import java.util.List;

public class ConservationAdapter extends BaseListAdapter {
    private HashMap<String, Integer> cvMap = new HashMap();

    public ConservationAdapter(Context context, List<Conversation> datas, int res) {
        super(context, datas, res);
    }

    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ListConversationItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_conversation_item, parent, false);
        Conversation conversation = (Conversation) datas.get(position);
        binding.setConversation(conversation);
        cvMap.put(conversation.getTargetId(), position);
        initConversation(binding, conversation);
        return binding.getRoot();
    }

    private void initConversation(ListConversationItemBinding binding, Conversation conversation) {
        binding.date.setText(TimeUtil.formatTime3(conversation.getLastMsgDate()));

        ContentType contentType = conversation.getLatestType();
        String targetId = conversation.getTargetId();

        JMessageClient.getUserInfo(targetId, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                binding.name.setText(userInfo.getNickname());
            }
        });

        if (contentType == ContentType.voice) {
            binding.content.setText("[声音]");
        } else if (contentType == ContentType.image) {
            binding.content.setText("[图片]");
        } else if (contentType == ContentType.text) {
            binding.content.setText(conversation.getLatestText());
        } else if (contentType == ContentType.custom) {
            binding.content.setText("[图文]");
        }
        initHead(binding.head, conversation.getTargetId());
    }

    public boolean isConversationExit(String target_id) {
        return this.cvMap.containsKey(target_id);
    }

    public int getIndexByTargetId(String target_id) {
        return (cvMap.get(target_id)).intValue();
    }

    public void refreshView(int index, Conversation cv) {
        ListConversationItemBinding binding = DataBindingUtil.findBinding(getView(index, null, null));
        binding.setConversation(cv);
        initConversation(binding, cv);
    }

    private void initHead(final ImageView imageView, final String id) {
        Bitmap bitmap = RxImageLoader.with(this.context).getBitmapFromMemory(id);
        if (bitmap == null) {
            JMessageClient.getUserInfo(id, new GetUserInfoCallback() {
                public void gotResult(int i, String s, UserInfo userInfo) {
                    userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                        public void gotResult(int i, String s, Bitmap bitmap) {
                            if (bitmap != null) {
                                RxImageLoader.with(ConservationAdapter.this.context).catche(new ImageBean(bitmap, id));
                                imageView.setBackground(ImagUtil.circle(ConservationAdapter.this.context, bitmap));
                                return;
                            }
                            imageView.setBackgroundResource(R.drawable.default_chat_head);
                        }
                    });
                }
            });
        } else {
            imageView.setBackground(ImagUtil.circle(this.context, bitmap));
        }
    }


}
