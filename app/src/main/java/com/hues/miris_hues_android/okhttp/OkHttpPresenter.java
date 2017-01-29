package com.hues.miris_hues_android.okhttp;

import java.io.IOException;

/**
 * Created by secret on 1/27/17.
 */

public class OkHttpPresenter {
    public void run(String url) throws IOException {
        DataGetThread dataGetThread = new DataGetThread(url);
        dataGetThread.start();
    }
}
