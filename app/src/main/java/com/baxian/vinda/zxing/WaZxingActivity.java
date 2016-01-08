package com.baxian.vinda.zxing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baxian.vinda.R;

/**
 * Zxing扫描条形码二维码等
 */
public class WaZxingActivity extends Activity {

    private Button mZXing_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zxing);

        mZXing_btn = (Button) findViewById(R.id.zxing_btn);
        mZXing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
