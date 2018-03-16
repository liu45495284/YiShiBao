package com.xnliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.TeamBean;
import com.xnliang.yishibao.module.bean.TeamMemberBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.SelfListActivity;
import com.xnliang.yishibao.view.customview.RoundImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-26.
 */

public class MyTeamMemberFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.rl_team_member_back)
    RelativeLayout relativeLayout;
    @Bind(R.id.rv_my_team_member)
    RecyclerView mMemberView;
    private View mView;
    private SelfItemBackListener mListener;
    private static final String teamIndex = "http://ysb.appxinliang.cn/api/user/team/member";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private JSONObject mJsonData;
    private List<TeamMemberBean.DataBeanX.DataBean> mDataBeans;

    public MyTeamMemberFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setMemberBack((SelfListActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.self_my_team_member ,container ,false);
        ButterKnife.bind(this, mView);
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "login");
        initData();
        return mView;
    }

    private void initView() {
        relativeLayout.setOnClickListener(this);

        MyAdapter adapter = new MyAdapter(getActivity() ,mDataBeans);

        mMemberView.setAdapter(adapter);
        mMemberView.addItemDecoration(new ListDecoration(getActivity() ,ListDecoration.VERTICAL_LIST , R.drawable.list_divide));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity() ,LinearLayoutManager.VERTICAL ,false);
        mMemberView.setLayoutManager(manager);
    }

    private void initData() {
        getDataFormNet(teamIndex , 1);
    }

    private void getDataFormNet(String url ,int page) {
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
                    new TypeReference<List<TeamMemberBean.DataBeanX.DataBean>>(){}.getType());

            handler.obtainMessage();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            initView();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_team_member_back:
                mListener.viewBackListener();
                break;

        }
    }


    public void setMemberBack(SelfItemBackListener listener){
        this.mListener = listener;
    }

    class MyAdapter extends RecyclerView.Adapter{


        private Context mContext;
        private List<TeamMemberBean.DataBeanX.DataBean> mData;

        public MyAdapter(Context context , List<TeamMemberBean.DataBeanX.DataBean> data) {
            this.mContext = context;
            this.mData = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder mHolder = new MyViewHolder((LayoutInflater.from(getActivity()).inflate(R.layout.self_my_team_member_item ,null)));
            return mHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder != null){
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.setData(mData ,position);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final RoundImageView mImage;
        private final TextView mName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.iv_team_head);
            mName = itemView.findViewById(R.id.tv_team_name);

        }

        public void setData(List<TeamMemberBean.DataBeanX.DataBean> data ,int position) {
            String image = data.get(position).getAvatar();
            String name = data.get(position).getUser_nickname();

            Glide.with(getActivity())
                    .load(image)
                    .into(mImage);

            mName.setText(name);
        }
    }
}
