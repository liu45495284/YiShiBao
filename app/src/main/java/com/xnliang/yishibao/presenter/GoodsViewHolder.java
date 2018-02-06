package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.GoodsRecycleViewAdapter;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;

import java.util.List;

/**
 * Created by JackLiu on 2018-01-31.
 */

public class GoodsViewHolder extends BaseViewHolder {

    private final Context mContext;
    private RecyclerView dapeiqs_rv;

    public GoodsViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext=mContext;
        dapeiqs_rv= itemView.findViewById(R.id.dapeiqs_rv);
    }

    public void setData(List data) {
        //1.已有数据
        //2.设置适配器：-->设置文本和recycleView的数据
        GoodsRecycleViewAdapter adapter=new GoodsRecycleViewAdapter(mContext,data);
        //设置adapter
        dapeiqs_rv.setAdapter(adapter);
        dapeiqs_rv.addItemDecoration(new DividerGridItemDecoration(mContext));

        //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
        GridLayoutManager manager= new GridLayoutManager(mContext,4);
        dapeiqs_rv.setLayoutManager(manager);

    }
}
