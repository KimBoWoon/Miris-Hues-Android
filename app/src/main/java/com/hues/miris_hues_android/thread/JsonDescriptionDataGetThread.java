package com.hues.miris_hues_android.thread;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hues.miris_hues_android.data.CognitiveDescriptionData;
import com.hues.miris_hues_android.data.DataManager;
import com.hues.miris_hues_android.log.Logging;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 보운 on 2017-02-06.
 */

public class JsonDescriptionDataGetThread extends Thread {
    private String url;
    private OkHttpClient mClient;

    public JsonDescriptionDataGetThread(String url) {
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
                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.obj = response.body().string();
                    handler.sendMessage(msg);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Logging.i(msg.obj.toString());
                JsonElement root = new JsonParser().parse(msg.obj.toString()).getAsJsonObject().get("description").getAsJsonObject().get("captions");

                ArrayList<CognitiveDescriptionData> cognitiveDescriptionDatas = new Gson().fromJson(root, new TypeToken<ArrayList<CognitiveDescriptionData>>() {
                }.getType());
                DataManager.getInstance().setDescriptionDatas(cognitiveDescriptionDatas);

                Logging.i(DataManager.getInstance().getDescriptionDatas().get(0).getText());
                Logging.i(String.valueOf(DataManager.getInstance().getDescriptionDatas().get(0).getConfidence()));
                Logging.i(String.valueOf(DataManager.getInstance().getTagDatas().size()));
                Logging.i("Success");
            }
        }
    };
}
