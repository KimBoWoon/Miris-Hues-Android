package com.hues.miris_hues_android.okhttp;

/**
 * Created by secret on 1/27/17.
 */

public class OkHttpPresenter {
    public void run(String url) {
        try {
            DataGetThread dataGetThread = new DataGetThread(url);
            dataGetThread.start();
            dataGetThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
