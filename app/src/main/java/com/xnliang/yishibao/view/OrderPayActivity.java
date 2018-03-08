package com.xnliang.yishibao.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-02.
 */

public class OrderPayActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_number)
    TextView mNumber;
    @Bind(R.id.tv_number_remain)
    TextView mRemain;
    @Bind(R.id.ib_integral)
    ImageButton button;
    @Bind(R.id.ib_order_back)
    ImageButton backButton;
    @Bind(R.id.bt_pay)
    Button payButton;

    private static final String mOrderPayUrl ="http://ysb.appxinliang.cn/api/order/addToTourism";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private String mTotalAmount;
    private String mScoreRemain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(this, "login");
        initView();
    }

    private void initView() {
        button.setOnClickListener(this);
        backButton.setOnClickListener(this);
        payButton.setOnClickListener(this);

    }

    private void getDataFromNet(String url , int id , String invite) {
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .post()
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
//                .addParams("id" , "id")
                .addParams("invite" , invite)
                .addParams("id" , "1")
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                        Toast.makeText(OrderPayActivity.this ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_order_back:
                onBackPressed();
                break;
            case R.id.ib_integral:
                Intent intent = new Intent(OrderPayActivity.this , IntegralTopUpActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_pay:
                getDataFromNet(mOrderPayUrl  , 1 , "");
                Toast.makeText(getApplicationContext(),"支付完成" , Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
