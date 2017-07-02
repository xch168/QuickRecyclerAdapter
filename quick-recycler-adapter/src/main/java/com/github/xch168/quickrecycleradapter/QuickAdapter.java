package com.github.xch168.quickrecycleradapter;

import android.content.Context;

import java.util.List;

/**
 * Created by XuCanHui on 2017/7/2.
 */

public abstract class QuickAdapter<T> extends BaseQuickAdapter<T> {

    public QuickAdapter(Context context) {
        super(context);
    }

    public QuickAdapter(Context context, List<T> data) {
        super(context, data);
    }

}
