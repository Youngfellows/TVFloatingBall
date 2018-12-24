package com.speex.floating.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.speex.floating.R;
import com.speex.floating.manager.FloatWindowManager;


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
                    FloatWindowManager.getInstance().showFloatWindow();
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