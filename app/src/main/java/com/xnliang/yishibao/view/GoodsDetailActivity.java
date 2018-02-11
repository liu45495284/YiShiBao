package com.xnliang.yishibao.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.HandlerBackUtil;
import com.xnliang.yishibao.presenter.dialog.GoodsNumberDialog;
import com.xnliang.yishibao.view.fragment.GoodsDetailFragment;

/**
 * Created by JackLiu on 2018-02-09.
 */

public class GoodsDetailActivity extends BaseActivity {

    public Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_goods_detail);
        initView();
    }

    private void initView() {
        initFragment();

        ImageButton backButton = findViewById(R.id.ib_detail_back);
        Button intoButton = findViewById(R.id.bt_put_into);
        Button buyButton = findViewById(R.id.bt_buy_now);

        backButton.setOnClickListener(new DetailBackClickListener());
        intoButton.setOnClickListener(new IntoClickListener());
        buyButton.setOnClickListener(new BuyClickListener());
    }

    private void initFragment() {
        GoodsDetailFragment detailFragment = new GoodsDetailFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.goods_detail ,detailFragment ,"detail");
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
            GoodsNumberDialog dialog = new GoodsNumberDialog(mContext , 50);
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
