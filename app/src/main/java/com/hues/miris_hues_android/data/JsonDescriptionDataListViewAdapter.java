package com.hues.miris_hues_android.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hues.miris_hues_android.R;

/**
 * Created by 보운 on 2017-02-06.
 */

public class JsonDescriptionDataListViewAdapter extends BaseAdapter {
    private JsonDescriptionDataListViewHolder holder;

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return DataManager.getInstance().getDescriptionDatas();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_description_item, viewGroup, false);

            holder = new JsonDescriptionDataListViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (JsonDescriptionDataListViewHolder) view.getTag();
        }

        String item = DataManager.getInstance().getDescriptionDatas().getText();

        holder.nameText.setText(item);

        return view;
    }
}
