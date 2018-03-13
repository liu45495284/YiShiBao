package com.xnliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;

/**
 * Created by JackLiu on 2018-02-08.
 */

public class ShopContainerFragment extends BaseFragment  {

    private boolean isInit;
    private static final int CMD_INIT = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_container , container ,false);
        initFragment();
        isInit = false;
        return view;
    }

    public void initFragment() {

        if (!isInit) {
            handler.sendEmptyMessageDelayed(CMD_INIT, 0);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case CMD_INIT:
                    ShopFragment shopFragment = new ShopFragment();
                    FragmentManager manager = getChildFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_home_container ,
                            shopFragment,"shop");
                    transaction.addToBackStack("shop");
                    transaction.commit();
                    isInit = true;
                    break;
            }
        }
    };
}
