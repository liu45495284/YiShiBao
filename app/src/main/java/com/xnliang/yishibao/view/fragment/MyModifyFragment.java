package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyModifyFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_modify_password , container ,false);
        return view;
    }
}
