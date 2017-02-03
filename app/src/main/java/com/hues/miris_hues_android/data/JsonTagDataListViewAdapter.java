package com.hues.miris_hues_android.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hues.miris_hues_android.R;

/**
 * Created by 보운 on 2017-02-01.
 */

public class JsonTagDataListViewAdapter extends BaseAdapter {
    private JsonTagDataViewHolder holder;

    @Override
    public int getCount() {
        return DataManager.getInstance().getTagDatas().size();
    }

    @Override
    public Object getItem(int i) {
        return DataManager.getInstance().getTagDatas().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);

            holder = new JsonTagDataViewHolder(viewGroup);
            view.setTag(holder);
        } else {
            holder = (JsonTagDataViewHolder) view.getTag();
        }

        CognitiveTagData item = DataManager.getInstance().getTagDatas().get(i);

        holder.nameText.setText(item.getTagName());
        holder.confidenceText.setText(String.valueOf(item.getTagConfidence()));

        return view;
    }
}
