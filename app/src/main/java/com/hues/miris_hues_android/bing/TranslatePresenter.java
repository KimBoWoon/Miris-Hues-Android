package com.hues.miris_hues_android.bing;

import com.hues.miris_hues_android.thread.TextTranslateThread;

/**
 * Created by secret on 2/6/17.
 */

public class TranslatePresenter implements TranslateContract.UserAction {
    private TranslateContract.View mTranslateView;
    private TranslateModel mTranslateModel;

    public TranslatePresenter(TranslateContract.View view) {
        this.mTranslateView = view;
        this.mTranslateModel = new TranslateModel();
    }

    public void getTranslateText(String inputText) {
        try {
            TextTranslateThread textTranslateThread = new TextTranslateThread((TranslateActivity) mTranslateView, inputText);
            textTranslateThread.start();
            textTranslateThread.join();
            mTranslateView.setText(textTranslateThread.getTranslateText());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
