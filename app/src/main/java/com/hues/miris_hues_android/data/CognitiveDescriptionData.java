package com.hues.miris_hues_android.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 보운 on 2017-02-06.
 */

public class CognitiveDescriptionData {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
