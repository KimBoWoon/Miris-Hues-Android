package com.hues.miris_hues_android.bot;

/**
 * Created by secret on 3/5/17.
 */

public class BotPresenter implements BotContract.UserAction {
    private BotContract.View mBotView;
    private BotModel mBotModel;

    public BotPresenter(BotContract.View view) {
        this.mBotView = view;
        this.mBotModel = new BotModel();
    }
}
