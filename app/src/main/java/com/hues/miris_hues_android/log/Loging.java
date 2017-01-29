package com.hues.miris_hues_android.log;

import android.util.Log;

import com.hues.miris_hues_android.main.MainPresenter;

/**
 * Created by secret on 1/29/17.
 */

public class Loging {
    static final String TAG = "MirisHues";

    /**
     * Log Level Error
     **/
    public static void e(String message) {
        if (MainPresenter.DEBUG) {
            Log.e(TAG, buildLogMsg(message));
        }
    }

    /**
     * Log Level Warning
     **/
    public static void w(String message) {
        if (MainPresenter.DEBUG) {
            Log.w(TAG, buildLogMsg(message));
        }
    }

    /**
     * Log Level Information
     **/
    public static void i(String message) {
        if (MainPresenter.DEBUG) {
            Log.i(TAG, buildLogMsg(message));
        }
    }

    /**
     * Log Level Debug
     **/
    public static void d(String message) {
        if (MainPresenter.DEBUG) {
            Log.d(TAG, buildLogMsg(message));
        }
    }

    /**
     * Log Level Verbose
     **/
    public static void v(String message) {
        if (MainPresenter.DEBUG) {
            Log.v(TAG, buildLogMsg(message));
        }
    }


    private static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);

        return sb.toString();
    }
}
