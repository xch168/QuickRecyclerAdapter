package com.github.xch168.quickrecycleradapter.sample;

import android.content.Context;

import com.github.xch168.quickrecycleradapter.QuickAdapter;
import com.github.xch168.quickrecycleradapter.QuickViewHolder;

/**
 * Created by XuCanHui on 2017/7/2.
 */

public class GankAdapter extends QuickAdapter<Gank> {


    public GankAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.repo_item;
    }


    @Override
    protected void convert(QuickViewHolder holder, Gank item) {

        holder.setText(R.id.tv, item.desc);
    }
}
