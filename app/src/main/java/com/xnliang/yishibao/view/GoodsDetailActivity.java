package com.xnliang.yishibao.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.HandlerBackUtil;
import com.xnliang.yishibao.presenter.dialog.GoodsNumberDialog;
import com.xnliang.yishibao.view.fragment.GoodsDetailFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-09.
 */

public class GoodsDetailActivity extends BaseActivity {

    private Context mContext;
    private static final String mGoodsDetailIndex ="http://ysb.appxinliang.cn/api/shop/detail";
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private String mPrice;
    private String mNum;
    private String mContent;
    private static final int INIT_FRAGMENT = 0;
    private String mImage;
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_goods_detail);
        initView();
        initData();
    }

    private void initData() {
        int id = getIntent().getIntExtra("detail" , 0);
        getWebPageFromNet(mGoodsDetailIndex ,id);
    }

    private void getWebPageFromNet(String url ,int id ) {
        OkHttpUtils
                .get()
                .addParams("id" , id + "")
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                        Toast.makeText(GoodsDetailActivity.this ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        processData(response);
                    }
                });
    }

    private void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        JSONObject jsondata = jsonObject.getJSONObject("data");
        mPrice = jsondata.getString("shop_price");
        mNum = jsondata.getString("store_count");
        mContent = jsondata.getString("content");
        mImage = jsondata.getString("image");
        mTitle = jsondata.getString("goods_name");

        handler.obtainMessage(INIT_FRAGMENT).sendToTarget();

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {

        ImageButton backButton = findViewById(R.id.ib_detail_back);
        Button intoButton = findViewById(R.id.bt_put_into);
        Button buyButton = findViewById(R.id.bt_buy_now);

        backButton.setOnClickListener(new DetailBackClickListener());
        intoButton.setOnClickListener(new IntoClickListener());
        buyButton.setOnClickListener(new BuyClickListener());
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case INIT_FRAGMENT:
                    initFragment();
                    break;
            }
        }
    };

    private void initFragment() {
        GoodsDetailFragment detailFragment = new GoodsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("price" , mPrice);
        bundle.putString("num" , mNum);
        bundle.putString("content" , mContent);
        detailFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_goods_detail ,detailFragment ,"detail");
        transaction.addToBackStack("detail");
        transaction.commit();
    }

    public class DetailBackClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class IntoClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //TODO modify data
            GoodsNumberDialog dialog = new GoodsNumberDialog(mContext , mImage , mTitle ,mPrice ,mNum);
            dialog.show();
            Toast.makeText(getApplicationContext(),"222",Toast.LENGTH_SHORT).show();
        }
    }

    public class BuyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //TODO
            Toast.makeText(getApplicationContext(),"333",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (!HandlerBackUtil.handleBackPress(this)) {
            super.onBackPressed();
        }
    }
}
