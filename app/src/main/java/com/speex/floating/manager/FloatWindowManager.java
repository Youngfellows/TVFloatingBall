package com.speex.floating.manager;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.speex.floating.bean.Point;
import com.speex.floating.view.FloatMenuView;
import com.speex.floating.view.FloatWindowView;

public class FloatWindowManager {
    private String TAG = this.getClass().getSimpleName();
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mFloatWindowLayoutParams;
    private WindowManager.LayoutParams mMenuLayoutParams;

    private FloatWindowView mFloatWindow;
    private FloatMenuView mMenu;
    private Context mContext;
    private FloatWindowView.OnTouchEventListener mOnTouchEventListener;

    public static FloatWindowManager getInstance() {
        return FloatWindowManagerHolder.instance;
    }

    private FloatWindowManager() {

    }

    private static class FloatWindowManagerHolder {
        private static final FloatWindowManager instance = new FloatWindowManager();
    }

    /**
     * 初始化上下文
     *
     * @param context
     */
    public void init(Context context) {
        this.mContext = context;
    }

    public void showFloatWindow() {

        if (mFloatWindow == null) {
            mFloatWindow = new FloatWindowView(mContext);
        }

        if (mFloatWindowLayoutParams == null) {
            mFloatWindowLayoutParams = new WindowManager.LayoutParams();
            initFloatWindowLayoutParams(mFloatWindowLayoutParams);
        }
        getWindowManager().addView(mFloatWindow, mFloatWindowLayoutParams);
    }

    public void removeFloatWindow() {
        if (mFloatWindow != null) {
            getWindowManager().removeView(mFloatWindow);
            mFloatWindow = null;
        }
    }

    public void showMenu() {

        if (mMenu == null) {
            mMenu = new FloatMenuView(mContext);
        }

        if (mMenuLayoutParams == null) {
            mMenuLayoutParams = new WindowManager.LayoutParams();
            initMenuLayoutParams(mMenuLayoutParams);
        }
        getWindowManager().addView(mMenu, mMenuLayoutParams);

        //注册监听回调
        if (mMenu != null) {
            mMenu.setTouchEventListener(mOnTouchEventListener);
        }
    }

    public void removeMenu() {
        if (mMenu != null) {
            getWindowManager().removeView(mMenu);
            mMenu = null;
        }
    }

    private WindowManager getWindowManager() {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    private void initFloatWindowLayoutParams(WindowManager.LayoutParams layoutParams) {
        layoutParams.flags = layoutParams.flags |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.x = 10;
        layoutParams.y = mFloatWindow.getHeight() + getStatusBarHeight();
    }

    private void initMenuLayoutParams(WindowManager.LayoutParams layoutParams) {
        layoutParams.flags = layoutParams.flags |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.format = PixelFormat.RGBA_8888;
    }

    public int getStatusBarHeight() {
        int height = 0;
        Resources resources = mContext.getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = resources.getDimensionPixelSize(resId);
        }
        return height;
    }

    public void updateLocation(int x, int y) {
        mFloatWindowLayoutParams.x = x;
        mFloatWindowLayoutParams.y = y;
        getWindowManager().updateViewLayout(mFloatWindow, mFloatWindowLayoutParams);
    }

    public void animateMove(int endX, int endY) {
        int startX = mFloatWindowLayoutParams.x;
        int startY = mFloatWindowLayoutParams.y;
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), new Point(startX, startY), new Point(endX, endY));
        animator.setDuration(300);
        animator.start();
    }

    private class PointEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startPoint, Point endPoint) {
            int x = (int) (startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX()));
            int y = (int) (startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY()));
            updateLocation(x, y);
            return null;
        }
    }

    /**
     * 是否有悬浮窗显示
     *
     * @return
     */
    public boolean isWindowShowing() {
        return mFloatWindow != null;
    }

    public void setTouchEventListener(FloatWindowView.OnTouchEventListener onTouchEventListener) {
        Log.i(TAG, "setOnTouchEventListener mFloatWindow = " + mFloatWindow);
        this.mOnTouchEventListener = onTouchEventListener;
        if (mFloatWindow != null) {
            mFloatWindow.setOnTouchEventListener(onTouchEventListener);
        }
    }

}
