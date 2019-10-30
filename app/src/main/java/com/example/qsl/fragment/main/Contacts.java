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
import com.example.qsl.base.LazyLoadFragment;
import com.example.qsl.bean.ContactBean;
import com.example.qsl.bean.ContactData;
import com.example.qsl.bean.UserResponse;
import com.example.qsl.customview.LetterView.CharacterClickListener;
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

public class Contacts extends LazyLoadFragment {
    private ContactsBinding binding;
    private ContactAdapter contactAdapter;
    private ArrayList<ContactData> contactDatas;
    private UserBean userBean;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.contacts, container, false);
            setNeedReloadData(true);
            initView();
            initData();
            initlisten();
        }
        return this.binding.getRoot();
    }

    public void initData() {
        HashMap<String, ContactData> contactMap = new HashMap();
        userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            if (userBean.getUserType() == 2) {
                HttpUtil.getInstance().getUserCustomer().subscribe(
                        str -> {
                            contactDatas.clear();
                            contactAdapter.clearView();
                            initUserData(contactMap, str);
                        }
                );
            } else if (userBean.getUserType() == 3){
                HttpUtil.getInstance().getAllUsers().subscribe(str ->{
                    contactDatas.clear();
                    contactAdapter.clearView();
                    initUserData(contactMap, str);
                });
            }
        }
    }


    private void initUserData(HashMap<String, ContactData> contactMap, String str) {
        UserResponse userResponse = GsonUtil.fromJson(str, UserResponse.class);
        if (userResponse.getCode() == 0) {
            UserResponse.DataBean data = userResponse.getData();
            if (data != null) {
                List<ContactBean> items = data.getItems();
                if (items != null && items.size() > 0) {
                    this.contactDatas.clear();
                    for (int i = 0; i < items.size(); i++) {
                        ContactBean itemsBean = items.get(i);
                        String name;
                        if (userBean.getUserType() == 2) {
                            name = itemsBean.getNickName();
                        } else {
                            name = itemsBean.getUserName();
                        }
                        if (!TextUtils.isEmpty(name)) {
                            String firstChar = StringUtil.getPingYin(name).substring(0, 1);
                            if (contactMap.containsKey(firstChar)) {
                                ((ContactData) contactMap.get(firstChar)).getContacts().add(itemsBean);
                            } else {
                                ContactData contactData = new ContactData();
                                ArrayList<ContactBean> contactList = new ArrayList();
                                contactList.add(itemsBean);
                                contactData.setCharcter(firstChar);
                                contactData.setContacts(contactList);
                                this.contactDatas.add(contactData);
                                contactMap.put(firstChar, contactData);
                            }
                        }
                    }
                    this.contactAdapter.notifyDataSetChanged();
                }
            }
        }
    }


    public void initView() {
        this.contactDatas = new ArrayList();
        this.contactAdapter = new ContactAdapter(getContext(), contactDatas);
        this.binding.lvContacts.setAdapter(this.contactAdapter);
    }

    public void initlisten() {


        this.binding.letterView.setCharacterListener(new CharacterClickListener() {
            public void clickCharacter(String character) {
                int position = Contacts.this.contactAdapter.getPositionByCharcter(character.toLowerCase());
                if (position != -1) {
                    Contacts.this.binding.lvContacts.setSelection(position);
                }
                Toast.makeText(Contacts.this.getContext(), character, Toast.LENGTH_SHORT).show();
            }

            public void clickArrow() {
            }
        });
        UserObservable.getInstance().register(new UserObserver<EventData>() {
            public void onUpdate(UserObservable<EventData> userObservable, EventData data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (data.getEventType()) {
                            case 1:
                                initData();
                                return;
                            case 4:
                                contactAdapter.clearView();
                                return;
                            case EventType.EVENTTYPE_TRANSFER_SUCCESS:
                                initData();
                                break;
                        }
                    }
                });
            }
        });
    }



    @Override
    protected void loadData() {
        initData();
    }
}
