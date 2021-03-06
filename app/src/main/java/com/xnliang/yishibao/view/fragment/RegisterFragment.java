package com.xnliang.yishibao.view.fragment;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.db.UserDbHelp;
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
 * Created by JackLiu on 2018-02-28.
 */

public class RegisterFragment extends BaseFragment implements View.OnClickListener{

    private SelfItemBackListener mListener;
    private LoginAndRegisterActivity mActivity;
    private MyCountDownTimer myCountDownTimer;
    private static final String mUrl = "http://ysb.appxinliang.cn/api/user/code";
    private static final String mRegisterInterface ="http://ysb.appxinliang.cn/api/user/register";
    private static final int SUCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private LinkedHashMap<String ,String> hashMap = new LinkedHashMap<>();

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
    private UserDbHelp dbHelper;
    private String mPhoneNum;
    private String mPassWord;

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

        dbHelper = new UserDbHelp(getActivity(),"UserInfo.db",null,1);
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
                register();
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_privacy:
                break;
        }
    }

    private void register() {
        mPhoneNum = mRegisterPhone.getText().toString();
        String securityNum = mRegisterCode.getText().toString();
        mPassWord = mPasswordNum.getText().toString();
        String passAgain = mPasswordAgain.getText().toString();
        if(mPhoneNum.isEmpty() || securityNum.isEmpty() || mPassWord.isEmpty() || passAgain.isEmpty()){
            Toast.makeText(mActivity ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
            return;
        }

        if(!mPassWord.equalsIgnoreCase(passAgain)){
            Toast.makeText(mActivity ,R.string.register_pass_failure ,Toast.LENGTH_SHORT).show();
            return;
        }

            OkHttpUtils
                    .post()
                    .url(mRegisterInterface)
                    .addParams("username" , mPhoneNum)
                    .addParams("verification_code" ,securityNum)
                    .addParams("password" , mPassWord)
                    .addParams("chk_password" ,passAgain)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("TAG", "联网失败" + e.getMessage());
                            Toast.makeText(mActivity ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
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

    //向数据库插入数据
//    public boolean register(String username,String password){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        /*String sql = "insert into userData(name,password) value(?,?)";
//        Object obj[]={username,password};
//        db.execSQL(sql,obj);*/
//        ContentValues values=new ContentValues();
//        values.put("name",username);
//        values.put("password",password);
//        db.insert("userData",null,values);
//        db.close();
//        //db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
//        return true;
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    public void send(){
        int length = mRegisterPhone.getText().length();
        String registerPhone = mRegisterPhone.getText().toString();

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

    private void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE){
            Toast.makeText(mActivity ,msg ,Toast.LENGTH_SHORT).show();
            return;
        }

        if(Integer.parseInt(code) == SUCESSFUL_CODE){
            mListener.viewBackListener();
//            if (register(mPhoneNum, mPassWord)) {
//                Log.i("TAG" ,"插入数据表成功");
//            }
            Toast.makeText(mActivity ,msg ,Toast.LENGTH_SHORT).show();
        }


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
}
