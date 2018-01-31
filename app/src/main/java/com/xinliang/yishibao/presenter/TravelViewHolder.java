package com.xinliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xinliang.yishibao.R;
import com.xinliang.yishibao.module.adapter.TodayGVAdapter;
import com.xinliang.yishibao.view.customview.CategrayGridView;

import java.util.List;

/**
 * Created by JackLiu on 2018-01-31.
 */

public class TravelViewHolder extends BaseViewHolder {

    private final Context mContext;
    private CategrayGridView gridView;

    public TravelViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        gridView = itemView.findViewById(R.id.gv_channel);
    }

    //        public void setData(List<WomenBean.WomenData.ModuleBean.DataBean> module1data) {
    public void setData (List module0data){
        //已得到数据了
        //设置适配器
        TodayGVAdapter adapter = new TodayGVAdapter(mContext, module0data);
        gridView.setAdapter(adapter);
    }
}
