package com.xnliang.yishibao.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.CartItemViewAdapter;
import com.xnliang.yishibao.module.adapter.CartRecycleViewAdapter;
import com.xnliang.yishibao.module.bean.ShoppingCartBean;
import com.xnliang.yishibao.module.utils.ListDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackLiu on 2018-01-26.
 */

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener , CartItemViewAdapter.CheckInterface{

    public List<ShoppingCartBean> data = new ArrayList();
    private CheckBox mCheckAll;
    private CartItemListener cartItemListener;
    private CartRecycleViewAdapter mCartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initView();
        initData();
    }

    private void initData() {
        data.clear();
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2};
        for (int i = 0 ; i < iconBanner.length ; i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setId(iconBanner[i]);
            data.add(shoppingCartBean);
        }
    }

    private void initView() {
        ImageButton backButton = findViewById(R.id.ib_cart_back);
        backButton.setOnClickListener(this);
        mCheckAll = findViewById(R.id.cb_all);
        mCheckAll.setOnClickListener(this);
        TextView settlement = findViewById(R.id.tv_cart_settlement);
        settlement.setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.rl_cart_item);
        mCartAdapter = new CartRecycleViewAdapter(this, data);
        recyclerView.setAdapter(mCartAdapter);

        recyclerView.addItemDecoration(new ListDecoration(this,ListDecoration.VERTICAL_LIST ,R.drawable.list_divide));
        /*
        fix recycleview item width not match_parent
         */
//        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL ,false);
        LinearLayoutManager manager = new LinearLayoutManager(this){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
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
                if (data.size() != 0) {
                    if (mCheckAll.isChecked()) {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChoosed(true);
                        }
                    } else {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChoosed(false);
                        }
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
        data.get(position).setChoosed(isChecked);
        if (isAllCheck()) {
            mCheckAll.setChecked(true);
        }else {
            mCheckAll.setChecked(false);
        }
    }

    private boolean isAllCheck() {
        for (ShoppingCartBean group : data) {
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
