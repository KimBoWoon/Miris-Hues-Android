package com.hues.miris_hues_android.main;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.hues.miris_hues_android.R;
import com.hues.miris_hues_android.data.DataManager;
import com.hues.miris_hues_android.data.JsonTagDataListViewAdapter;
import com.hues.miris_hues_android.log.Logging;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainPresenter mMainPresenter;
    private AQuery aq;
    private JsonTagDataListViewAdapter jsonTagDataListViewAdapter;
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
        jsonTagDataListViewAdapter = new JsonTagDataListViewAdapter();

        listview = (ListView) findViewById(R.id.json_data_list);
        listview.setAdapter(jsonTagDataListViewAdapter);
    }

    @OnClick(R.id.main_btn_tag)
    public void tagBtnClicked() {
        Logging.i("tagBtnClicked Function");
        mMainPresenter.getJsonString("http://miris-webapp.azurewebsites.net/tag");
        listview.deferNotifyDataSetChanged();
        jsonTagDataListViewAdapter.notifyDataSetChanged();
    }

//    @OnClick(R.id.main_btn_text)
//    public void textBtnClicked() {
//        mMainPresenter.getJsonString("http://miris-webapp.azurewebsites.net/text");
//    }
}
