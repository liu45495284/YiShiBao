package com.xnliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.CoinBean;
import com.xnliang.yishibao.module.bean.TeamBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.SelfListActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class IntegralAndCashListFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.rl_list_back)
    RelativeLayout mListBack;
    @Bind(R.id.tv_list_name)
    TextView mlistName;
    @Bind(R.id.rv_list)
    RecyclerView mListRecyclerView;

    private SelfItemBackListener mListener;
    private static final String coinIndex = "http://ysb.appxinliang.cn/api/user/log/coin";
    private static final String scoreIndex = "http://ysb.appxinliang.cn/api/user/log/score";
    private static final String withdrawIndex = "http://ysb.appxinliang.cn/api/user/withdraw/lists";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private JSONObject mJsonData;
    private List<CoinBean.DataBeanX.DataBean> mDataBeans;
    private int mStyle;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListBack((SelfListActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_integral_and_cash ,container ,false);
        ButterKnife.bind(this ,view);
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "login");
//        initView();
        Intent intent = getActivity().getIntent();
        mStyle = intent.getIntExtra("list" , 0);
        initData();
        return view;
    }

    private void initData() {
        switch (mStyle){
            case 1:
                getDataFromNet(coinIndex , 1);
                break;
            case 2:
                getDataFromNet(scoreIndex , 1);
                break;
            case 3:
                getDataFromNet(withdrawIndex , 1);
                break;
        }
    }

    private void getDataFromNet(String url ,int page) {
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
                        processData(response);
                    }
                });
    }

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            return;
        }


        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            mJsonData = jsonObject.getJSONObject("data");
            mDataBeans = JSON.parseObject(mJsonData.getString("data") ,
                    new TypeReference<List<CoinBean.DataBeanX.DataBean>>(){}.getType());


            handler.obtainMessage();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            initView();
        }
    };

    private void initView() {
        mListBack.setOnClickListener(this);

        if (mStyle == 0){
            return;
        }
        switch (mStyle){
            case 1:
                mlistName.setText(R.string.integral_cash_list);
                break;
            case 2:
                mlistName.setText(R.string.integral_shop_list);
                break;
            case 3:
                mlistName.setText(R.string.cash_list);
                break;
        }

        MyAdapter adapter = new MyAdapter(getActivity() ,mDataBeans , mStyle);

        mListRecyclerView.setAdapter(adapter);
        mListRecyclerView.addItemDecoration(new ListDecoration(getActivity() ,ListDecoration.VERTICAL_LIST , R.drawable.list_divide));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity() ,LinearLayoutManager.VERTICAL ,false);
        mListRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_list_back:
                mListener.viewBackListener();
                break;

        }
    }

    public void setListBack(SelfItemBackListener listener){
        this.mListener = listener;
    }

    class MyAdapter extends RecyclerView.Adapter{

        private Context mContext;
        private List<CoinBean.DataBeanX.DataBean> mData;
        private int mStyle;
        public MyAdapter(Context context ,List<CoinBean.DataBeanX.DataBean> data , int style) {
            this.mContext = context;
            this.mData = data;
            this.mStyle = style;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder mHolder = new MyViewHolder((LayoutInflater.from(getActivity()).inflate(R.layout.self_integral_cash_item ,parent,false)));
            return mHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder != null){
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.setData(mData , position ,mStyle);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_item_desc)
        TextView mItemDesc;
        @Bind(R.id.tv_item_time)
        TextView mItemTime;
        @Bind(R.id.tv_item_style)
        TextView mItemStyle;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }

        public void setData(List<CoinBean.DataBeanX.DataBean> data , int position ,int style) {

            switch (style) {
                case 1:
                    int integral = data.get(position).getScore();
                    String time = data.get(position).getCreate_time();
                    String msg = data.get(position).getMsg();
                    mItemDesc.setText(integral+"");
                    mItemTime.setText(time);
                    mItemStyle.setText(msg);
                    break;
                case 2:
                    int shopIntegral = data.get(position).getScore();
                    String shopTime = data.get(position).getCreate_time();
                    String shopMsg = data.get(position).getMsg();
                    mItemDesc.setText(shopIntegral+"");
                    mItemTime.setText(shopTime);
                    mItemStyle.setText(shopMsg);
                    break;
                case 3:
                    int tixian = data.get(position).getScore();
                    String tixianTime = data.get(position).getCreate_time();
                    String status = data.get(position).getStatus();

                    mItemDesc.setText(tixian+"");
                    mItemTime.setText(tixianTime);
                    mItemStyle.setText(status);
                    break;

            }
        }
    }
}
