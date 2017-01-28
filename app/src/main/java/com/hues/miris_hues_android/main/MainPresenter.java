package com.hues.miris_hues_android.main;

/**
 * Created by secret on 1/27/17.
 */

public class MainPresenter implements MainContract.UserAction {
    private MainContract.View mMainView;
    private MainModel mMainModel;

    public MainPresenter(MainContract.View view) {
        this.mMainView = view;
        this.mMainModel = new MainModel();
    }

    @Override
    public void viewJsonString() {

    }
}
