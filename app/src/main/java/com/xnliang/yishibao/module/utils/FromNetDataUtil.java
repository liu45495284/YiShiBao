package com.xnliang.yishibao.module.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xnliang.yishibao.presenter.ResponseCallBackListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.LinkedHashMap;

import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class FromNetDataUtil {

    private static FromNetDataUtil instance;
//    private ResponseCallBackListener mListener;
    private String response;
    private int RESPONSE_FLAG = 1;

    public static synchronized FromNetDataUtil getIntance(){
        if (instance == null){
            synchronized (FromNetDataUtil.class){
                if(instance == null){
                    instance = new FromNetDataUtil();
                }
            }
        }
        return instance;
    }

    public void getDataFromNet(String url) {
        url = "http://api/user/register";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);

                        //联网成功后使用fastjson解析
//                        processData(response);

                    }
                });
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
//                        mListener.getResponseCallBack(RESPONSE_FLAG);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);

                        setResponse(response);

                    }
                });
    }


    public void processData(String json , String flag) {
        JSONObject jsonObject = JSON.parseObject(json);

        if (flag.equals("login")){
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            JSONObject jsonData = jsonObject.getJSONObject("data");
                String token = jsonData.getString("token");
                JSONObject jsonUser = jsonData.getJSONObject("user");
                    String score = jsonUser.getString("score");
                    String coin = jsonUser.getString("coin");
                    String userNickName = jsonUser.getString("user_nickname");
                    String avatar = jsonUser.getString("avatar");
                    String mobile = jsonUser.getString("mobile");
                    String position = jsonUser.getString("position");

//            PersonDetailBean detailBean = new PersonDetailBean(Integer.parseInt(score) ,Integer.parseInt(coin)
//                    ,userNickName,avatar,mobile,position);

//            mListener.getResponseData(code ,msg);
        }
}

    public String getResponse(){
        return response;
    }

    public void setResponse(String response){
        this.response = response;
    }

    public void setResponseListener(ResponseCallBackListener listener){
//        this.mListener = listener;
    }
}