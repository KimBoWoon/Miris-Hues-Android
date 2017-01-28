package com.hues.miris_hues_android.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hues.miris_hues_android.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        mMainPresenter = new MainPresenter(this);
    }
}
