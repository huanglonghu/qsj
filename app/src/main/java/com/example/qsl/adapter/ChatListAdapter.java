package com.example.qsl.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.qsl.R;
import com.example.qsl.bean.ImageBean;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.databinding.ListChatReceiveImgItemBinding;
import com.example.qsl.databinding.ListChatReceivemsgItemBinding;
import com.example.qsl.databinding.ListChatSendImgItemBinding;
import com.example.qsl.databinding.ListChatSendmsgItemBinding;
import com.example.qsl.fragment.chat.EmotionUtils;
import com.example.qsl.fragment.chat.SpanStringUtils;
import com.example.qsl.util.ImagUtil;
import com.example.qsl.util.TimeUtil;
import com.example.qsl.widget.BigImg;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.enums.MessageStatus;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class ChatListAdapter extends BaseChatListAdapter {

    private LayoutInflater inflater;
    private HashMap<Integer, Long> dateMap;

    public ChatListAdapter(Context context, List datas, int res, HashMap dateMap) {
        super(context, datas, res);
        this.dateMap = dateMap;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        Message msg = (Message) datas.get(position);
        MessageDirect direct = msg.getDirect();
        MessageContent content = msg.getContent();
        long createTime = msg.getCreateTime();
        ContentType contentType = content.getContentType();
        typeMap.put(position, contentType);
        if (dateMap.get(position) == null) {
            dateMap.put(position, createTime);
        }
        View view = null;
        if (contentType == ContentType.text) {
            TextContent textContent = (TextContent) content;
            String text = textContent.getText();
            if (direct == MessageDirect.send) {
                ListChatSendmsgItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_sendmsg_item, parent, false);
                setDate(binding.date, createTime, position);
                initHead(binding.ivHead, msg);
                binding.content.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        binding.content.selectAll();
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(binding.content.getText().toString());
                        Toast.makeText(context, "已复制", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                view = binding.getRoot();
                binding.content.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE,
                        context, binding.content, text));
            } else if (direct == MessageDirect.receive) {
                ListChatReceivemsgItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_receivemsg_item, parent, false);
                binding.content.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE,
                        context, binding.content, text));
                setDate(binding.date, createTime, position);
                initHead(binding.ivHead, msg);
                view = binding.getRoot();
            }
        } else if (contentType == ContentType.image) {
            ImageContent imageContent = (ImageContent) content;
            if (direct == MessageDirect.send) {
                ListChatSendImgItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_send_img_item, parent, false);
                view = binding.getRoot();
                String localThumbnailPath = imageContent.getLocalThumbnailPath();
                Bitmap bitmap = BitmapFactory.decodeFile(localThumbnailPath);
                binding.img.setImageBitmap(bitmap);
                imageContent.downloadOriginImage(msg, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        String path = file.getPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        binding.img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BigImg bigImg = new BigImg(context, bitmap);
                                bigImg.showAtLocation(v, Gravity.CENTER, 0, 0);
                            }
                        });
                    }
                });

                setDate(binding.date, createTime, position);
                initHead(binding.ivHead, msg);
            } else if (direct == MessageDirect.receive) {
                ListChatReceiveImgItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_receive_img_item, parent, false);
                view = binding.getRoot();
                imageContent.downloadOriginImage(msg, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        String path = file.getPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        binding.img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BigImg bigImg = new BigImg(context, bitmap);
                                bigImg.showAtLocation(v, Gravity.CENTER, 0, 0);
                            }
                        });
                    }
                });
                imageContent.downloadThumbnailImage(msg, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        String path = file.getPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        binding.img.setImageBitmap(bitmap);
                    }
                });
                setDate(binding.date, createTime, position);
                initHead(binding.ivHead, msg);
            }
        }
        View finalView = view;
        if (direct == MessageDirect.send) {
            ProgressBar progressBar = finalView.findViewById(R.id.img_loading);
            if (msg.getStatus() == MessageStatus.send_success) {
                progressBar.setVisibility(View.GONE);
            }
            msg.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {

                    if (i == 0) {
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Drawable drawable = context.getResources().getDrawable(R.drawable.error);
                        progressBar.setIndeterminateDrawable(null);
                        progressBar.setBackground(drawable);
                    }
                }
            });
        }
        return view;
    }


    private void setDate(TextView tv, long createTime, int position) {
        if (position == 0) {
            String time = TimeUtil.formatTime3(createTime);
            tv.setText(time);
        } else {
            Long crateTime2 = dateMap.get(position - 1);
            long l = (createTime - crateTime2) / 1000;
            if (l >= 120) {
                String time = TimeUtil.formatTime3(createTime);
                tv.setText(time);
            } else {
                tv.setVisibility(View.GONE);
            }
        }

    }


    private void initHead(ImageView imageView, Message msg) {
        String fromID = msg.getFromID();
        Bitmap bitmap = RxImageLoader.with(context).getBitmapFromMemory(fromID);
        if (bitmap == null) {
            JMessageClient.getUserInfo(msg.getFromID(), new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                        @Override
                        public void gotResult(int i, String s, Bitmap bitmap) {
                            if (bitmap != null) {
                                ImageBean imageBean = new ImageBean(bitmap, msg.getFromID());
                                RxImageLoader.with(context).catche(imageBean);
                                Drawable drawable = ImagUtil.circle(context, bitmap);
                                imageView.setBackground(drawable);
                            } else {
                                imageView.setBackgroundResource(R.drawable.default_chat_head);
                            }
                        }
                    });
                }
            });
        } else {
            Drawable drawable = ImagUtil.circle(context, bitmap);
            imageView.setBackground(drawable);
        }

    }


    public void refreshMessageState(int position, MessageStatus messageStatus) {

        if (messageStatus == MessageStatus.send_success) {
            View view = getView(position, null, null);

            ProgressBar pb = view.findViewById(R.id.img_loading);

            pb.setVisibility(View.GONE);
        } else if (messageStatus == MessageStatus.send_fail) {

        }

    }


}
