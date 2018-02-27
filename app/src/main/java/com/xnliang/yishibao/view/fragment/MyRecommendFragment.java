package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyRecommendFragment extends BaseFragment {

    private View mView;
    @Bind(R.id.rl_recommend_back)
    RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_recommend , container ,false);
        ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
