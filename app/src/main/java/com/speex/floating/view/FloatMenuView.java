package com.speex.floating.view;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.speex.floating.R;
import com.speex.floating.bean.Size;
import com.speex.floating.manager.FloatWindowManager;

public class FloatMenuView extends FrameLayout {
    private TextView textView;
    private FloatWindowView.OnTouchEventListener mOnTouchEventListener;

    public FloatMenuView(Context context) {
        this(context, null);
    }

    public FloatMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
        setListener();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_menu, this);
        textView = (TextView) findViewById(R.id.floatMenuView);
        animateExpand(textView);
    }


    private void setListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindowManager.getInstance().removeMenu();
                FloatWindowManager.getInstance().showFloatWindow();
                FloatWindowManager.getInstance().setTouchEventListener(mOnTouchEventListener);
            }
        });
    }

    private void animateExpand(TextView textView) {
        int finalWidth = textView.getLayoutParams().width;
        int finalHeight = textView.getLayoutParams().height;
        performAnimate(textView, new Size(0, 0), new Size(finalWidth, finalHeight));
    }

    private void performAnimate(final View target, final Size start, final Size end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();
                Log.d("aa", "current value: " + currentValue);

                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;

                //直接调用整型估值器通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start.getWidth(), end.getWidth());
                target.getLayoutParams().height = mEvaluator.evaluate(fraction, start.getHeight(), end.getHeight());
                target.requestLayout();
            }
        });

        valueAnimator.setDuration(5000).start();
    }

    public void setTouchEventListener(FloatWindowView.OnTouchEventListener onTouchEventListener) {
        this.mOnTouchEventListener = onTouchEventListener;
    }
}
