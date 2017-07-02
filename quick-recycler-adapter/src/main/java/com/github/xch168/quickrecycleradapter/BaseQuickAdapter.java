package com.github.xch168.quickrecycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XuCanHui on 2017/7/2.
 */

public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<QuickViewHolder> {

    protected final Context context;

    protected final List<T> data;

    public BaseQuickAdapter(Context context) {
        this(context, null);
    }

    public BaseQuickAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : new ArrayList<>(data);
    }

    @Override
    public QuickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return QuickViewHolder.create(parent, getLayoutResId(viewType));
    }

    @Override
    public void onBindViewHolder(QuickViewHolder holder, int position) {

        convert(holder, data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elems) {
        data.addAll(elems);
        notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem) {
        set(data.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
        data.set(index, elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return data.contains(elem);
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    protected abstract int getLayoutResId(int viewType);

    protected abstract void convert(QuickViewHolder holder, T item);

}
