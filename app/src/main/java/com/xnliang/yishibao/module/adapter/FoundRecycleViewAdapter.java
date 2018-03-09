package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.presenter.FoundContentViewHolder;
import com.xnliang.yishibao.presenter.FoundFootViewHolder;
import com.xnliang.yishibao.presenter.FoundHeadViewHolder;
import com.xnliang.yishibao.view.fragment.FoundFragment;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class FoundRecycleViewAdapter extends RecyclerView.Adapter {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final LinkedHashMap<String ,List> moduleBeanList;
    private static final int HEAD_VIEW = 0;
    private static final int CONTENT_ITEM = 1;
    private static final int LOADING_FOOT = 2;
    private static final int RECYCLEVIEW_ITEM_COUNT = 2;
    private int mCurrentType = HEAD_VIEW;
    private FoundFragment mFragment;

    public FoundRecycleViewAdapter(Context mContext, LinkedHashMap<String ,List> moduleBeanList , FoundFragment foundFragment) {
        this.mContext = mContext;
        this.mFragment = foundFragment;
        this.moduleBeanList = moduleBeanList;
        //以后用它来初始化布局
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_VIEW) {
            return new FoundHeadViewHolder(mContext, mLayoutInflater.inflate(R.layout.found_head_view, null));
        }else if(viewType == CONTENT_ITEM) {
            return new FoundContentViewHolder(mContext, mLayoutInflater.inflate(R.layout.found_content_list, null) , mFragment);
        }else if(viewType == LOADING_FOOT){
            return new FoundFootViewHolder(mContext, mLayoutInflater.inflate(R.layout.found_loading_view, parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEAD_VIEW) {
            FoundHeadViewHolder headViewHolder= (FoundHeadViewHolder) holder;
//            headViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)==CONTENT_ITEM) {
            FoundContentViewHolder contentViewHolder= (FoundContentViewHolder) holder;
            contentViewHolder.setData();
        }else if(getItemViewType(position)==LOADING_FOOT){

        }

    }

    @Override
    public int getItemCount() {
        return RECYCLEVIEW_ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case HEAD_VIEW:
                mCurrentType = HEAD_VIEW;
                break;
            case CONTENT_ITEM:
                mCurrentType = CONTENT_ITEM;
                break;
            case LOADING_FOOT:
                mCurrentType = LOADING_FOOT;
                break;
        }
        return mCurrentType;
    }
}
