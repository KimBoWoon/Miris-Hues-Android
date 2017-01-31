package com.hues.miris_hues_android.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by secret on 1/27/17.
 */

public class CognitiveTextData {
    @SerializedName("name")
    private String textName;
    @SerializedName("confidence")
    private double textConfidence;

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public double getTextConfidence() {
        return textConfidence;
    }

    public void setTextConfidence(double textConfidence) {
        this.textConfidence = textConfidence;
    }
}
