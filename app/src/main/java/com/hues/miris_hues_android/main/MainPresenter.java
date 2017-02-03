package com.hues.miris_hues_android.main;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.hues.miris_hues_android.okhttp.JsonTagDataGetThread;
import com.hues.miris_hues_android.okhttp.JsonTextDataGetThread;

/**
 * Created by secret on 1/27/17.
 */

public class MainPresenter implements MainContract.UserAction {
    private MainContract.View mMainView;
    private MainModel mMainModel;
    public static boolean DEBUG = true;

    public MainPresenter(MainContract.View view) {
        this.mMainView = view;
        this.mMainModel = new MainModel();
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
    public void getJsonString(String keyword) {
        try {
            if (keyword.equals("tag")) {
                JsonTagDataGetThread jsonTagDataGetThread = new JsonTagDataGetThread("http://miris-webapp.azurewebsites.net/tag");
                jsonTagDataGetThread.start();
                jsonTagDataGetThread.join();
            } else if (keyword.equals("text")) {
                JsonTextDataGetThread jsonTextDataGetThread = new JsonTextDataGetThread("http://miris-webapp.azurewebsites.net/text");
                jsonTextDataGetThread.start();
                jsonTextDataGetThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
