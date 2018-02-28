package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class RegisterFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register , container ,false);
        return view;
    }
}
