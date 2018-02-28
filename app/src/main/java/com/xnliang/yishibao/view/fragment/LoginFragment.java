package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xnliang.yishibao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.et_login_phone_num)
    EditText mPhoneNum;
    @Bind(R.id.et_login_password_num)
    EditText mPassNum;
    @Bind(R.id.bt_login)
    Button mLogin;
    @Bind(R.id.tv_forget)
    TextView mForget;
    @Bind(R.id.tv_register)
    TextView mRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login , container ,false);
        ButterKnife.bind(this ,view);
        initView();
        return view;
    }

    private void initView() {
        mLogin.setOnClickListener(this);
        mForget.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                //TODO
                break;
            case R.id.tv_forget:
                //TODO
                break;
            case R.id.tv_register:
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.login_container ,registerFragment ,"register");
                transaction.addToBackStack("register");
                transaction.commit();
                break;
        }
    }
}
