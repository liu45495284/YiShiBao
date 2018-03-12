package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.FoundListBean;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.view.MainActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;

/**
 * Created by JackLiu on 2018-01-30.
 */

public class FoundItemRecycleViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final LinkedHashMap<String , List> mData;
    private MyViewHolder mHolder;
    private MainActivity mActivity;
    private DividerGridItemDecoration mListDecoration;
    private static final String mHeartIndex ="http://ysb.appxinliang.cn/api/discover/like";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private List<FoundListBean.DataBeanX.ListsBean.DataBean> mListData;

    public FoundItemRecycleViewAdapter(Context context, LinkedHashMap<String , List> data) {
        this.mContext = context;
        this.mActivity = (MainActivity)context;
        this.mData = data;
        sharedPreferencesHelper = new SharedPreferencesHelper(mActivity, "login");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.found_content, null);
        mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(mData ,position);

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        List<FoundListBean.DataBeanX.ListsBean.DataBean> listData = mData.get("data");
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mHead;
        private final TextView mUserName;
        private final TextView mContent;
        private final TextView mTime;
        private final RecyclerView mImageView;
        private final ImageView mHeart;
        private final TextView mHeartNum;

        public MyViewHolder(View itemView) {
            super(itemView);
        mHead = (ImageView) itemView.findViewById(R.id.iv_head);
        mUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
        mContent = (TextView) itemView.findViewById(R.id.tv_content);
        mTime = (TextView) itemView.findViewById(R.id.tv_time);
        mImageView = itemView.findViewById(R.id.rv_image);
        mHeart = itemView.findViewById(R.id.iv_heart);
        mHeartNum = itemView.findViewById(R.id.tv_heart_number);

        mImageView.setOnClickListener(this);
        mHeart.setOnClickListener(this);
        }

        public void setData(LinkedHashMap<String , List> data,int position) {
            mListData = data.get("data");
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(mListData.get(position).getAvatar())
                    .into(mHead);

            mUserName.setText(mListData.get(position).getUser_nickname());
            mContent.setText(mListData.get(position).getContent());
            mTime.setText(mListData.get(position).getCreate_time());
            mHeartNum.setText(String.valueOf(mListData.get(position).getLike_count()));

            List imageNum = mListData.get(position).getImages();
            for (int i = 0 ; i < imageNum.size() ; i++){
                if(TextUtils.isEmpty(imageNum.get(i).toString())){
                    return;
                }
            }
            FoundContentAdapter contentAdapter = new FoundContentAdapter(mActivity ,imageNum);
            mImageView.setAdapter(contentAdapter);
            if(mListDecoration == null) {
                mListDecoration = new DividerGridItemDecoration(mContext);
            }
            mImageView.addItemDecoration(mListDecoration);
            GridLayoutManager manager = new GridLayoutManager(mContext ,4);
            mImageView.setLayoutManager(manager);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rv_image:

                    break;
                case R.id.iv_heart:
                    mHeart.setBackgroundResource(R.mipmap.heart_red);
                    postHeartForNet(mHeartIndex , getAdapterPosition() + 1);
                    mHeartNum.setText(String.valueOf(mListData.get(getAdapterPosition()).getLike_count() + 1));
                    break;
            }
        }
    }

    private void postHeartForNet(String url , int id) {
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addParams("id" ,String.valueOf(id))
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

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
