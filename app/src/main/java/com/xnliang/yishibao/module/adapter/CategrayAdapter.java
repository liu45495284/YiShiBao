package com.xnliang.yishibao.module.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.fragment.CategrayItemFragment;

import java.util.List;

/**
 * Created by JackLiu on 2018-01-29.
 */

public class CategrayAdapter extends BaseAdapter {

        private final Context mContext;
        private final JSONArray module1data;
        private final MainActivity mActivity;

        public CategrayAdapter(Context context, JSONArray module1data) {
            this.mContext = context;
            this.module1data = module1data;
            this.mActivity = (MainActivity)context;
        }

        @Override
        public int getCount() {
            return module1data == null ? 0 : 10;
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
            String url = module1data.getJSONObject(position).getString("app_icon");
            String name = module1data.getJSONObject(position).getString("name");
            //使用Glide加载图片
            Glide.with(mContext).load(url).into(holder.iv_channel);
            //设置文本
            holder.tv_channel.setText(name);
            return convertView;
        }

        public static class ViewHolder {
            public ImageView iv_channel;
            public TextView tv_channel;
        }
    }

