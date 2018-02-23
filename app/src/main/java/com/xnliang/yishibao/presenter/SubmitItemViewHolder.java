package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.SubmitListItemAdapter;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;
import com.xnliang.yishibao.module.utils.ListDecoration;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-23.
 */

public class SubmitItemViewHolder extends BaseViewHolder {

    private Context mContext;
    public RecyclerView mView;
    private RecyclerView.ItemDecoration mDecoration = null;
    public SubmitItemViewHolder(Context context ,View itemView) {
        super(itemView);
        this.mContext = context;
        mView = itemView.findViewById(R.id.rv_submit_item);
    }

    public void setData(List data){
        SubmitListItemAdapter submitItem = new SubmitListItemAdapter(mContext , data);
        mView.setAdapter(submitItem);
        mView.addItemDecoration(new ListDecoration(mContext , ListDecoration.VERTICAL_LIST ,R.drawable.cart_list_divide));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mView.setLayoutManager(linearLayoutManager);
    }
}
