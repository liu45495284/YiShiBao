package com.xnliang.yishibao.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.SubmitOrderRecycleViewAdapter;
import com.xnliang.yishibao.module.utils.ListDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackLiu on 2018-02-23.
 */

public class OrderSubmitActivity extends BaseActivity implements View.OnClickListener {

    public List data = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_submit);
        initView();
        initData();
    }

    private void initData() {
        data.clear();
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2};
        for (int i = 0 ; i < iconBanner.length ; i++) {
            data.add(iconBanner[i]);
        }
    }

    private void initView() {
        ImageButton backButton = findViewById(R.id.ib_submit_back);
        backButton.setOnClickListener(this);
        TextView submitOrder = findViewById(R.id.tv_submit_order);
        submitOrder.setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.rv_submit_item);
        SubmitOrderRecycleViewAdapter submitAdapter = new SubmitOrderRecycleViewAdapter(this , data);
        recyclerView.setAdapter(submitAdapter);

        recyclerView.addItemDecoration(new ListDecoration(this,ListDecoration.VERTICAL_LIST ,R.drawable.cart_list_divide));
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_submit_back:
                finish();
                break;
            case R.id.tv_submit_order:
                Intent intent = new Intent(this , OrderPayActivity.class);
                startActivity(intent);
                break;
        }
    }
}
