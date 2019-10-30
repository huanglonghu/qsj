package com.example.qsl.fragment.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.BankCardMsg;
import com.example.qsl.bean.QrcodeMsg;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.databinding.LayoutQrcodeMsgBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.util.ImagUtil;
import com.example.qsl.util.LogUtil;

import java.util.List;

public class Qrcode extends BaseFragment {

    private LayoutQrcodeMsgBinding binding;
    private QrcodeMsg.DataBean data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_qrcode_msg, container, false);
        initData();
        initlisten();
        return binding.getRoot();
    }


    private int zfbQrId;

    private int wxQrId;

    @Override
    public void initData() {

        HttpUtil.getInstance().getAllQrcode().subscribe(
                str -> {
                    QrcodeMsg qrcodeMsg = GsonUtil.fromJson(str, QrcodeMsg.class);
                    data = qrcodeMsg.getData();
                    if (data != null) {
                        List<QrcodeMsg.DataBean.ItemsBean> qrCodes = data.getItems();
                        if (qrCodes != null && qrCodes.size() > 0) {
                            for (int i = 0; i < qrCodes.size(); i++) {
                                if (i < 2) {
                                    QrcodeMsg.DataBean.ItemsBean itemsBean = qrCodes.get(i);
                                    String describe = itemsBean.getDescribe();
                                    String picture = itemsBean.getPicture();
                                    String url = ImagUtil.handleUrl(picture);
                                    if (!TextUtils.isEmpty(url)) {
                                        if (describe.contains("支付宝")) {
                                            zfbQrId = itemsBean.getId();
                                            RxImageLoader.with(getContext()).load(url).into(binding.zfbQrcode, 1);
                                        } else if (describe.contains("微信")) {
                                            wxQrId = itemsBean.getId();
                                            RxImageLoader.with(getContext()).load(url).into(binding.wxQrcode, 1);
                                        }
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
        );


        HttpUtil.getInstance().getAllBank().subscribe(
                str -> {
                    BankCardMsg bankCardMsg = GsonUtil.fromJson(str, BankCardMsg.class);
                    int code = bankCardMsg.getCode();
                    if (code == 0) {
                        BankCardMsg.DataBean data = bankCardMsg.getData();
                        List<BankCardMsg.DataBean.ItemsBean> items = data.getItems();
                        if (items != null && items.size() > 0) {
                            BankCardMsg.DataBean.ItemsBean itemsBean = items.get(0);
                            binding.setBankBean(itemsBean);
                        }
                    }
                }
        );

    }

    @Override
    public void initView() {

    }



    @Override
    public void initlisten() {

    }
}
