package com.github.xch168.quickrecycleradapter;

/**
 * Created by XuCanHui on 2017/7/7.
 */

public class QuickLoadMoreView extends BaseLoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.quick_load_more_view;
    }

    @Override
    public int getLoadingViewId() {
        return R.id.loading_view;
    }

    @Override
    public int getLoadFailViewId() {
        return R.id.load_fail_view;
    }

    @Override
    public int getLoadEndViewId() {
        return R.id.load_end_view;
    }
}
