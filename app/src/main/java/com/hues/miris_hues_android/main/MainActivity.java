package com.hues.miris_hues_android.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.hues.miris_hues_android.R;
import com.hues.miris_hues_android.data.DataManager;
import com.hues.miris_hues_android.data.JsonTagDataListViewAdapter;
import com.hues.miris_hues_android.data.JsonTextDataListViewAdapter;
import com.hues.miris_hues_android.gps.GPSActivity;
import com.hues.miris_hues_android.log.Logging;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainPresenter mMainPresenter;
    private AQuery aq;
    private JsonTagDataListViewAdapter jsonTagDataListViewAdapter;
    private JsonTextDataListViewAdapter jsonTextDataListViewAdapter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public void initView() {
        mMainPresenter = new MainPresenter(this);
        aq = new AQuery(this);
        ButterKnife.bind(this);

        DataManager.getInstance().getTagDatas().clear();
    }

    @OnClick(R.id.main_btn_tag)
    public void tagBtnClicked() {
        Logging.i("tagBtnClicked Function");

        mMainPresenter.getJsonString("tag");

        jsonTagDataListViewAdapter = new JsonTagDataListViewAdapter();
        listview = (ListView) findViewById(R.id.json_data_list);
        listview.setAdapter(jsonTagDataListViewAdapter);
        jsonTagDataListViewAdapter.notifyDataSetChanged();

        aq.id(R.id.main_img).image(String.valueOf(DataManager.getInstance().getListBlobItems().get(DataManager.getInstance().getListBlobItems().size() - 2).getUri()));
    }

    @OnClick(R.id.main_btn_text)
    public void textBtnClicked() {
        Logging.i("textBtnClicked Function");

        mMainPresenter.getJsonString("text");

        jsonTextDataListViewAdapter = new JsonTextDataListViewAdapter();
        listview = (ListView) findViewById(R.id.json_data_list);
        listview.setAdapter(jsonTextDataListViewAdapter);
        jsonTextDataListViewAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.main_btn_gps)
    public void gpsBtnClicked() {
        startActivity(new Intent(this, GPSActivity.class));
    }
}
