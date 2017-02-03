package com.hues.miris_hues_android.data;

import android.view.View;
import android.widget.TextView;

import com.hues.miris_hues_android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by secret on 2/3/17.
 */

public class JsonTagDataViewHolder {
    @Bind(R.id.item_name)
    public TextView nameText;
    @Bind(R.id.item_confidence)
    public TextView confidenceText;

    public JsonTagDataViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
