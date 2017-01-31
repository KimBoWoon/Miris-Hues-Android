package com.hues.miris_hues_android.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hues.miris_hues_android.R;

/**
 * Created by 보운 on 2017-02-01.
 */

public class JsonTagDataListViewAdapter extends BaseAdapter {
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
        }

        TextView nameText = (TextView) view.findViewById(R.id.item_name);
        TextView confidenceText = (TextView) view.findViewById(R.id.item_confidence);

        CognitiveTagData item = DataManager.getInstance().getTagDatas().get(i);

        nameText.setText(item.getTagName());
        confidenceText.setText(String.valueOf(item.getTagConfidence()));

        return view;
    }
}
