package com.hues.miris_hues_android.main;

import com.hues.miris_hues_android.thread.AzureBlobStorageThread;
import com.hues.miris_hues_android.thread.JsonDescriptionDataGetThread;
import com.hues.miris_hues_android.thread.JsonTagDataGetThread;
import com.hues.miris_hues_android.thread.JsonTextDataGetThread;

/**
 * Created by secret on 1/27/17.
 */

public class MainModel {
    public void jsonTagDataThreadStart(String url) {
        try {
            JsonTagDataGetThread jsonTagDataGetThread =
                    new JsonTagDataGetThread(url);
            jsonTagDataGetThread.start();
            jsonTagDataGetThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void jsonTextDataThreadStart(String url) {
        try {
            JsonTextDataGetThread jsonTextDataGetThread =
                    new JsonTextDataGetThread(url);
            jsonTextDataGetThread.start();
            jsonTextDataGetThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void jsonDescriptionDataThreadStart(String url) {
        try {
            JsonDescriptionDataGetThread jsonDescriptionDataGetThread =
                    new JsonDescriptionDataGetThread(url);
            jsonDescriptionDataGetThread.start();
            jsonDescriptionDataGetThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getAzureStorage(String url) {
        try {
            AzureBlobStorageThread azureBlobStorageThread =
                    new AzureBlobStorageThread(url);
            azureBlobStorageThread.start();
            azureBlobStorageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
