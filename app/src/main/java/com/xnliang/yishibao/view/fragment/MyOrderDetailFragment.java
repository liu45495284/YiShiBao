package com.xnliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.OrderDetailRecycleViewAdapter;
import com.xnliang.yishibao.module.bean.OrderBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyOrderDetailFragment extends BaseFragment {

    private static final String ORDER_DETAIL = "order_detail";
    private List<OrderBean.DataBeanX.DataBean> moduleBeanList = new ArrayList();
    private List categoryList = new ArrayList();
    private View mView;
    private static final String orderIndex = "http://ysb.appxinliang.cn/api/order/all";
    private static final String paidIndex = "http://ysb.appxinliang.cn/api/order/paid";
    private static final String unpaidIndex = "http://ysb.appxinliang.cn/api/order/unpaid";
    private static final String finishIndex = "http://ysb.appxinliang.cn/api/order/finish";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private JSONObject mJsonData;
    private List<OrderBean.DataBeanX.DataBean> mDatasBean;
    private static final int ALL_ORDER = 0;
    private static final int PAID_ORDER = 1;
    private static final int UPPAID_ORDER = 2;
    private static final int FINISH_ORDER = 3;
    private RecyclerView mRecyclerView;
    private OrderDetailRecycleViewAdapter mDetailAdapter;
    private ListDecoration mListDecoration;


    public static final String ARG_PAGE = "ARG_PAGE";
    private boolean IS_LOADED = false;
    private static int mSerial=0;
    private int mTabPos=0;
    private boolean isFirst = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void sendMessage(){
        Message message = loadHandler.obtainMessage();
        message.sendToTarget();
    }

    public void setTabPos(int mTabPos) {
        this.mTabPos = mTabPos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_order_detail , container ,false);
        ButterKnife.bind(this, mView);
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "login");

        Bundle arguments = getArguments();
        int cate = arguments.getInt(ORDER_DETAIL);

        //设置页和当前页一致时加载，防止预加载
        if (isFirst && mTabPos==mSerial) {
            isFirst = false;
            sendMessage();
        }

        return mView;
    }

    public static MyOrderDetailFragment newInstance(int type){
        Bundle args = new Bundle();
        args.putInt(ORDER_DETAIL , type);

        MyOrderDetailFragment detailFragment = new MyOrderDetailFragment();
        detailFragment.setArguments(args);
        return detailFragment;
    }

    private void initView() {

        if(mRecyclerView == null){
            mRecyclerView = mView.findViewById(R.id.rv_my_order_detail);

        }
        if(mDetailAdapter == null){
            mDetailAdapter = new OrderDetailRecycleViewAdapter(getActivity() ,moduleBeanList );
            mRecyclerView.setAdapter(mDetailAdapter);
        }

        if(mListDecoration == null){
            mListDecoration = new ListDecoration(getActivity() ,ListDecoration.VERTICAL_LIST , R.drawable.cart_list_divide);
            mRecyclerView.addItemDecoration(mListDecoration);
        }
        mRecyclerView.setItemAnimator(null);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL ,false);
        mRecyclerView.setLayoutManager(manager);

    }

    private void initData(int cate) {
        moduleBeanList.clear();
        switch (cate) {
            case 0:
                getAllDataFromNet(orderIndex ,1 , 0);
                break;
            case 1:
                getAllDataFromNet(paidIndex ,1 , 1);
                break;
            case 2:
                getAllDataFromNet(unpaidIndex ,1 ,2);
                break;
            case 3:
                getAllDataFromNet(finishIndex ,1 , 3);
                break;
        }
    }

    public void getAllDataFromNet(String url , int page , final int flag){
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .get()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addParams("page" ,page+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                        Toast.makeText(getActivity() ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        processData(response , flag);
                    }
                });
}
    private void processData(String response , int flag) {
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            mJsonData = jsonObject.getJSONObject("data");

            mDatasBean = JSON.parseObject(mJsonData.getString("data") ,
                    new TypeReference<List<OrderBean.DataBeanX.DataBean>>(){}.getType());

            switch (flag){
                case ALL_ORDER:
                    handler.obtainMessage(ALL_ORDER).sendToTarget();
                    break;
                case PAID_ORDER:
                    handler.obtainMessage(PAID_ORDER).sendToTarget();
                    break;
                case UPPAID_ORDER:
                    handler.obtainMessage(UPPAID_ORDER).sendToTarget();
                    break;
                case FINISH_ORDER:
                    handler.obtainMessage(FINISH_ORDER).sendToTarget();
                    break;
            }
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ALL_ORDER:
                    moduleBeanList.addAll(mDatasBean);
                    mDetailAdapter.notifyDataSetChanged();
                    break;
                case PAID_ORDER:
                    moduleBeanList.addAll(mDatasBean);
                    mDetailAdapter.notifyDataSetChanged();
                    break;
                case UPPAID_ORDER:
                    moduleBeanList.addAll(mDatasBean);
                    mDetailAdapter.notifyDataSetChanged();
                    break;
                case FINISH_ORDER:
                    moduleBeanList.addAll(mDatasBean);
                    mDetailAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler loadHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(!IS_LOADED){
                IS_LOADED = true;
                //这里执行加载数据的操作
                initView();
                initData(mTabPos);
                Log.e("tag", "我是page"+(mTabPos+1));
            }
            return;
        }
    };



}
