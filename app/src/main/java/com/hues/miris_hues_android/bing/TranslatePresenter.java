package com.hues.miris_hues_android.bing;

import com.hues.miris_hues_android.data.SharedStore;
import com.hues.miris_hues_android.log.Logging;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Created by secret on 2/6/17.
 */

public class TranslatePresenter implements TranslateContract.UserAction {
    private TranslateContract.View mTranslateView;
    private TranslateModel mTranslateModel;

    public TranslatePresenter(TranslateContract.View view) {
        this.mTranslateView = view;
        this.mTranslateModel = new TranslateModel();

        Translate.setClientId(SharedStore.getString((TranslateActivity) mTranslateView, "MIRIS_TRANSLATE"));
        Translate.setClientSecret(SharedStore.getString((TranslateActivity) mTranslateView, "TRANSLATE_CLIENT_KEY"));
    }

    public void getTranslateText(String inputText) {
        try {
            Logging.i(inputText);
            String result = Translate.execute(inputText, Language.AUTO_DETECT, Language.KOREAN);
            Logging.i(result);
            mTranslateView.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
