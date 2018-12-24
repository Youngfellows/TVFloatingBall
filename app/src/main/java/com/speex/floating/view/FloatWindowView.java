package com.speex.floating.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.speex.floating.R;
import com.speex.floating.app.App;
import com.speex.floating.manager.FloatWindowManager;
import com.speex.floating.utils.ScreenUtils;


public class FloatWindowView extends FrameLayout {
    private String TAG = this.getClass().getSimpleName();
    private FrameLayout floatWindow;
    private float xInView;
    private float yInView;

    private float xInScreen;
    private float yInScreen;

    private float xDownInScreen;
    private float yDownInScreen;


    public FloatWindowView() {
        this(App.mContext);
    }

    public FloatWindowView(Context context) {
        this(context, null);
    }

    public FloatWindowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
        setListener();

    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_float_window, this);
        floatWindow = (FrameLayout) view.findViewById(R.id.iv_float_window);
    }

    private void setListener() {
        MyTouchListener myTouchListener = new MyTouchListener();
        floatWindow.setOnTouchListener(myTouchListener);
    }


    private class MyTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xInView = event.getX();
                    yInView = event.getY();
                    xDownInScreen = event.getRawX();
                    yDownInScreen = event.getRawY() - FloatWindowManager.getInstance().getStatusBarHeight();
                    xInScreen = event.getRawX();
                    yInScreen = event.getRawY() - FloatWindowManager.getInstance().getStatusBarHeight();
                    Log.e(TAG, "ACTION_DOWN 按下啦");
                    break;
                case MotionEvent.ACTION_MOVE:
                    xInScreen = event.getRawX();
                    yInScreen = event.getRawY() - FloatWindowManager.getInstance().getStatusBarHeight();
                    FloatWindowManager.getInstance().updateLocation((int) (xInScreen - xInView), (int) (yInScreen - yInView));
                    break;
                case MotionEvent.ACTION_UP:
                    Log.e(TAG, "ACTION_UP 松开啦");
                    if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) { //如果抬起点和按下点一致，则视为点击
//                        FloatWindowManager.getInstance().removeFloatWindow();
//                        FloatWindowManager.getInstance().showMenu();

                        // TODO: 2018/12/25 点击了
                        Log.i(TAG, "点击了按钮 , mOnClickListener = " + mOnClickListener);
                        if (mOnClickListener != null) {
                            mOnClickListener.onClick();
                        }
                        break;
                    }

                    //计算图标与屏幕距离，判断应停靠在哪一边
                    float xRatio = xInScreen / ScreenUtils.getWindowWidthPx();
                    float yRatio = yInScreen / ScreenUtils.getWindowHeightPx();

                    if (xRatio < 0.5 && yRatio < 0.5) { //第一象限
                        if (xRatio < yRatio) { //停靠屏幕左边
                            FloatWindowManager.getInstance().animateMove(0, (int) (yInScreen - yInView));
                        } else { //停靠屏幕上边
                            FloatWindowManager.getInstance().animateMove((int) (xInScreen - xInView), 0);
                        }
                    } else if (xRatio > 0.5 && yRatio < 0.5) { //第二象限
                        if (xRatio > 1 - yRatio) { //停靠屏幕右边
                            FloatWindowManager.getInstance().animateMove((ScreenUtils.getWindowWidthPx() - getWidth()), (int) (yInScreen - yInView));
                        } else { //停靠屏幕上边
                            FloatWindowManager.getInstance().animateMove((int) (xInScreen - xInView), 0);
                        }
                    } else if (xRatio > 0.5 && yRatio > 0.5) { //第三象限
                        if (xRatio > yRatio) { //停靠屏幕右边
                            FloatWindowManager.getInstance().animateMove((ScreenUtils.getWindowWidthPx() - getWidth()), (int) (yInScreen - yInView));
                        } else { //停靠屏幕下边
                            FloatWindowManager.getInstance().animateMove((int) (xInScreen - xInView), ScreenUtils.getWindowHeightPx() - getHeight());
                        }
                    } else { //第四象限
                        if (1 - xRatio > yRatio) { //停靠屏幕左边
                            FloatWindowManager.getInstance().animateMove(0, (int) (yInScreen - yInView));
                        } else { //停靠屏幕下边
                            FloatWindowManager.getInstance().animateMove((int) (xInScreen - xInView), ScreenUtils.getWindowHeightPx() - getHeight());
                        }
                    }

                    break;
            }

            return true;
        }
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick();
    }
}
