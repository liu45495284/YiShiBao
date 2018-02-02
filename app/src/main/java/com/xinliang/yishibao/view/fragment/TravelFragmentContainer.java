package com.xinliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xinliang.yishibao.R;
import com.xinliang.yishibao.presenter.ItmeCallBackListener;

/**
 * Created by JackLiu on 2018-02-02.
 */

public class TravelFragmentContainer extends BaseFragment {

    private FragmentManager mFragementManager;
    private boolean isInit;
    private static final int CMD_INIT = 0;
    private static final int DETAIL_ADD = 1;
    private TravelFragment mTravelFragment;
    private Fragment mTravelDetailFragment;
    private FragmentTransaction mFragmentTransaction;

    public TravelFragmentContainer() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_travel_container ,container , false);
        isInit = false;
        initFragment();
        return rootView;
    }

    public void initFragment() {

        if (!isInit) {
            handler.sendEmptyMessageDelayed(CMD_INIT, 1000);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case CMD_INIT:
                    mTravelFragment = new TravelFragment();
                    mFragementManager = getChildFragmentManager();
                    mFragmentTransaction = mFragementManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.fragment_container ,
                            mTravelFragment,"travel");
                    mFragmentTransaction.addToBackStack("travel");
                    mFragmentTransaction.commitAllowingStateLoss();
                    isInit = true;
                    break;
            }
        }
    };
}
