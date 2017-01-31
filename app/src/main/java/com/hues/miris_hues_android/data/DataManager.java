package com.hues.miris_hues_android.data;

import java.util.List;

/**
 * Created by Null on 2017-01-31.
 */
public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private List<CognitiveTagData> tagDatas;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }

    public List<CognitiveTagData> getTagDatas() {
        return tagDatas;
    }

    public void setTagDatas(List<CognitiveTagData> tagDatas) {
        this.tagDatas = tagDatas;
    }
}
