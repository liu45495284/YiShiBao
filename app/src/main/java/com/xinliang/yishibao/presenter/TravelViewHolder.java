package com.xinliang.yishibao.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.xinliang.yishibao.R;
import com.xinliang.yishibao.module.adapter.TodayGVAdapter;
import com.xinliang.yishibao.view.MainActivity;
import com.xinliang.yishibao.view.customview.CategrayGridView;
import com.xinliang.yishibao.view.fragment.HomeFragment;
import com.xinliang.yishibao.view.fragment.TravelFragment;

import java.util.List;

/**
 * Created by JackLiu on 2018-01-31.
 */

public class TravelViewHolder extends BaseViewHolder {

    private final Context mContext;
    private final MainActivity mActivity;
    private CategrayGridView gridView;
    private ItmeCallBackListener mListener;

    public TravelViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        mActivity = (MainActivity)mContext;
        gridView = itemView.findViewById(R.id.gv_channel);
        this.setItemClickListener(mActivity);
    }

    //        public void setData(List<WomenBean.WomenData.ModuleBean.DataBean> module1data) {
    public void setData (List module0data){
        //已得到数据了
        //设置适配器
        TodayGVAdapter adapter = new TodayGVAdapter(mContext, module0data);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.gridViewItemClickListener(parent,view,position,id);

            }
        });
    }

    public void setItemClickListener(ItmeCallBackListener listener) {
        mListener = listener;
    }

}
