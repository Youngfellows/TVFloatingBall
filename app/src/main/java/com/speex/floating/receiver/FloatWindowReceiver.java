package com.speex.floating.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.speex.floating.manager.FloatWindowManager;
import com.speex.floating.view.FloatWindowView;

/**
 * Created by Byron on 2019/2/12.
 */

public class FloatWindowReceiver extends BroadcastReceiver {

    private String TAG = this.getClass().getSimpleName();

    /**
     * 显示悬浮窗
     */
    private String ACTION_FLOAT_WINDOW_SHOW = "com.speex.ACTION_FLOAT_WINDOW_SHOW";

    /**
     * 隐藏悬浮窗
     */
    private String ACTION_FLOAT_WINDOW_HIDE = "com.speex.ACTION_FLOAT_WINDOW_HIDE";

    /**
     * adb shell am broadcast -a com.speex.ACTION_FLOAT_WINDOW_SHOW
     * adb shell am broadcast -a com.speex.ACTION_FLOAT_WINDOW_HIDE
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_FLOAT_WINDOW_SHOW.equals(action)) {
            Log.i(TAG, "onReceive: show");
            FloatWindowManager.getInstance().init(context);
            FloatWindowManager.getInstance().showFloatWindow();
            FloatWindowManager.getInstance().setTouchEventListener(new FloatWindowView.OnTouchEventListener() {
                @Override
                public void onDown() {
                    Log.d(TAG, "onDown: xxx");
                }

                @Override
                public void onClick() {
                    Log.d(TAG, "onClick: xxx");
                }

                @Override
                public void onUp() {
                    Log.d(TAG, "onUp: xxx");
                }
            });

        } else if (ACTION_FLOAT_WINDOW_HIDE.equals(action)) {
            Log.i(TAG, "onReceive: hide");
            FloatWindowManager.getInstance().removeFloatWindow();
        }
    }
}
