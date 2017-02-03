package com.hues.miris_hues_android.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by secret on 1/27/17.
 */

public class CognitiveTextData {
    @SerializedName("boundingBox")
    private String boundingBox;
    @SerializedName("lines")
    private ArrayList<Lines> lines;

    public ArrayList<Lines> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Lines> lines) {
        this.lines = lines;
    }

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    class Lines {
        @SerializedName("boundingBox")
        private String boundingBox;
        @SerializedName("words")
        private ArrayList<Words> wordses;

        public String getBoundingBox() {
            return boundingBox;
        }

        public void setBoundingBox(String boundingBox) {
            this.boundingBox = boundingBox;
        }

        public ArrayList<Words> getWordses() {
            return wordses;
        }

        public void setWordses(ArrayList<Words> wordses) {
            this.wordses = wordses;
        }

        class Words {
            @SerializedName("boundingBox")
            private String boundingBox;
            @SerializedName("text")
            private String text;

            public String getBoundingBox() {
                return boundingBox;
            }

            public void setBoundingBox(String boundingBox) {
                this.boundingBox = boundingBox;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}