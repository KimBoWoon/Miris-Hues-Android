package com.hues.miris_hues_android.gps;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.hues.miris_hues_android.R;
import com.hues.miris_hues_android.log.Logging;

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

        if (!mGpsPresenter.isGetLocation()) {
            mGpsPresenter.showSettingsAlert();
        }
    }

    @Override
    public void gpsDataUpdate(double latitude, double longitude) {
        Logging.i("위도 : " + latitude + ", 경도 : " + longitude);
        aq.id(R.id.gps_text_latitude).text("위도 " + latitude).textSize(20).textColor(Color.BLACK);
        aq.id(R.id.gps_text_longitude).text("경도 " + longitude).textSize(20).textColor(Color.BLACK);
    }
}
