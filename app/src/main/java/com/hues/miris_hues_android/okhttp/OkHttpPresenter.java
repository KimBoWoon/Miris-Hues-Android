package com.hues.miris_hues_android.okhttp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by secret on 1/27/17.
 */

public class OkHttpPresenter {
    OkHttpClient mClient = new OkHttpClient();

    public String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
