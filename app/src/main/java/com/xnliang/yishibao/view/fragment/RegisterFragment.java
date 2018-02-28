package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.LoginAndRegisterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class RegisterFragment extends BaseFragment implements View.OnClickListener{

    private SelfItemBackListener mListener;

    @Bind(R.id.rl_register_back)
    RelativeLayout mRegBack;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setLoginBackListener((LoginAndRegisterActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register , container ,false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mRegBack.setOnClickListener(this);
    }

    public void setLoginBackListener(SelfItemBackListener listener){
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_register_back:
                mListener.viewBackListener();
                break;
        }
    }
}
