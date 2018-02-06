package com.xnliang.yishibao.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.fragment.TravelDetailFragment;
import com.xnliang.yishibao.module.utils.HandlerBackUtil;
/**
 * Created by JackLiu on 2018-02-02.
 */

public class TravelDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_base);
        initView();
    }

    private void initView() {
        TravelDetailFragment travelDetailFragment = new TravelDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_base , travelDetailFragment , "detail");
        fragmentTransaction.addToBackStack("detail");
        fragmentTransaction.commitAllowingStateLoss();
//        Button detailBack = findViewById(R.id.bt_detail);
//        TextView cityName = findViewById(R.id.tv_travel_detail_name);
//        Button snapUp = findViewById(R.id.bt_snap_up);
//        detailBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        snapUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (!HandlerBackUtil.handleBackPress(this)) {
            super.onBackPressed();
        }
    }
}
