package com.hues.miris_hues_android.data;

import com.microsoft.azure.storage.blob.ListBlobItem;

import java.util.ArrayList;

/**
 * Created by Null on 2017-01-31.
 */
public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private ArrayList<CognitiveTagData> tagDatas = new ArrayList<CognitiveTagData>();
    private ArrayList<CognitiveTextData> textDatas = new ArrayList<CognitiveTextData>();
    private ArrayList<ListBlobItem> listBlobItems = new ArrayList<ListBlobItem>();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }

    public ArrayList<CognitiveTagData> getTagDatas() {
        return tagDatas;
    }

    public void setTagDatas(ArrayList<CognitiveTagData> tagDatas) {
        this.tagDatas = tagDatas;
    }

    public ArrayList<CognitiveTextData> getTextDatas() {
        return textDatas;
    }

    public void setTextDatas(ArrayList<CognitiveTextData> textDatas) {
        this.textDatas = textDatas;
    }

    public ArrayList<ListBlobItem> getListBlobItems() {
        return listBlobItems;
    }

    public void setListBlobItems(ArrayList<ListBlobItem> listBlobItems) {
        this.listBlobItems = listBlobItems;
    }
}
