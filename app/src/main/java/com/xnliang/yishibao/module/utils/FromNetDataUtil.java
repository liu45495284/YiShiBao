package com.xnliang.yishibao.module.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.FormBody;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class FromNetDataUtil {

    private static FromNetDataUtil instance;

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
                        processData(response);
                    }
                });
    }

    public void postDataFromNet(String url ,String tag , String value){

        FormBody formBody = new FormBody
                .Builder()
                .add(tag, value)
                .build();

        OkHttpUtils
                .post()
                .url(url)
                .addParams(tag ,value)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                    }
                });
    }


    private void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

        String data = jsonObject.getString("data");
        JSONObject jsonData = JSON.parseObject(data);

        String module = jsonData.getString("module");
}
}