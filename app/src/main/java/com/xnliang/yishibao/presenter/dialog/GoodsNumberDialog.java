package com.xnliang.yishibao.presenter.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.VectorEnabledTintResources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.view.GoodsDetailActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-11.
 */

public class GoodsNumberDialog extends AlertDialog implements View.OnClickListener{

    private Context mContext;
    private int amount = 1; //购买数量
    private int goods_storage = 1; //商品库存
    private int goods_price ;
    private String imageUrl;
    private String title;
    private EditText mEtNumber;
    private View mView;
    private GoodsDetailActivity mActivity;
    private static final String addCartUrl = "http://ysb.appxinliang.cn/api/shop/cart/add";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;

    public GoodsNumberDialog(Context context , String url ,String title ,String price ,String data) {
        super(context , R.style.dialogStyle);
        this.mContext = context;
        this.mActivity = (GoodsDetailActivity)context;
        this.imageUrl = url;
        this.title = title;
        this.goods_storage = Integer.valueOf(data);
        this.goods_price = Integer.valueOf(price);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.goods_alert_dialog , null);
        initView();
        setView(mView);
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "login");
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        ImageView detailView = mView.findViewById(R.id.iv_detail_dialog);
        TextView detailTitle = mView.findViewById(R.id.tv_detail_title);
        TextView detailIntegral = mView.findViewById(R.id.tv_detail_integral);
        TextView detailNum = mView.findViewById(R.id.tv_detail_number);

        Glide.with(mActivity).load(imageUrl).into(detailView);
        detailTitle.setText(title);
        String price = String.format(mActivity.getResources().getString(R.string.detail_dialog_integral),  goods_price);
        String storage = String.format(mActivity.getResources().getString(R.string.detail_dialog_num),  goods_storage);
        detailIntegral.setText(price);
        detailNum.setText(storage);

        Button dropButton = mView.findViewById(R.id.bt_detail_drop);
        Button plusButton = mView.findViewById(R.id.bt_detail_plus);
        mEtNumber = mView.findViewById(R.id.et_detail_number);
        Button confirmButton = mView.findViewById(R.id.bt_confirm);
        dropButton.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        mEtNumber.addTextChangedListener(new NumberTextWatcher());
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_detail_drop:
                if (amount > 1) {
                amount--;
                mEtNumber.setText(amount + "");
            }
                break;
            case R.id.bt_detail_plus:
                if (amount <= goods_storage) {
                    amount++;
                    mEtNumber.setText(amount + "");
                }
                break;
            case R.id.bt_confirm:
                if (amount == 0) {
                    Toast.makeText(mContext , R.string.goods_num_failure , Toast.LENGTH_SHORT).show();
                    return;
                }

                dismiss();
                postDataForNet(addCartUrl , amount , goods_price);
                Toast.makeText(mContext , R.string.cart_add_complete , Toast.LENGTH_SHORT).show();
                break;
        }
        mEtNumber.clearFocus();
    }

    private void postDataForNet(String url ,int id ,int num) {
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addParams("goods_id" , id + "")
                .addParams("goods_num" , num + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
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

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public class NumberTextWatcher implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty())
                return;
            amount = Integer.valueOf(s.toString());
            if (amount > goods_storage) {
                mEtNumber.setText(goods_storage + "");
                return;
            }
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = mActivity.getWindowManager().getDefaultDisplay().getHeight() / 2;
        window.setAttributes(params);

    }
}
