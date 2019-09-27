package com.example.qsl.fragment.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.qsl.R;
import com.example.qsl.adapter.ContactAdapter;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.ContactData;
import com.example.qsl.bean.ContactsResponse;
import com.example.qsl.customview.LetterView;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.ContactsBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.EventType;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.observable.UserObserver;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Contacts extends BaseFragment {

    private ContactsBinding binding;
    private ContactAdapter contactAdapter;
    private ArrayList<ContactData> contactDatas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.contacts, container, false);
            initView();
            initData();
            initlisten();
        }
        return binding.getRoot();
    }


    @Override
    public void initData() {
        HashMap<String, ContactData> contactMap = new HashMap<>();
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            if (userBean.getUserType() == 2) {
                HttpUtil.getInstance().getAllCustomers().subscribe(
                        str -> {
                            initContactData(contactMap, str);
                        }
                );
            } else if (userBean.getUserType() == 3) {
                HttpUtil.getInstance().getAllUsers().subscribe(
                        str -> {
                            initContactData(contactMap, str);
                        }
                );

            }


        }


    }

    private void initContactData(HashMap<String, ContactData> contactMap, String str) {
        ContactsResponse contactsResponse = GsonUtil.fromJson(str, ContactsResponse.class);
        int code = contactsResponse.getCode();
        if (code == 0) {
            ContactsResponse.DataBean data = contactsResponse.getData();
            if (data != null) {
                List<ContactsResponse.DataBean.ItemsBean> items = data.getItems();
                if (items != null && items.size() > 0) {
                    for (int i = 0; i < items.size(); i++) {
                        ContactsResponse.DataBean.ItemsBean itemsBean = items.get(i);
                        String nickName = itemsBean.getNickName();
                        if (!TextUtils.isEmpty(nickName)) {
                            String firstChar = StringUtil.getPingYin(nickName).substring(0, 1);
                            if (!contactMap.containsKey(firstChar)) {
                                ContactData contactData = new ContactData();
                                ArrayList<ContactsResponse.DataBean.ItemsBean> contactList = new ArrayList<>();
                                contactList.add(itemsBean);
                                contactData.setCharcter(firstChar);
                                contactData.setContacts(contactList);
                                contactDatas.add(contactData);
                                contactMap.put(firstChar, contactData);
                            } else {
                                ContactData contactData = contactMap.get(firstChar);
                                ArrayList<ContactsResponse.DataBean.ItemsBean> contacts = contactData.getContacts();
                                contacts.add(itemsBean);
                            }

                        }
                    }
                    contactAdapter.notifyDataSetChanged();
                }

            }


        }
    }

    @Override
    public void initView() {
        contactDatas = new ArrayList<>();
        contactAdapter = new ContactAdapter(getContext(), contactDatas);
        binding.lvContacts.setAdapter(contactAdapter);
    }

    @Override
    public void initlisten() {

        binding.letterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                int position = contactAdapter.getPositionByCharcter(character.toLowerCase());
                if (position != -1) {
                    binding.lvContacts.setSelection(position);
                }
                Toast.makeText(getContext(), position + "===============" + character, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clickArrow() {

            }
        });

        UserObserver<EventData> userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {
                int eventType = data.getEventType();
                switch (eventType) {
                    case EventType.EVENTTYPE_LOGIN_SUCCESS:
                        initData();
                        break;
                }
            }
        };
        UserObservable.getInstance().register(userObserver);


    }
}
