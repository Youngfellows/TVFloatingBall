package com.speex.floating.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.speex.floating.app.App;


/**
 * 获取屏幕宽高
 */
public class ScreenUtils {

    public static int getWindowWidthPx() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) App.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getWindowHeightPx() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) App.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getWindowWidthDp() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) App.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return DensityUtils.PxToDp(displayMetrics.widthPixels);
    }

    public static int getWindowHeightDp() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) App.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return DensityUtils.PxToDp(displayMetrics.heightPixels);
    }
}
