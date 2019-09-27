package com.example.qsl.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.qsl.R;
import com.example.qsl.bean.ContactData;
import com.example.qsl.bean.ContactsResponse;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.database.entity.Friend;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.ItemContactsBinding;
import com.example.qsl.databinding.ItemFriendBinding;
import com.example.qsl.fragment.chat.Chat;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.ImagUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<ContactData> contactDatas;
    private Context context;
    private HashMap<Integer, View> viewMap = new HashMap<>();

    public ContactAdapter(Context context, List<ContactData> contactDatas) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.contactDatas = contactDatas;

    }


    public void initData(int position, ItemContactsBinding binding) {
        ContactData contactData = contactDatas.get(position);
        binding.character.setText(contactData.getCharcter());
        ArrayList<ContactsResponse.DataBean.ItemsBean> contacts = contactData.getContacts();
        for (int i = 0; i < contacts.size(); i++) {
            ContactsResponse.DataBean.ItemsBean itemsBean = contacts.get(i);
            String headImageUrl = itemsBean.getAvatar();
            String url = ImagUtil.handleUrl(headImageUrl);
            ItemFriendBinding itemFriendBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_friend, binding.friendContainer, false);
            itemFriendBinding.name.setText(itemsBean.getNickName());
            if (!TextUtils.isEmpty(url)) {
                RxImageLoader.with(context).getBitmap(url).subscribe(
                        imageBean -> {
                            Bitmap bitmap = imageBean.getBitmap();
                            Drawable drawable = ImagUtil.circle(context, bitmap);
                            itemFriendBinding.friendPhoto.setBackground(drawable);
                        }
                );
            } else {
                itemFriendBinding.friendPhoto.setBackgroundResource(R.drawable.contact_normal);
            }

            View convertView = itemFriendBinding.getRoot();
            UserBean userBean = UserOption.getInstance().querryUser();
            if (userBean != null) {
                int userType = userBean.getUserType();
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Chat chat = new Chat();
                        Bundle bundle = new Bundle();
                        bundle.putString("converstaionName", itemsBean.getNickName());
                        if (userType == 2) {
                            bundle.putString("converationId", 3 + "__" + itemsBean.getId());
                        }else if(userType == 3) {
                            bundle.putString("converationId", 2 + "__" + itemsBean.getId());
                        }

                        chat.setArguments(bundle);
                        Presenter.getInstance().step2fragment(chat, "chat");
                    }
                });
            }

            binding.friendContainer.addView(convertView);
        }


    }

    @Override
    public int getCount() {
        return contactDatas == null ? 0 : contactDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (viewMap.get(position) == null) {
            ItemContactsBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_contacts, parent, false);
            convertView = binding.getRoot();
            viewMap.put(position, convertView);
            initData(position, binding);
        }

        return viewMap.get(position);
    }

    public int getPositionByCharcter(String charcter) {

        for (int i = 0; i < contactDatas.size(); i++) {
            ContactData data = contactDatas.get(i);
            if (data.getCharcter().equals(charcter)) {
                return i;
            }
        }

        return -1;


    }


}
