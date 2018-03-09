package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.FoundListBean;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.view.MainActivity;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by JackLiu on 2018-01-30.
 */

public class FoundItemRecycleViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final LinkedHashMap<String , List> mData;
    private MyViewHolder mHolder;
    private MainActivity mActivity;
    private DividerGridItemDecoration mListDecoration;

    public FoundItemRecycleViewAdapter(Context context, LinkedHashMap<String , List> data) {
        this.mContext = context;
        this.mActivity = (MainActivity)context;
        this.mData = data;
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

    class MyViewHolder extends RecyclerView.ViewHolder {
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
        }

        public void setData(LinkedHashMap<String , List> data,int position) {
            List<FoundListBean.DataBeanX.ListsBean.DataBean> listData = data.get("data");
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(listData.get(position).getAvatar())
                    .into(mHead);

            mUserName.setText(listData.get(position).getUser_nickname());
            mContent.setText(listData.get(position).getContent());
            mTime.setText(listData.get(position).getCreate_time());
            mHeartNum.setText(String.valueOf(listData.get(position).getLike_count()));

            List imageNum = listData.get(position).getImages();
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
    }
}
