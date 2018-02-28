package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.SelfListActivity;
import com.xnliang.yishibao.view.SettingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyModifyFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.rl_modify_back)
    RelativeLayout mModifyBack;
    @Bind(R.id.et_modify_old)
    EditText mOld;
    @Bind(R.id.et_modify_new)
    EditText mNew;
    @Bind(R.id.et_modify_again)
    EditText mAgain;

    private SelfItemBackListener mListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setmModifyBack((SelfListActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_modify_password , container ,false);
        ButterKnife.bind(this ,view);
        initView();
        return view;
    }

    private void initView() {
        mModifyBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_modify_back:
                mListener.viewBackListener();
                break;
            case R.id.et_modify_old:
                break;
            case R.id.et_modify_new:
                break;
            case R.id.et_modify_again:
                break;
        }
    }

    public void setmModifyBack(SelfItemBackListener listener){
        this.mListener = listener;
    }
}
