package com.xnliang.yishibao.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xnliang.yishibao.R;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class IntegralTopUpActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integral_top_up);
        initView();
    }

    private void initView() {
        ImageButton backButton = findViewById(R.id.ib_pay_back);
        Button payButton = findViewById(R.id.bt_integral);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext() , "支付方式" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
