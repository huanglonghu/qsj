package com.example.qsl.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.qsl.R;
import com.example.qsl.bean.ContactBean;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.databinding.ItemCustomerBinding;
import com.example.qsl.util.ImagUtil;

import java.util.HashMap;
import java.util.List;

public class CustomerAdapter extends BaseAdapter {
    private List<ContactBean> contactDatas;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private HashMap<Integer, View> viewMap = new HashMap();

    public CustomerAdapter(Context context, List<ContactBean> contactDatas) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.contactDatas = contactDatas;
    }


    public int getCount() {
        return this.contactDatas == null ? 0 : this.contactDatas.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.viewMap.get(Integer.valueOf(position)) == null) {
            ItemCustomerBinding binding = DataBindingUtil.inflate(this.mLayoutInflater, R.layout.item_customer, parent, false);
            ContactBean contactBean = contactDatas.get(position);
            binding.name.setText(contactBean.getNickName());
            String avatar = contactBean.getAvatar();
            String url = ImagUtil.handleUrl(avatar);
            if (TextUtils.isEmpty(url)) {
                binding.friendPhoto.setBackgroundResource(R.drawable.contact_normal);
            } else {
                RxImageLoader.with(this.context).getBitmap(url).subscribe(
                        imageBean -> {
                            binding.friendPhoto.setBackground(ImagUtil.circle(this.context, imageBean.getBitmap()));
                        }
                );
            }

            viewMap.put(Integer.valueOf(position), binding.getRoot());

        }
        return (View) this.viewMap.get(Integer.valueOf(position));
    }


    public void clearView() {
        this.viewMap.clear();
        this.contactDatas.clear();
        notifyDataSetChanged();
    }
}
