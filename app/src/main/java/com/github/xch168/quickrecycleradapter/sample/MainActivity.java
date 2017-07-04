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
        for (int i = 0; i < 100; i++) {
            mNewsAdapter.add(new News(i + ""));
        }


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mNewsAdapter);
    }
}
