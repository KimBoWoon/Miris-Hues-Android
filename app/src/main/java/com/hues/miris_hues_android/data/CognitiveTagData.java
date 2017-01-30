package com.hues.miris_hues_android.data;

/**
 * Created by secret on 1/27/17.
 */

public class CognitiveTagData {
    private String tagName;
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
