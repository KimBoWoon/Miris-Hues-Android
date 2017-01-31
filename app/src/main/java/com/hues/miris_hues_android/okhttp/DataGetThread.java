package com.hues.miris_hues_android.okhttp;

import android.os.Handler;
import android.os.Message;

import com.hues.miris_hues_android.log.Logging;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by secret on 1/29/17.
 */

public class DataGetThread extends Thread {
    private String url;
    private OkHttpClient mClient;

    public DataGetThread(String url) {
        this.url = url;
        mClient = new OkHttpClient();
    }

    @Override
    public void run() {
        super.run();

        try {
            Request request = new Request.Builder().url(url).build();
            Response response = mClient.newCall(request).execute();

            while (true) {
                if (response.isSuccessful()) {
                    Logging.i(response.body().string());
                    Message msg = Message.obtain();
                    msg.what = 0;
//                    msg.obj = response.body().string();
                    msg.obj = "qasdfzxcv";
                    handler.sendMessage(msg);
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
                Logging.i(msg.obj.toString());
                Logging.i("Success");
            }
        }
    };
}
