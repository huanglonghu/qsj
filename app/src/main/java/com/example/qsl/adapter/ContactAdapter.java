package com.example.qsl.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.qsl.Interface.ClickSureListener;
import com.example.qsl.R;
import com.example.qsl.bean.ContactBean;
import com.example.qsl.bean.ContactData;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.ItemContactsBinding;
import com.example.qsl.databinding.ItemFriendBinding;
import com.example.qsl.fragment.chat.Chat;
import com.example.qsl.fragment.main.Customers;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.ImagUtil;
import com.example.qsl.widget.EditNameDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

public class ContactAdapter extends BaseAdapter {
    private List<ContactData> contactDatas;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private HashMap<Integer, View> viewMap = new HashMap();

    public ContactAdapter(Context context, List<ContactData> contactDatas) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.contactDatas = contactDatas;
    }

    public void initData(int position, ItemContactsBinding binding) {
        ContactData contactData = contactDatas.get(position);
        binding.character.setText(contactData.getCharcter());
        ArrayList<ContactBean> contacts = contactData.getContacts();
        UserBean userBean = UserOption.getInstance().querryUser();
        for (int i = 0; i < contacts.size(); i++) {
            ContactBean dataBean = contacts.get(i);
            String url = ImagUtil.handleUrl(dataBean.getAvatar());
            ItemFriendBinding itemFriendBinding =DataBindingUtil.inflate(this.mLayoutInflater, R.layout.item_friend, binding.friendContainer, false);
            itemFriendBinding.setUserType(userBean.getUserType());
            itemFriendBinding.setContactBean(dataBean);
            itemFriendBinding.setRemark.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new EditNameDialog(context, new ClickSureListener() {
                        @Override
                        public void click(String remark) {
                            HttpUtil.getInstance().setRemark(remark, dataBean.getId()).subscribe(
                                    str -> {
                                        JSONObject jb = new JSONObject(str);
                                        int code = jb.getInt("code");
                                        if (code == 0) {
                                            Toast.makeText(context, "备注修改成功", Toast.LENGTH_SHORT).show();
                                            dataBean.setNickName(remark);
                                            itemFriendBinding.setContactBean(dataBean);
                                        } else {
                                            String msg = jb.getString("msg");
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                        }
                    }).show();
                }
            });
            itemFriendBinding.transferUser.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Customers customers = new Customers();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", dataBean.getId());
                    customers.setArguments(bundle);
                    Presenter.getInstance().step2fragment(customers, "customers");
                }
            });
            if (TextUtils.isEmpty(url)) {
                itemFriendBinding.friendPhoto.setBackgroundResource(R.drawable.contact_normal);
            } else {
                RxImageLoader.with(this.context).getBitmap(url).subscribe(
                        imageBean -> {
                            itemFriendBinding.friendPhoto.setBackground(ImagUtil.circle(this.context, imageBean.getBitmap()));
                        }
                );
            }
            View convertView = itemFriendBinding.getRoot();

            if (userBean != null) {
                final int userType = userBean.getUserType();
                convertView.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Chat chat = new Chat();
                        Bundle bundle = new Bundle();
                        bundle.putString("converstaionName", dataBean.getNickName());
                        if (userType == 2) {
                            bundle.putString("converationId", "3__" + dataBean.getId());
                        } else if (userType == 3) {
                            bundle.putString("converationId", "2__" + dataBean.getId());
                        }
                        HttpUtil.getInstance().createChat(dataBean.getId()).subscribe();
                        chat.setArguments(bundle);
                        Presenter.getInstance().step2fragment(chat, "chat");
                    }
                });
            }
            binding.friendContainer.addView(convertView);
        }
    }


    public int getCount() {
        return contactDatas == null ? 0 : contactDatas.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (viewMap.get(position) == null) {
            ItemContactsBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_contacts, parent, false);
            viewMap.put(position, binding.getRoot());
            initData(position, binding);
        }
        return viewMap.get(position);
    }

    public int getPositionByCharcter(String charcter) {
        for (int i = 0; i < contactDatas.size(); i++) {
            if ((contactDatas.get(i)).getCharcter().equals(charcter)) {
                return i;
            }
        }
        return -1;
    }

    public void clearView() {
        viewMap.clear();
        contactDatas.clear();
        notifyDataSetChanged();
    }
}
