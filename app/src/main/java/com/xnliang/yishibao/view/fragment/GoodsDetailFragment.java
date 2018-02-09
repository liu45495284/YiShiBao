package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;

/**
 * Created by JackLiu on 2018-02-09.
 */

public class GoodsDetailFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_detail , container , false);
        return view;
    }

    @Override
    public boolean onBackPressed() {
        getActivity().finish();
        return true;
    }
}
