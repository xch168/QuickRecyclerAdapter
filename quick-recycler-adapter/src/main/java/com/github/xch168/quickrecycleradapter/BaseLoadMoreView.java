package com.github.xch168.quickrecycleradapter;


import android.view.View;

/**
 * Created by XuCanHui on 2017/7/7.
 */

public abstract class BaseLoadMoreView implements View.OnClickListener {

    public static final int STATUS_LOADING = 1;
    public static final int STATUS_FAIL = 2;
    public static final int STATUS_END = 3;

    private int mLoadMoreStatus = STATUS_LOADING;

    private BaseQuickAdapter.OnLoadMoreListener mOnLoadMoreListener;

    private QuickViewHolder mHolder;

    public void convert(QuickViewHolder holder) {
        mHolder = holder;
        switch (mLoadMoreStatus) {
            case STATUS_LOADING:
                holder.setVisible(R.id.loading_view, true);
                holder.setVisible(R.id.load_fail_view, false);
                holder.setVisible(R.id.load_end_view, false);
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoadMore();
                }
                break;
            case STATUS_FAIL:
                holder.setVisible(R.id.loading_view, false);
                holder.setVisible(R.id.load_fail_view, true);
                holder.setVisible(R.id.load_end_view, false);
                holder.setOnClickListener(R.id.load_fail_view, this);
                break;
            case STATUS_END:
                holder.setVisible(R.id.loading_view, false);
                holder.setVisible(R.id.load_fail_view, false);
                holder.setVisible(R.id.load_end_view, true);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        mLoadMoreStatus = STATUS_LOADING;
        convert(mHolder);
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public abstract int getLayoutId();

    public abstract int getLoadingViewId();

    public abstract int getLoadFailViewId();

    public abstract int getLoadEndViewId();

    public void setOnLoadMoreListener(BaseQuickAdapter.OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }
}
