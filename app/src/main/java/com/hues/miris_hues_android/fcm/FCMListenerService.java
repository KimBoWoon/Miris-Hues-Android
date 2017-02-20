package com.hues.miris_hues_android.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hues.miris_hues_android.log.Logging;

/**
 * Created by secret on 2/20/17.
 */

public class FCMListenerService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Logging.i("===============FCM MSG===============");
//        Loging.i("메시지 수신 : " + remoteMessage.getData().toString());
//        String jsonStr = remoteMessage.getData().get("key2");
//        FCMMsgModel msg = new Gson().fromJson(jsonStr, FCMMsgModel.class);
//        Loging.i("나이 : " + msg.getAge());
        Logging.i(remoteMessage.getData().toString());
        Logging.i("===============FCM MSG===============");
    }
}
