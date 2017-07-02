package com.github.xch168.quickrecycleradapter.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mNewsAdapter = new NewsAdapter(this);
        mNewsAdapter.add(new News("1"));
        mNewsAdapter.add(new News("2"));
        mNewsAdapter.add(new News("3"));
        mNewsAdapter.add(new News("4"));
        mNewsAdapter.add(new News("5"));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mNewsAdapter);
    }
}
