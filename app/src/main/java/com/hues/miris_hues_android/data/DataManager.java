package com.hues.miris_hues_android.data;

import java.util.ArrayList;

/**
 * Created by Null on 2017-01-31.
 */
public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private ArrayList<CognitiveTagData> tagDatas = new ArrayList<CognitiveTagData>();

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
}
