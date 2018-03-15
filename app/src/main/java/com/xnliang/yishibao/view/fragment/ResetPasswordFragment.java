package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.FromNetDataUtil;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.LoginAndRegisterActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-03-15.
 */

public class ResetPasswordFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.rl_reset_back)
    RelativeLayout mResetBack;
    @Bind(R.id.et_reset_num)
    EditText mResetNum;
    @Bind(R.id.et_reset_code)
    EditText mResetCode;
    @Bind(R.id.bt_send_msg)
    Button mSendMsg;
    @Bind(R.id.et_register_password_num)
    EditText mResetNew;
    @Bind(R.id.bt_reset)
    Button mReset;

    private SelfItemBackListener mListener;
    private MyCountDownTimer myCountDownTimer;
    private static final String mUrl = "http://ysb.appxinliang.cn/api/user/code";
    private static final String mResetInterface ="http://ysb.appxinliang.cn/api/user/forget";
    private static final int SUCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private LinkedHashMap<String ,String> hashMap = new LinkedHashMap<>();
    private String mPhoneNum;
    private String mPassWord;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setLoginBackListener((LoginAndRegisterActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password ,container ,false);
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }

    private void initView( View view) {
        mResetBack.setOnClickListener(this);
        mSendMsg.setOnClickListener(this);
        mReset.setOnClickListener(this);

        mResetNum.addTextChangedListener(new TextWatcher(0));
        mResetCode.addTextChangedListener(new TextWatcher(1));
        mResetNew.addTextChangedListener(new TextWatcher(2));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_reset_back:
                mListener.viewBackListener();
                break;
            case R.id.bt_send_msg:
                send();
                break;
            case R.id.bt_reset:
                reset();
                break;
        }
    }

    private void reset() {
        mPhoneNum = mResetNum.getText().toString();
        String securityNum = mResetCode.getText().toString();
        mPassWord = mResetNew.getText().toString();
        if(mPhoneNum.isEmpty() || securityNum.isEmpty() || mPassWord.isEmpty()){
            Toast.makeText(getActivity() ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils
                .post()
                .url(mResetInterface)
                .addParams("username" , mPhoneNum)
                .addParams("verification_code" ,securityNum)
                .addParams("password" , mPassWord)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
//                        Toast.makeText(getActivity() ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        processData(response);
                    }
                });

    }

    private void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE){
            Toast.makeText(getActivity() ,msg ,Toast.LENGTH_SHORT).show();
            return;
        }

        if(Integer.parseInt(code) == SUCESSFUL_CODE){
            mListener.viewBackListener();
            Toast.makeText(getActivity() ,msg ,Toast.LENGTH_SHORT).show();
        }


    }

    public void send(){
        int length = mResetNum.getText().length();
        String registerPhone = mResetNum.getText().toString();

        if(length != 11){
            Toast.makeText(getActivity() ,R.string.please_input_right_phone_num ,Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("username" ,registerPhone);
        FromNetDataUtil.getIntance().postDataFromNet(mUrl ,hashMap);

        myCountDownTimer=new MyCountDownTimer(60000,1000);
        //开始倒计时
        myCountDownTimer.start();
    }


    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String skip = String .valueOf(millisUntilFinished/1000) ;
            mSendMsg.setBackgroundResource(R.color.gray_cc);
            mSendMsg.setText(skip + "s");
            mSendMsg.setOnClickListener(null);
        }

        @Override
        public void onFinish() {
            myCountDownTimer.cancel();
            mSendMsg.setBackgroundResource(R.color.red);
            mSendMsg.setText(R.string.send_security_code);
            mSendMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    send();
                }
            });
        }
    }

    public void setLoginBackListener(SelfItemBackListener listener){
        this.mListener = listener;
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
