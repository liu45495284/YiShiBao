package com.xinliang.yishibao.module.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinliang.yishibao.R;

import java.util.List;

/**
 * Created by JackLiu on 2018-01-29.
 */

public class CategrayAdapter extends BaseAdapter {

        private final Context mContext;
//        private final List<WomenBean.WomenData.ModuleBean.DataBean> module1data;
          private final List module1data;

//        public TodayGVAdapter(Context mContext, List<WomenBean.WomenData.ModuleBean.DataBean> module1data) {
//            this.mContext = mContext;
//            this.module1data = module1data;
//        }

        public CategrayAdapter(Context mContext, List module1data) {
            this.mContext = mContext;
            this.module1data = module1data;
        }

        @Override
        public int getCount() {
            return module1data == null ? 0 : 5;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                //item的布局：垂直线性，ImagView+TextView
                convertView = View.inflate(mContext, R.layout.gridview_item_categray, null);
                holder = new ViewHolder();
                holder.iv_channel = (ImageView) convertView.findViewById(R.id.img_categray);
                holder.tv_channel = (TextView) convertView.findViewById(R.id.text_categray);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //装配数据
//            WomenBean.WomenData.ModuleBean.DataBean datasig = module1data.get(position);

            //使用Glide加载图片
            Glide.with(mContext).load(R.mipmap.categray_chuju).into(holder.iv_channel);
            //设置文本
            holder.tv_channel.setText("厨具");
            return convertView;
        }

        public static class ViewHolder {
            public ImageView iv_channel;
            public TextView tv_channel;
        }
    }

