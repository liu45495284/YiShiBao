package com.xnliang.yishibao.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.CartRecycleViewAdapter;
import com.xnliang.yishibao.module.utils.ListDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackLiu on 2018-01-26.
 */

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener {

    public List data = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {
        ImageButton backButton = findViewById(R.id.ib_cart_back);
        backButton.setOnClickListener(this);
        RecyclerView recyclerView = new RecyclerView(this);
        CartRecycleViewAdapter adapter = new CartRecycleViewAdapter(this, data);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new ListDecoration(this,ListDecoration.VERTICAL_LIST ,R.drawable.list_divide));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL ,false);
        recyclerView.setLayoutManager(layoutManager);
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
        }
    }
}
