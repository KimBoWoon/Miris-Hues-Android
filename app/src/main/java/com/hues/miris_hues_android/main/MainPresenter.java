package com.hues.miris_hues_android.main;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.hues.miris_hues_android.okhttp.OkHttpPresenter;

import java.io.IOException;

/**
 * Created by secret on 1/27/17.
 */

public class MainPresenter implements MainContract.UserAction {
    private MainContract.View mMainView;
    private MainModel mMainModel;
    private OkHttpPresenter mOkHttp;
    public static boolean DEBUG = true;

    public MainPresenter(MainContract.View view) {
        this.mMainView = view;
        this.mMainModel = new MainModel();
        this.mOkHttp = new OkHttpPresenter();
    }

    public boolean isDebuggable(Context context) {
        boolean debuggable = false;
        PackageManager pm = context.getPackageManager();

        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /* debuggable variable will remain false */
            e.printStackTrace();
        }

        return debuggable;
    }

    @Override
    public void viewJsonString() {
        try {
            mOkHttp.run("http://miris-webapp.azurewebsites.net/tag");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
