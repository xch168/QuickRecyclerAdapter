package com.github.xch168.quickrecycleradapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by XuCanHui on 2017/7/2.
 */

public class QuickViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;

    private final Context context;

    private int position;

    private View convertView;

    Object associatedObject;

    public QuickViewHolder(Context context, View convertView) {
        super(convertView);

        this.context = context;
        this.convertView = convertView;
        this.views = new SparseArray<>();
    }

    public static QuickViewHolder create(ViewGroup parent, int layoutResId) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        return new QuickViewHolder(parent.getContext(), convertView);
    }

    public View getView() {
        return convertView;
    }

    public <T extends View> T getView(int viewId) {
        return retrieveView(viewId);
    }

    public QuickViewHolder setText(int viewId, CharSequence value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }

    public QuickViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = retrieveView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public QuickViewHolder setBackgroundColor(int viewId, int color) {
        View view = retrieveView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public QuickViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = retrieveView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public QuickViewHolder setTextColor(int viewId, int textColor) {
        TextView view = retrieveView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public QuickViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = retrieveView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }

    public QuickViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = retrieveView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public QuickViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = retrieveView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public QuickViewHolder setAlpha(int viewId, float value) {
        retrieveView(viewId).setAlpha(value);
        return this;
    }

    public QuickViewHolder setVisible(int viewId, boolean visible) {
        View view = retrieveView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public QuickViewHolder linkify(int viewId) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public QuickViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView view = retrieveView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public QuickViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            setTypeface(viewId, typeface);
        }
        return this;
    }

    public QuickViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = retrieveView(viewId);
        view.setProgress(progress);
        return this;
    }

    public QuickViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        return this;
    }

    public QuickViewHolder setMax(int viewId, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        return this;
    }

    public QuickViewHolder setRating(int viewId, float rating) {
        RatingBar view = retrieveView(viewId);
        view.setRating(rating);
        return this;
    }

    public QuickViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = retrieveView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}
