package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.SelfListItemRecycleViewAdapter;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.fragment.SelfFragment;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class SelfListViewHolder extends BaseViewHolder {

    private final MainActivity mActivity;
    private final Context mContext;
    private final RecyclerView mRecyclerView;

    public SelfListViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        mActivity = (MainActivity)mContext;
        mRecyclerView = itemView.findViewById(R.id.rv_self_list);
    }
    public void setData(List data) {
        //1.已有数据
        //2.设置适配器：-->设置文本和recycleView的数据
        SelfListItemRecycleViewAdapter adapter = new SelfListItemRecycleViewAdapter(mContext,data);
        //设置adapter
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new ListDecoration(mContext,ListDecoration.VERTICAL_LIST , R.drawable.list_divide));

        //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
        LinearLayoutManager manager= new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL ,false);
        mRecyclerView.setLayoutManager(manager);

    }
}
