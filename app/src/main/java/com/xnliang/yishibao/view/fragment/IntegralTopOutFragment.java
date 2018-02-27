package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;

import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-27.
 */

public class IntegralTopOutFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.integral_top_out ,container ,false);
        ButterKnife.bind(this ,view);
        return view;
    }
}
