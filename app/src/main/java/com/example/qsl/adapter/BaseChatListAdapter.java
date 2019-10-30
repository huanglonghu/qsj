package com.example.qsl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.jpush.im.android.api.enums.ContentType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseChatListAdapter<T> extends BaseAdapter {
    public Context context;
    public List<T> datas;
    private LayoutInflater layoutInflater;
    private int res;
    public ArrayList<Object> selectList = new ArrayList();
    public HashMap<Integer, ContentType> typeMap = new HashMap();
    private HashMap<Integer, View> viewMap = new HashMap();

    protected abstract View initView(LayoutInflater layoutInflater, int i, List<T> list, int i2, ViewGroup viewGroup);

    public BaseChatListAdapter(Context context, List<T> datas, int res) {
        this.datas = datas;
        this.context = context;
        this.res = res;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public HashMap<Integer, View> getViewMap() {
        return this.viewMap;
    }

    public void setViewMap(HashMap<Integer, View> viewMap) {
        this.viewMap = viewMap;
    }

    public int getCount() {
        return this.datas == null ? 0 : this.datas.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.viewMap.get(Integer.valueOf(position)) == null) {
            this.viewMap.put(Integer.valueOf(position), initView(this.layoutInflater, this.res, this.datas, position, parent));
        }
        return (View) this.viewMap.get(Integer.valueOf(position));
    }

    public void selectAll(boolean select) {
        for (Integer intValue : this.viewMap.keySet()) {
            int i = intValue.intValue();
            setSelect((View) this.viewMap.get(Integer.valueOf(i)), select, i);
        }
    }

    public void setSelect(View view, boolean select, int position) {
    }

    public void clearView() {
        this.viewMap.clear();
    }

    public ArrayList<Object> getSelectList() {
        return this.selectList;
    }

    public void clearSelectList() {
        for (int i = 0; i < this.selectList.size(); i++) {
            this.viewMap.remove(Integer.valueOf(i));
        }
        this.selectList.clear();
    }
}
