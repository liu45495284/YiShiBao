package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.FromNetDataUtil;
import com.xnliang.yishibao.presenter.ResponseCallBackListener;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener ,ResponseCallBackListener {

    private SelfItemBackListener mListener;
    private static final String mLoginInterface ="http://ysb.appxinliang.cn/api/user/login";
    private int mFlag;
    private static final int RESPONSE_CALLBACK = 1;
    private static final int SUCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;

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
                login();
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

    private void login() {
        String phoneNum = mPhoneNum.getText().toString();
        String passNum = mPassNum.getText().toString();
        if(phoneNum.isEmpty() || passNum.isEmpty()){
            Toast.makeText(getActivity() ,R.string.login_failure , Toast.LENGTH_SHORT).show();
        }

        LinkedHashMap<String ,String> hashMap = new LinkedHashMap();
        hashMap.put("username",phoneNum);
        hashMap.put("password" ,passNum);
        hashMap.put("device_type" ,"android");

        FromNetDataUtil.getIntance().postDataFromNet(mLoginInterface ,hashMap);
        FromNetDataUtil.getIntance().setResponseListener(this);
        if(mFlag == RESPONSE_CALLBACK){
            String response = FromNetDataUtil.getIntance().getResponse();
            FromNetDataUtil.getIntance().processData(response ,"login");
        }
    }

    @Override
    public boolean onBackPressed() {
        getActivity().finish();
        return true;
    }

    @Override
    public void getResponseCallBack(int flag) {
        this.mFlag = flag;
    }

    @Override
    public void getResponseData(String code, String msg) {

        if(Integer.parseInt(code) == FAILURE_CODE){
            Toast.makeText(getActivity() ,msg ,Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(code) == SUCESSFUL_CODE){
            Toast.makeText(getActivity() ,msg ,Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }


    }
}
