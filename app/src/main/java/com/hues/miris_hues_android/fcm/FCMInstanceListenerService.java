package com.hues.miris_hues_android.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.hues.miris_hues_android.data.SharedStore;
import com.hues.miris_hues_android.log.Logging;

/**
 * Created by secret on 2/20/17.
 */

public class FCMInstanceListenerService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        SharedStore.setString(this, "token", token);
        Logging.i(token);
    }
}