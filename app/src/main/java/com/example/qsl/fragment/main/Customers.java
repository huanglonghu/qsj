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

import com.example.qsl.Interface.ClickSureListener;
import com.example.qsl.R;
import com.example.qsl.adapter.CustomerAdapter;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.ContactBean;
import com.example.qsl.bean.UserResponse;
import com.example.qsl.databinding.CustomerBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.EventType;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.widget.TipDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Customers extends BaseFragment {
    private CustomerBinding binding;
    private ArrayList<ContactBean> datas;
    private CustomerAdapter customerAdapter;
    private TipDialog tipDialog;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.customer, container, false);
            initView();
            initlisten();
        }
        initData();
        return this.binding.getRoot();
    }

    public void initData() {
        HttpUtil.getInstance().getAllCustomers().subscribe(
                str -> {
                    initCustomerData(str);
                });

    }

    private void initCustomerData(String str) {
        UserResponse userResponse = GsonUtil.fromJson(str, UserResponse.class);
        if (userResponse.getCode() == 0) {
            UserResponse.DataBean data = userResponse.getData();
            if (data != null) {
                List<ContactBean> items = data.getItems();
                if (items != null && items.size() > 0) {
                    datas.clear();
                    customerAdapter.clearView();
                    datas.addAll(items);
                    customerAdapter.notifyDataSetChanged();
                }
            }
        }
    }


    public void initView() {
        datas = new ArrayList<>();
        customerAdapter = new CustomerAdapter(getContext(), datas);
        binding.lvContacts.setAdapter(customerAdapter);
        int userId = getArguments().getInt("id");
        binding.lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tipDialog = new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        int customerId = datas.get(position).getId();
                        HttpUtil.getInstance().transferUser(userId, customerId).subscribe(
                                str -> {
                                    JSONObject jb = new JSONObject(str);
                                    int code = jb.getInt("code");
                                    if (code == 0) {
                                        Toast.makeText(getContext(), "移交成功", Toast.LENGTH_SHORT).show();
                                        UserObservable.getInstance().notifyObservers(new EventData(EventType.EVENTTYPE_TRANSFER_SUCCESS));
                                        tipDialog.cancel();
                                        Presenter.getInstance().back();
                                    } else {
                                        String msg = jb.getString("msg");
                                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                    }

                                }
                        );
                    }
                }, "你确定要将客户移交给该客户吗?");
                tipDialog.show();

            }
        });
    }

    public void initlisten() {


    }
}
