package com.hues.miris_hues_android.main;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hues.miris_hues_android.data.Constant;
import com.hues.miris_hues_android.data.SharedStore;
import com.hues.miris_hues_android.log.Logging;
import com.hues.miris_hues_android.thread.AzureBlobStorageThread;
import com.hues.miris_hues_android.thread.JsonTagDataGetThread;
import com.hues.miris_hues_android.thread.JsonTextDataGetThread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by secret on 1/27/17.
 */

public class MainPresenter implements MainContract.UserAction {
    private MainContract.View mMainView;
    private MainModel mMainModel;
    private FirebaseRemoteConfig config;
    public static boolean DEBUG = true;

    public MainPresenter(MainContract.View view) {
        this.mMainView = view;
        this.mMainModel = new MainModel();

        initFirebase();
        getAzureStorage();
    }

    private void initFirebase() {
        // 1. config 획득
        config = FirebaseRemoteConfig.getInstance();
        // 2. setting 획득
        FirebaseRemoteConfigSettings configSettings
                = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();

        // 3. 설정
        config.setConfigSettings(configSettings);
        // 4. data 획득
        loadRemoteData();
    }

    public void loadRemoteData() {
        // 리모트로 가져오는 시간
        long cacheException = 3600;
        if (config.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheException = 0;
        }

        // 원격 매개변수 요청
        config.fetch(cacheException).addOnCompleteListener((MainActivity) mMainView, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    config.activateFetched();

                    // 원격 매개변수 데이터 저장
                    // 늦게 저장이 되더라도 한번 저장되면 앱 구동시 즉각 반응 가능하다.
                    SharedStore.setString((MainActivity) mMainView,
                            Constant.AZURE_STORAGE_CONNECTION_STRING,
                            config.getString(Constant.AZURE_STORAGE_CONNECTION_STRING));
                    SharedStore.setString((MainActivity) mMainView,
                            Constant.AZURE_ACCOUNT_NAME,
                            config.getString(Constant.AZURE_ACCOUNT_NAME));
                    SharedStore.setString((MainActivity) mMainView,
                            Constant.AZURE_ACCOUNT_KEY,
                            config.getString(Constant.AZURE_ACCOUNT_KEY));
                    SharedStore.setBooolean((MainActivity) mMainView,
                            Constant.APP_DEBUG_MODE,
                            config.getBoolean(Constant.APP_DEBUG_MODE));

                    Logging.i(String.valueOf(SharedStore.getString((MainActivity) mMainView, "AZURE_STORAGE_CONNECTION_STRING")));
                    Logging.i(String.valueOf(SharedStore.getString((MainActivity) mMainView, "AZURE_ACCOUNT_NAME")));
                    Logging.i(String.valueOf(SharedStore.getString((MainActivity) mMainView, "AZURE_ACCOUNT_KEY")));
                    Logging.i(String.valueOf(SharedStore.getBoolean((MainActivity) mMainView, "APP_DEBUG_MODE")));
                }
            }
        });
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

    public void getAzureStorage() {
        try {
            AzureBlobStorageThread azureBlobStorageThread = new AzureBlobStorageThread(SharedStore.getString((MainActivity) mMainView, Constant.AZURE_STORAGE_CONNECTION_STRING));
            azureBlobStorageThread.start();
            azureBlobStorageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
