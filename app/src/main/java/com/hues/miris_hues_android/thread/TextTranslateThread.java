package com.hues.miris_hues_android.thread;

import android.content.Context;

import com.hues.miris_hues_android.data.SharedStore;
import com.hues.miris_hues_android.log.Logging;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Created by secret on 2/7/17.
 */

public class TextTranslateThread extends Thread {
    private Context context;
    private String inputText;
    private String resultString;

    public TextTranslateThread(Context context, String inputText) {
        this.context = context;
        this.inputText = inputText;
    }

    @Override
    public void run() {
        super.run();

        try {
            Logging.i("Text Translate Thread");
            Translate.setClientId(SharedStore.getString(context, "MIRIS_TRANSLATE"));
            Translate.setClientSecret(SharedStore.getString(context, "TRANSLATE_CLIENT_KEY"));

            resultString = Translate.execute(inputText, Language.ENGLISH, Language.KOREAN);
            Logging.i(resultString);
        } catch (Exception e) {
            Logging.i(e.toString());
            e.printStackTrace();
        }
    }

    public String getTranslateText() {
        return resultString;
    }
}
