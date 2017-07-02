package com.github.xch168.quickrecycleradapter.sample;

import android.content.Context;

import com.github.xch168.quickrecycleradapter.QuickAdapter;
import com.github.xch168.quickrecycleradapter.QuickViewHolder;

/**
 * Created by XuCanHui on 2017/7/2.
 */

public class NewsAdapter extends QuickAdapter<News> {


    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.news_item;
    }


    @Override
    protected void convert(QuickViewHolder holder, News item) {

        holder.setText(R.id.tv, item.getTitle());
    }
}
