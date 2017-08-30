package com.github.xch168.quickrecycleradapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XuCanHui on 2017/7/2.
 */

public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<QuickViewHolder> {

    private static final int TYPE_LOAD_MORE_VIEW = 1;
    private static final int TYPE_EMPTY_VIEW = 2;


    protected final Context context;

    protected final List<T> data;

    // load more view
    private BaseLoadMoreView mLoadMoreView;
    private boolean mLoadMoreEnable = false;

    // empty view
    private int mEmptyViewLayoutId;
    private int mEmptyClickableViewId;
    private View.OnClickListener mOnEmptyViewClickListener;

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
            case TYPE_EMPTY_VIEW:
                layoutId = getEmptyViewLayoutId();
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
            case TYPE_EMPTY_VIEW:
                View clickableView = holder.retrieveView(getEmptyViewClickableViewId());
                if (mOnEmptyViewClickListener != null &&  clickableView != null) {
                    clickableView.setOnClickListener(mOnEmptyViewClickListener);
                }
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
        if (position > 0 && position == getLoadMoreViewPosition()) {
            return TYPE_LOAD_MORE_VIEW;
        } else if (getEmptyViewLayoutId() > 0 && data.isEmpty()) {
            return TYPE_EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (mLoadMoreEnable && !data.isEmpty()) {
            return data.size() + 1;
        } else if (getEmptyViewLayoutId() > 0 && data.isEmpty()) {
            return 1;
        }
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup oldSpanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (viewType == TYPE_LOAD_MORE_VIEW) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    if (oldSpanSizeLookup != null) {
                        return oldSpanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
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

    public void setEmptyViewLayoutId(int layoutId) {
        mEmptyViewLayoutId = layoutId;
    }

    protected int getEmptyViewLayoutId() {
        return mEmptyViewLayoutId;
    }

    public void setOnEmptyViewClickListener(int clickableViewId, View.OnClickListener listener) {
        mEmptyClickableViewId = clickableViewId;
        mOnEmptyViewClickListener = listener;
    }

    protected int getEmptyViewClickableViewId() {
        return mEmptyClickableViewId;
    }

    public void setLoadMoreEnd() {
        mLoadMoreView.setLoadMoreStatus(BaseLoadMoreView.STATUS_END);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public void setLoadMoreFail() {
        mLoadMoreView.setLoadMoreStatus(BaseLoadMoreView.STATUS_FAIL);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public int getLoadMoreViewPosition() {
        return data.size();
    }

    public void setLoadMoreEnable(boolean enable) {
        mLoadMoreEnable = enable;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreView.setOnLoadMoreListener(listener);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
