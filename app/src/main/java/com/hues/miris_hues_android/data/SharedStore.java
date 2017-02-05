package com.hues.miris_hues_android.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 보운 on 2017-02-05.
 */

public class SharedStore {
    public static String key = "pref";

    // 불린값 획득
    public static boolean getBoolean(Context context, String param) {
        return context.getSharedPreferences(key, 0).getBoolean(param, false);
    }

    // 불린값 저장
    public static void setBooolean(Context context, String param, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(key, 0).edit();
        editor.putBoolean(param, value);
        editor.commit();
    }

    // 문자열 획득
    public static String getString(Context context, String param) {
        return context.getSharedPreferences(key, 0).getString(param, "");
    }

    // 문자열 저장
    public static void setString(Context context, String param, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(key, 0).edit();
        editor.putString(param, value);
        editor.commit();
    }
}
