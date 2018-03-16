package com.xnliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.AddressManagerBean;
import com.xnliang.yishibao.module.bean.CoinBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyAddressFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.rl_address_back)
    RelativeLayout relativeLayout;
    @Bind(R.id.bt_confirm)
    Button confirmButton;
    @Bind(R.id.rv_address)
    RecyclerView recyclerView;

    private static final String addressManger = "http://ysb.appxinliang.cn/api/user/address";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private JSONObject mJsonData;
    private List<AddressManagerBean.DataBeanX.DataBean> mDataBeans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_path_choose , container ,false);
        ButterKnife.bind(this,view);
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "login");
        initView();
        initData();
        return view;
    }

    private void initView() {
        relativeLayout.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    private void initData() {
        getDataFromNet(addressManger , 1);
    }

    private void getDataFromNet(String url, int page) {
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
                    new TypeReference<List<AddressManagerBean.DataBeanX.DataBean>>(){}.getType());


            handler.obtainMessage();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            MyAdapter adapter = new MyAdapter(getActivity() ,mDataBeans);

            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new ListDecoration(getActivity() , ListDecoration.VERTICAL_LIST ,R.drawable.cart_list_divide));
            LinearLayoutManager manager = new LinearLayoutManager(getActivity() ,LinearLayoutManager.VERTICAL ,false);
            recyclerView.setLayoutManager(manager);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_address_back:
                getActivity().finish();
                break;
            case R.id.bt_confirm:

                AddressEditFragment editFragment = new AddressEditFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.self_container ,editFragment ,"edit");
                transaction.addToBackStack("edit");
                                           transaction.commit();
                break;
        }
    }

    class MyAdapter extends RecyclerView.Adapter{

        private Context mContext;
        private List<AddressManagerBean.DataBeanX.DataBean> mData;
        public MyAdapter(Context context , List<AddressManagerBean.DataBeanX.DataBean> data) {
            this.mContext = context;
            this.mData = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.self_address_item ,null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder != null){
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.setData(mData , position);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.cb_address)
        CheckBox mCheckBox;
        @Bind(R.id.tv_address_consignee)
        TextView mConsignee;
        @Bind(R.id.tv_address_phone)
        TextView mPhone;
        @Bind(R.id.tv_address_desc)
        TextView mAddress;
        @Bind(R.id.iv_address_edit)
        ImageView mEdit;
        @Bind(R.id.tv_default_address)
        TextView mDefault;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public void setData(List<AddressManagerBean.DataBeanX.DataBean> data ,int position){
            //TODO
            String address = data.get(position).getAddress();
            String consignee = data.get(position).getConsignee();
            String mobile = data.get(position).getMobile();
            int isDefault = data.get(position).getIs_default();
            final int id = data.get(position).getId();

            mConsignee.setText(consignee);
            mPhone.setText(mobile);
            mAddress.setText(address);
            mDefault.setText(isDefault);

            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("address" , id);
                    AddressEditFragment editFragment = new AddressEditFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.self_container ,editFragment ,"edit");
                    transaction.addToBackStack("edit");
                    transaction.commit();
                }
            });
        }
    }
}
