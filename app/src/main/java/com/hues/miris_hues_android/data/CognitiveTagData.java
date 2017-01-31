package com.hues.miris_hues_android.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by secret on 1/27/17.
 */

public class CognitiveTagData {
    @SerializedName("name")
    private String tagName;
    @SerializedName("confidence")
    private double tagConfidence;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public double getTagConfidence() {
        return tagConfidence;
    }

    public void setTagConfidence(double tagConfidence) {
        this.tagConfidence = tagConfidence;
    }
}
