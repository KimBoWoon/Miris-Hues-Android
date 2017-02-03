package com.hues.miris_hues_android.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hues.miris_hues_android.R;

/**
 * Created by 보운 on 2017-02-03.
 */

public class JsonTextDataListViewAdapter extends BaseAdapter {
    private JsonTextDataViewHolder holder;

    @Override
    public int getCount() {
        return DataManager.getInstance().getTextDatas().size();
    }

    @Override
    public Object getItem(int i) {
        return DataManager.getInstance().getTextDatas().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_text_item, viewGroup, false);

            holder = new JsonTextDataViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (JsonTextDataViewHolder) view.getTag();
        }

        CognitiveTextData item = DataManager.getInstance().getTextDatas().get(i);

        holder.boundingBox.setText(item.getLines().get(i).getWordses().get(i).getBoundingBox());
        holder.word.setText(item.getLines().get(i).getWordses().get(i).getText());

        return view;
    }
}
