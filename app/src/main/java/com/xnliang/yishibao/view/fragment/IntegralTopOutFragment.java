package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.SelfListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-27.
 */

public class IntegralTopOutFragment extends BaseFragment implements View.OnClickListener {

    private SelfItemBackListener mListener;

    @Bind(R.id.rl_out_back)
    RelativeLayout mOutBack;
    @Bind(R.id.tv_remain_integral)
    TextView mRemainIntegral;
    @Bind(R.id.et_integral_num)
    EditText mIntegralNum;
    @Bind(R.id.cb_zhifubao)
    CheckBox mZhifuBao;
    @Bind(R.id.cb_wechat)
    CheckBox mWeChat;
    @Bind(R.id.cb_yinlian)
    CheckBox mYinLian;
    @Bind(R.id.et_accout)
    EditText mAccout;
    @Bind(R.id.bt_submit)
    Button mSubmit;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setIntegralOutListener((SelfListActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.integral_top_out ,container ,false);
        ButterKnife.bind(this ,view);
        initView();
        return view;
    }

    private void initView() {
        mOutBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_out_back:
                mListener.viewBackListener();
                break;
        }
    }

    public void setIntegralOutListener(SelfItemBackListener listener){
        this.mListener = listener;
    }
}
