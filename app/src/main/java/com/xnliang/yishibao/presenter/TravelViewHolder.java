package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.TravelViewItemAdapter;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.customview.CategrayGridView;

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
    public void setData (List data){
        //已得到数据了
        //设置适配器
        TravelViewItemAdapter adapter = new TravelViewItemAdapter(mContext, data);
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
