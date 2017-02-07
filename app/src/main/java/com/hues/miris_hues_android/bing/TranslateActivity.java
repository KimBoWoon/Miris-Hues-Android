package com.hues.miris_hues_android.bing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.hues.miris_hues_android.R;
import com.hues.miris_hues_android.log.Logging;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by secret on 2/7/17.
 */

public class TranslateActivity extends AppCompatActivity implements TranslateContract.View {
    private TranslatePresenter mTranslatePresenter;
    private AQuery aq;
//    @Bind(R.id.translate_edit_text)
//    private EditText mTranslateEditText;
//    @Bind(R.id.translate_text_result)
//    private TextView mTranslateTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate_activity);

        initView();
    }

    @Override
    public void initView() {
        mTranslatePresenter = new TranslatePresenter(this);
        aq = new AQuery(this);
        ButterKnife.bind(this);

//        mTranslateEditText = (EditText) findViewById(R.id.translate_edit_text);
//        mTranslateTextView = (TextView) findViewById(R.id.translate_text_result);
    }

    @Override
    public void setText(String text) {
//        mTranslateTextView.setText(text);
        aq.id(R.id.translate_text_result).text(text).textColor(Color.BLACK).textSize(20);
    }

    @OnClick(R.id.translate_btn)
    public void translateText() {
//        String text = mTranslateEditText.getText().toString();
        Logging.i("translate Function");
        String input = aq.id(R.id.translate_edit_text).getText().toString();
        Logging.i(input);
        mTranslatePresenter.getTranslateText(input);
    }
}
