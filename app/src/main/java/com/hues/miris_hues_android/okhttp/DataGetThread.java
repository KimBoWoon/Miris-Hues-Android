package com.hues.miris_hues_android.okhttp;

import android.os.Handler;
import android.os.Message;

import com.hues.miris_hues_android.log.Loging;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by secret on 1/29/17.
 */

public class DataGetThread extends Thread {
    private String url;

    public DataGetThread(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        super.run();

        OkHttpClient mClient = new OkHttpClient();

        try {
            Request request = new Request.Builder().url(url).build();
            Response response = mClient.newCall(request).execute();

            while (true) {
                if (response.isSuccessful()) {
                    Loging.i(response.body().string());
                    handler.sendEmptyMessage(0);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {   // Message id 가 0 이면
                Loging.i("Success");
            }
        }
    };
}
