package com.baxian.vinda.touch;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.baxian.vinda.R;
import com.baxian.vinda.base.BaseActivity;

/**
 * Created by Administrator on 2016/1/5.
 */
public class WaTouchActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private Button mTouch_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        initView();
    }

    private void initView(){
        mTouch_btn = (Button) findViewById(R.id.touch_btn);

        mTouch_btn.setOnClickListener(this);
        mTouch_btn.setOnTouchListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.touch_btn:
                Log.d("TAG", "onClick execute");
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("TAG", "onTouch execute, action " + event.getAction());
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
