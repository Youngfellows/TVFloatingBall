package com.speex.floating.utils;


import android.content.Context;

public class DensityUtils {
    /**
     * dp转px
     *
     * @param dp 要转换的dp值
     * @return 转换后的px值
     */
    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int PxToDp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}