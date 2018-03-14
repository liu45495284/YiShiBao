package com.xnliang.yishibao.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.CartItemViewAdapter;
import com.xnliang.yishibao.module.adapter.CartRecycleViewAdapter;
import com.xnliang.yishibao.module.bean.CartBean;
import com.xnliang.yishibao.module.bean.ShoppingCartBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;

/**
 * Created by JackLiu on 2018-01-26.
 */

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener , CartItemViewAdapter.CheckInterface{

    private CheckBox mCheckAll;
    private CartItemListener cartItemListener;
    private CartRecycleViewAdapter mCartAdapter;
    private static final String cartIndex = "http://ysb.appxinliang.cn/api/shop/cart";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private static final int INIT_DATA = 0;
    private List<CartBean.DataBean.ListsBean> goodsList;
    private JSONObject mJsonData;
    private String mAmount;
    private TextView mCartTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        sharedPreferencesHelper = new SharedPreferencesHelper(this, "login");
        initView();
        initData();
    }

    private void initData() {
//        data.clear();
//        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
//                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2};
//        for (int i = 0 ; i < iconBanner.length ; i++) {
//            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//            shoppingCartBean.setId(iconBanner[i]);
//            data.add(shoppingCartBean);
//        }

        getDataFormNet(cartIndex);
    }

    private void getDataFormNet(String url) {
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .get()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
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
        mJsonData = jsonObject.getJSONObject("data");
        mAmount = mJsonData.getString("amount");

        goodsList = JSON.parseObject(mJsonData.getString("carts") ,
                new TypeReference<ArrayList<CartBean.DataBean.ListsBean>>(){}.getType());

        handler.obtainMessage(INIT_DATA).sendToTarget();
        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case INIT_DATA:
                    RecyclerView recyclerView = findViewById(R.id.rl_cart_item);
                    mCartAdapter = new CartRecycleViewAdapter(ShoppingCartActivity.this, goodsList);
                    recyclerView.setAdapter(mCartAdapter);

                    recyclerView.addItemDecoration(new ListDecoration(ShoppingCartActivity.this,ListDecoration.VERTICAL_LIST ,R.drawable.list_divide));
        /*
        fix recycleview item width not match_parent
         */
//        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL ,false);
                    LinearLayoutManager manager = new LinearLayoutManager(ShoppingCartActivity.this){
                        @Override
                        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                        }
                    };
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    break;
            }
        }
    };

    private void initView() {
        ImageButton backButton = findViewById(R.id.ib_cart_back);
        mCartTotal = findViewById(R.id.tv_cart_total);
        String amount = String.format(getResources().getString(R.string.cart_item_integral),  0);
        mCartTotal.setText(amount);
        backButton.setOnClickListener(this);
        mCheckAll = findViewById(R.id.cb_all);
        mCheckAll.setOnClickListener(this);
        TextView settlement = findViewById(R.id.tv_cart_settlement);
        settlement.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_cart_back:
                finish();
                break;
            case R.id.cb_all:
                if (goodsList.size() != 0) {
                    if (mCheckAll.isChecked()) {
                        for (int i = 0; i < goodsList.size(); i++) {
                            goodsList.get(i).setChoosed(true);
                        }
                        sharedPreferencesHelper.put("checkAll" , 0);
                    } else {
                        for (int i = 0; i < goodsList.size(); i++) {
                            goodsList.get(i).setChoosed(false);
                        }
                        String amount = String.format(getResources().getString(R.string.cart_item_integral),  0);
                        mCartTotal.setText(amount);
                        sharedPreferencesHelper.put("checkAll" , 1);
                    }
                    cartItemListener.cartItem();

                }
                break;
            case R.id.tv_cart_settlement:
                Intent intent = new Intent(this,OrderSubmitActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void checkGroup(int position, boolean isChecked) {
        goodsList.get(position).setChoosed(isChecked);
        if (isAllCheck()) {
            mCheckAll.setChecked(true);
        }else {
            mCheckAll.setChecked(false);
        }
    }

    @Override
    public void totalPrice(List<String> list) {
        int amount = 0;
        for (int i = 0 ; i < list.size();i++){
            amount = amount + Integer.valueOf(list.get(i));
        }
        String total = String.format(getResources().getString(R.string.cart_item_integral),  amount);
        mCartTotal.setText(total);
    }

    private boolean isAllCheck() {
        for (CartBean.DataBean.ListsBean group : goodsList) {
            if (!group.isChoosed())
                return false;
        } return true;
    }

    public interface CartItemListener{
        void cartItem();
    }

    public void setCartItemListener(CartItemListener cartItemListener){
        this.cartItemListener = cartItemListener;
    }


}
