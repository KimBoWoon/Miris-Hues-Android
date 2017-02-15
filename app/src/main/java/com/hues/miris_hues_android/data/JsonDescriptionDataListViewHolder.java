package com.hues.miris_hues_android.data;

import android.view.View;
import android.widget.TextView;

import com.hues.miris_hues_android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 보운 on 2017-02-06.
 */

public class JsonDescriptionDataListViewHolder {
    @Bind(R.id.description_text)
    public TextView nameText;

    public JsonDescriptionDataListViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
