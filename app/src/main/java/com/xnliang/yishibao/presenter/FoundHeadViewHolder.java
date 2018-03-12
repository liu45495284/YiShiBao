package com.xnliang.yishibao.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.FoundListBean;
import com.xnliang.yishibao.view.MainActivity;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class FoundHeadViewHolder extends BaseViewHolder {

    private final Context mContext;
    private MainActivity mActivity;
    private final Banner banner;
    private static final String mBarnnerIndex ="http://ysb.appxinliang.cn/api/banner";
    private List bannerList = new ArrayList();
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private static final int SUCCESSFUL = 0;

    public FoundHeadViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mActivity = (MainActivity) context;
        banner = itemView.findViewById(R.id.banner_found);
        //fix recyclerView auto scroll bug
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();

    }

    public void setData() {
        initData(mBarnnerIndex);
    }

    private void initData(String url) {
        OkHttpUtils
                .get()
                .url(url)
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

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);
        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        JSONArray jsonData = jsonObject.getJSONArray("data");

        for(int i =0 ; i < jsonData.size() ; i++){
            bannerList.add(jsonData.getJSONObject(i).getString("image"));
        }

        handler.obtainMessage(SUCCESSFUL).sendToTarget();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESSFUL:

                    //使用Glide加载图片
                    //新版的banner的使用----偷下懒的使用方法
                    banner.setImages(bannerList).setImageLoader(new GlideImageLoader()).start();

                    break;
            }
        }
    };
}
