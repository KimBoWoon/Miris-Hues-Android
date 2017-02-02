package com.hues.miris_hues_android.gps;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.hues.miris_hues_android.R;

/**
 * Created by secret on 2/2/17.
 */

public class GPSActivity extends AppCompatActivity implements GPSContract.View {
    private GPSPresenter mGpsPresenter;
    private AQuery aq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_layout);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGpsPresenter.stopUsingGPS();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGpsPresenter.stopUsingGPS();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGpsPresenter.stopUsingGPS();
    }

    @Override
    public void initView() {
        mGpsPresenter = new GPSPresenter(this);
        aq = new AQuery(this);

        if (mGpsPresenter.isGetLocation()) {
            aq.id(R.id.gps_text_latitude).text("위도 " + mGpsPresenter.getLatitude()).textSize(20).textColor(Color.BLACK);
            aq.id(R.id.gps_text_longitude).text("경도 " + mGpsPresenter.getLongitude()).textSize(20).textColor(Color.BLACK);
        } else {
            mGpsPresenter.showSettingsAlert();
        }
    }
}
