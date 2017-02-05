package com.hues.miris_hues_android.gps;

/**
 * Created by secret on 2/2/17.
 */

public interface GPSContract {
    interface View {
        void initView();
        void gpsDataUpdate(double latitude, double longitude);
    }
    interface UserAction {

    }
}
