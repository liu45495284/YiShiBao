package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.FoundItemRecycleViewAdapter;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;
import com.xnliang.yishibao.module.utils.ListDecoration;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class FoundContentViewHolder extends BaseViewHolder {
    private final Context mContext;

    private final RecyclerView mItemView;

    public FoundContentViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        mItemView = itemView.findViewById(R.id.rv_found_list);
    }

    public void setData(List data) {
        //1.已有数据
        //2.设置适配器：-->设置文本和recycleView的数据
        FoundItemRecycleViewAdapter adapter = new FoundItemRecycleViewAdapter(mContext,data);
        //设置adapter
        mItemView.setAdapter(adapter);
        mItemView.addItemDecoration(new ListDecoration(mContext,ListDecoration.VERTICAL_LIST,R.drawable.list_divide));

        //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
        GridLayoutManager manager= new GridLayoutManager(mContext , 1);
        mItemView.setLayoutManager(manager);
    }
}
