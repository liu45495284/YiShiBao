package com.xnliang.yishibao.module;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by JackLiu on 2018-01-29.
 */

public class HomeRecycleViewDate {
    /**
     * 使用okhttpUtils进行联网请求数据
     */
    private void getDataFromNet() {
        String url = "http://api/user/register";
        OkHttpUtils.
                get()
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

    // fastjson解析数据
    private void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

        String data = jsonObject.getString("data");
        JSONObject jsonData = JSON.parseObject(data);

        String module = jsonData.getString("module");
//        List<WomenBean.WomenData.ModuleBean> moduleBeanList = parseArray(module, WomenBean.WomenData.ModuleBean.class);
        //测试是否解析数据成功
        //String strTest=moduleBeanList.get(0).getC_title();
        //Log.e("TAG",strTest);

//        if (moduleBeanList != null) {
//            //有数据
//            //设置适配器
//            homeRecycleAdapter = new HomeRecycleViewAdapter(mContext, moduleBeanList);
//            rvHome.setAdapter(homeRecycleAdapter);
//
//            //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
//            //第一个参数是上下文，第二个参数是只有一列
//            GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
//            rvHome.setLayoutManager(manager);
//        }
    }


}
