package com.hues.miris_hues_android.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.hues.miris_hues_android.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainPresenter mMainPresenter;
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public void initView() {
        mMainPresenter = new MainPresenter(this);
        aq = new AQuery(this);

        mMainPresenter.getJsonString();

        aq.id(R.id.main_text).text("Hello, World!");
    }
}
