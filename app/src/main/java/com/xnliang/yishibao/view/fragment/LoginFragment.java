package com.xnliang.yishibao.view.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.db.UserDbHelp;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.view.MainActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private static final String mLoginInterface ="http://ysb.appxinliang.cn/api/user/login";
    private int mFlag;
    private static final int RESPONSE_CALLBACK = 1;
    private static final int SUCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private UserDbHelp dbHelper;
    private SharedPreferencesHelper sharedPreferencesHelper;


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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "login");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login , container ,false);
        ButterKnife.bind(this ,view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        dbHelper = new UserDbHelp(getActivity(),"UserInfo.db",null,1);
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
            return;
        }

        LinkedHashMap<String ,String> hashMap = new LinkedHashMap();
        hashMap.put("username",phoneNum);
        hashMap.put("password" ,passNum);
        hashMap.put("device_type" ,"android");

        postDataFromNet(mLoginInterface ,hashMap);

    }

    public void postDataFromNet(String url , LinkedHashMap<String ,String> hashMap){

//        FormBody formBody = new FormBody
//                .Builder()
//                .add(tag, value)
//                .build();

        OkHttpUtils
                .post()
                .url(url)
                .params(hashMap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);

                        processData(response);
                    }
                });
    }

    public void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");

        if(Integer.parseInt(code) == FAILURE_CODE){
            Toast.makeText(getActivity() ,msg ,Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(code) == SUCESSFUL_CODE){
            JSONObject jsonData = jsonObject.getJSONObject("data");
            String token = jsonData.getString("token");
            JSONObject jsonUser = jsonData.getJSONObject("user");
            String score = jsonUser.getString("score");
            String coin = jsonUser.getString("coin");
            String userNickName = jsonUser.getString("user_nickname");
            String avatar = jsonUser.getString("avatar");
            String mobile = jsonUser.getString("mobile");
            String position = jsonUser.getString("position");

            insertUserData(userNickName,mobile,coin,score,avatar);
            Toast.makeText(getActivity() ,msg ,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),MainActivity.class);
            getActivity().startActivity(intent);

            sharedPreferencesHelper.put("isLogin",true);
            sharedPreferencesHelper.put("token" ,token);
        }
    }

    //向数据库插入数据
    public void insertUserData(String nickname ,String mobile,String coin,String score,String avatar){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        db.execSQL("delete from userDetail");
        ContentValues values=new ContentValues();
        values.put("name",nickname);
        values.put("mobile",mobile);
        values.put("coin",coin);
        values.put("score",score);
        values.put("avatar",avatar);
        db.insert("userDetail",null,values);
        db.close();
    }

    @Override
    public boolean onBackPressed() {
        getActivity().finish();
        return true;
    }

}
