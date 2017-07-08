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

    private static final int TYPE_LOAD_MORE_VIEW = 1;


    protected final Context context;

    protected final List<T> data;

    private BaseLoadMoreView mLoadMoreView;

    public BaseQuickAdapter(Context context) {
        this(context, null);
    }

    public BaseQuickAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.mLoadMoreView = getLoadMoreView();
    }

    @Override
    public QuickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId;
        switch (viewType) {
            case TYPE_LOAD_MORE_VIEW:
                layoutId = mLoadMoreView.getLayoutId();
                break;
            default:
                layoutId = getLayoutResId(viewType);
                break;
        }
        return QuickViewHolder.create(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(QuickViewHolder holder, int position) {

        int viewType = holder.getItemViewType();
        switch (viewType) {

            case TYPE_LOAD_MORE_VIEW:
                mLoadMoreView.convert(holder);
                break;
            default:
                convert(holder, data.get(position));
                break;
        }

    }

    protected BaseLoadMoreView getLoadMoreView() {
        return new QuickLoadMoreView();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_LOAD_MORE_VIEW;
        }
        return super.getItemViewType(position);
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

    public void setLoadMoreEnd() {
        mLoadMoreView.setLoadMoreStatus(BaseLoadMoreView.STATUS_END);
        notifyItemChanged(getItemCount() - 1);
    }

    public void setLoadMoreFail() {
        mLoadMoreView.setLoadMoreStatus(BaseLoadMoreView.STATUS_FAIL);
        notifyItemChanged(getItemCount() - 1);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreView.setOnLoadMoreListener(listener);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
