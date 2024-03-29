package com.example.qsl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    public List<T> datas;
    public Context context;
    private HashMap<Integer, View> viewMap = new HashMap<>();
    private LayoutInflater layoutInflater;
    private int res;
    public ArrayList<Object> selectList = new ArrayList<>();

    public BaseListAdapter(Context context, List<T> datas, int res) {
        this.datas = datas;
        this.context = context;
        this.res = res;
        layoutInflater = LayoutInflater.from(context);
    }

    public HashMap<Integer, View> getViewMap() {
        return viewMap;
    }

    public void setViewMap(HashMap<Integer, View> viewMap) {
        this.viewMap = viewMap;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (viewMap.get(position) == null) {
            convertView = initView(layoutInflater, res, datas, position, parent);
            viewMap.put(position, convertView);
        }
        return viewMap.get(position);
    }

    protected abstract View initView(LayoutInflater layoutInflater, int res, List<T> datas, int position, ViewGroup parent);

    public void selectAll(boolean select) {
        for (int i : viewMap.keySet()) {
            View view = viewMap.get(i);
            setSelect(view, select, i);
        }
    }

    public void setSelect(View view, boolean select, int position) {
    }

    public void clearView() {
        viewMap.clear();
    }


    public ArrayList<Object> getSelectList() {
        return selectList;
    }

    public void clearSelectList() {
        for (int i = 0; i < selectList.size(); i++) {
            viewMap.remove(i);
        }
        selectList.clear();
    }

}
