package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-27.
 */

public class MyPersonalDataFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.rl_person_back)
    RelativeLayout mBackLayout;
    @Bind(R.id.rl_head)
    RelativeLayout mHead;
    @Bind(R.id.et_person_name)
    EditText mPersonName;
    @Bind(R.id.et_person_phone)
    EditText mPersonPhone;
    @Bind(R.id.bt_person_save)
    Button mPersonSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_data , container ,false);
        ButterKnife.bind(this , view);
        initView();
        return view;
    }

    private void initView() {
        mBackLayout.setOnClickListener(this);
        mHead.setOnClickListener(this);
        mPersonSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_person_back:
                getActivity().finish();
                break;
            case R.id.rl_head:
                //TODO
                break;
            case R.id.bt_person_save:
                //TODO
                break;
        }
    }
}
