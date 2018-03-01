package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.FromNetDataUtil;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.LoginAndRegisterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class RegisterFragment extends BaseFragment implements View.OnClickListener{

    private SelfItemBackListener mListener;
    private LoginAndRegisterActivity mActivity;
    private String mUrl = "http://ysb.appxinliang.cn/api/user/code";

    @Bind(R.id.rl_register_back)
    RelativeLayout mRegBack;
    @Bind(R.id.et_register_num)
    EditText mRegisterPhone;
    @Bind(R.id.et_register_code)
    EditText mRegisterCode;
    @Bind(R.id.bt_send_msg)
    Button mSendMsg;
    @Bind(R.id.et_register_password_num)
    EditText mPasswordNum;
    @Bind(R.id.et_register_password_num_again)
    EditText mPasswordAgain;
    @Bind(R.id.bt_register)
    Button mRegister;
    @Bind(R.id.tv_register)
    TextView mPersonServer;
    @Bind(R.id.tv_privacy)
    TextView mPrivacy;
    private String mMumber;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setLoginBackListener((LoginAndRegisterActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (LoginAndRegisterActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_register , container ,false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mRegBack.setOnClickListener(this);
        mSendMsg.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mPersonServer.setOnClickListener(this);
        mPrivacy.setOnClickListener(this);

        mRegisterPhone.addTextChangedListener(new TextWatcher(0));
        mRegisterCode.addTextChangedListener(new TextWatcher(1));
        mPasswordNum.addTextChangedListener(new TextWatcher(2));
        mPasswordAgain.addTextChangedListener(new TextWatcher(3));

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
            case R.id.bt_send_msg:
                //TODO
                send();
                break;
            case R.id.bt_register:
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_privacy:
                break;
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void send(){
        int length = mRegisterPhone.getText().length();
        if(length != 11){
            Toast.makeText(getActivity() ,R.string.please_input_right_phone_num ,Toast.LENGTH_SHORT).show();
            return;
        }
        FromNetDataUtil.getIntance().postDataFromNet(mUrl , "username" , mRegisterPhone.getText().toString());

    }

    private void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

        String data = jsonObject.getString("data");
        JSONObject jsonData = JSON.parseObject(data);

        String module = jsonData.getString("module");
    }

    class TextWatcher implements android.text.TextWatcher{
        private int mFlag;

        public TextWatcher(int flag) {
            this.mFlag = flag;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
