package com.speex.floating.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.speex.floating.R;
import com.speex.floating.manager.FloatWindowManager;
import com.speex.floating.view.FloatWindowView;


public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch switchFloatWindow = (Switch) findViewById(R.id.switch_float_window);
        if (FloatWindowManager.getInstance().isWindowShowing()) {
            switchFloatWindow.setChecked(true);
        } else {
            switchFloatWindow.setChecked(false);
        }

        switchFloatWindow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FloatWindowManager.getInstance().init(MainActivity.this);
                    FloatWindowManager.getInstance().showFloatWindow();
                    FloatWindowManager.getInstance().setTouchEventListener(new FloatWindowView.OnTouchEventListener() {
                        @Override
                        public void onDown() {
                            Log.d(TAG, "onDown: 按下了");
                        }

                        @Override
                        public void onClick() {
                            Log.d(TAG, "onClick: 点击了");
                            FloatWindowManager.getInstance().removeFloatWindow();
                            FloatWindowManager.getInstance().showMenu();
                        }

                        @Override
                        public void onUp() {
                            Log.d(TAG, "onUp: 松开了");
                        }
                    });
                    finish();
                } else {
                    FloatWindowManager.getInstance().removeFloatWindow();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
