package com.speex.floating.utils;


import com.speex.floating.app.App;

public class DensityUtils {
    /**
     * dp转px
     *
     * @param dp 要转换的dp值
     * @return 转换后的px值
     */
    public static int dpToPx(float dp) {
        final float scale = App.mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int PxToDp(float px) {
        final float scale = App.mContext.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}