package com.github.xch168.quickrecycleradapter.sample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.xch168.quickrecycleradapter.BaseQuickAdapter;
import com.github.xch168.quickrecycleradapter.DividerGridItemDecoration;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnLoadMoreListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GankAdapter mGankAdapter;

    private Retrofit retrofit;
    private GankService service;

    private static final int PAGE_SIZE = 20;
    private int pageNum = 1;

    private static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        initRetrofit();

        loadData(pageNum);
    }

    private void initUI() {
        mSwipeRefreshLayout = findViewById(R.id.srl);
        mRecyclerView = findViewById(R.id.recycler_view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mGankAdapter = new GankAdapter(this);
        mGankAdapter.setLoadMoreEnable(true);
        mGankAdapter.setOnLoadMoreListener(this);
        mGankAdapter.setOnEmptyViewClickListener(R.id.btn_refresh, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });

        Drawable dividerHorizontal = getDrawable(R.drawable.divider_horizontal);
        Drawable dividerVertical = getDrawable(R.drawable.divider_vertical);
        DividerItemDecoration itemDecorationHorizontal = new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL);
        DividerItemDecoration itemDecorationVertical = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecorationHorizontal.setDrawable(dividerHorizontal);
        itemDecorationVertical.setDrawable(dividerVertical);
        //mRecyclerView.addItemDecoration(itemDecorationHorizontal);
        //mRecyclerView.addItemDecoration(itemDecorationVertical);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mGankAdapter);
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(GankService.class);
    }

    private void loadData(final int pageNum) {

        service.listGank(pageNum, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankData gankData) {
                        if (pageNum == 1) {
                            mGankAdapter.clear();
                        }

                        if (!flag) {
                            gankData.gankList.clear();
                            flag = true;
                        }

                        mGankAdapter.addAll(gankData.gankList);
                        if (gankData.gankList.isEmpty() || gankData.gankList.size() < PAGE_SIZE) {
                            mGankAdapter.setLoadMoreEnd();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (pageNum > 1) {
                            mGankAdapter.setLoadMoreFail();
                        }
                    }

                    @Override
                    public void onComplete() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {

        pageNum = 1;
        loadData(pageNum);
    }

    @Override
    public void onLoadMore() {

        loadData(++pageNum);
    }
}
