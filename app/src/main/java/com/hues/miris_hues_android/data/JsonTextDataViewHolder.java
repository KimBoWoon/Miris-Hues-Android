package com.hues.miris_hues_android.data;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hues.miris_hues_android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 보운 on 2017-02-03.
 */

public class JsonTextDataViewHolder {
//    @Bind(R.id.text_item_boundingBox)
    public TextView boundingBox;
//    @Bind(R.id.text_item_word)
    public TextView word;
    @Bind(R.id.listview_text_layout)
    public LinearLayout root;

    public JsonTextDataViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
