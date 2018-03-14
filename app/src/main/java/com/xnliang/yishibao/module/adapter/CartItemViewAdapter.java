package com.xnliang.yishibao.module.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.CartBean;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.view.ShoppingCartActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-11.
 */

public class CartItemViewAdapter extends RecyclerView.Adapter  {


    private Context mContext;
    private List<CartBean.DataBean.ListsBean> mData;
    private final LayoutInflater mLayoutInflater;
    private int amount = 1; //购买数量
    private int goods_storage = 50; //商品库存
    private CheckInterface checkInterface;
    public ShoppingCartActivity cartActivity;
    private static final String removeIndex = "http://ysb.appxinliang.cn/api/shop/cart/del";
    private static final String changeNumUrl = "http://ysb.appxinliang.cn/api/shop/cart/change";
    private static final String totalPriceUrl = "http://ysb.appxinliang.cn/api/shop/cart/price";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private int mGoodsNum;
    private static final int TOTAL_REFRESH = 0;
    private static final int TOTAL_PRICE = 1;
    private static final int REMOVE_ALL = 2;
    private static final int REMOVE_SINGLE = 3;
    private Boolean single = false;
    private String mTotalAmount;
    private List totalPrice = new ArrayList();
    private MyViewHolder viewHolder;

    public CartItemViewAdapter(Context context, List<CartBean.DataBean.ListsBean> data) {
        this.mContext = context;
        this.cartActivity = (ShoppingCartActivity) context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "login");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.cart_item_view, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        totalPrice.clear();
        if (holder != null) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(mData, position);

            myViewHolder.mItemEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myViewHolder.mRl.setVisibility(View.GONE);
                    myViewHolder.mLl.setVisibility(View.VISIBLE);
                    myViewHolder.mFinish.setVisibility(View.VISIBLE);
                }
            });

            myViewHolder.mDrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amount > 1) {
                        amount--;
                        myViewHolder.mCartEdit.setText(amount + "");
                    }
                }
            });

            myViewHolder.mPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amount <= goods_storage) {
                        amount++;
                        myViewHolder.mCartEdit.setText(amount + "");
                    }
                }
            });

            myViewHolder.mCartEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().isEmpty())
                        return;
                    amount = Integer.valueOf(s.toString());
                    if (amount > goods_storage) {
                        myViewHolder.mCartEdit.setText(goods_storage + "");
                        return;
                    }
                }
            });



            final CartBean.DataBean.ListsBean listsBean = mData.get(position);
            final boolean choosed = listsBean.isChoosed();
            if (choosed){
                myViewHolder.mSingleCheck.setChecked(true);
                int id = mData.get(position).getGoods_id();
                getPriceFromNet(totalPriceUrl ,id , 0 ,amount , position);
            }else{
                myViewHolder.mSingleCheck.setChecked(false);
                handler.obtainMessage(REMOVE_ALL).sendToTarget();
            }
            myViewHolder.mSingleCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listsBean.setChoosed(((CheckBox) v).isChecked());
                    if (((CheckBox) v).isChecked()){
                        int id = mData.get(position).getGoods_id();
                        getPriceFromNet(totalPriceUrl ,id , 0 ,amount , position);
                    }else{
                        int id = mData.get(position).getGoods_id();
                        getPriceFromNet(totalPriceUrl ,id , 0 ,amount , position);
                        single = true;


                    }
                    checkInterface.checkGroup(position, ((CheckBox) v).isChecked());
                }
            });
        }
    }

        @Override
        public int getItemCount () {
            return mData.size();
        }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private final ImageView mItemPicture;
            private final TextView mItemName;
            private final TextView mItemIngegral;
            private final ImageButton mItemEdit;
            private final RelativeLayout mRl;
            private final Button mFinish;
            private final LinearLayout mLl;
            private final Button mDrop;
            private final Button mPlus;
            private final EditText mCartEdit;
            private final TextView mCartItemNum;
            private final CheckBox mSingleCheck;

            public MyViewHolder(View itemView) {
                super(itemView);
                mItemPicture = (ImageView) itemView.findViewById(R.id.iv_cart_picture);
                mItemName = (TextView) itemView.findViewById(R.id.tv_cart_item_title);
                mItemIngegral = (TextView) itemView.findViewById(R.id.tv_cart_integral);
                mItemEdit = itemView.findViewById(R.id.ib_cart_edit);
                mRl = itemView.findViewById(R.id.rl_cart_content);
                mFinish = itemView.findViewById(R.id.bt_cart_edit_finish);
                mLl = itemView.findViewById(R.id.ll_cart_num);
                mDrop = itemView.findViewById(R.id.bt_cart_drop);
                mPlus = itemView.findViewById(R.id.bt_cart_plus);
                mCartEdit = itemView.findViewById(R.id.et_cart_number);
                mCartItemNum = itemView.findViewById(R.id.tv_cart_num);
                mSingleCheck = itemView.findViewById(R.id.cb_single_check);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showRemoveDialog();
                        return false;
                    }
                });

                mFinish.setOnClickListener(this);
            }

        private void showRemoveDialog() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.remove_goods);
            builder.setNegativeButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    removeData(getAdapterPosition());
                }
            });

            builder.setPositiveButton(R.string.cancle_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        public void removeData(int position){
            if (mData.size() != 0) {
                int id = mData.get(position).getGoods_id();
                mData.remove(position);
                removeDataForNet(removeIndex , id);
                notifyItemRemoved(position);
            }
            }

            public void setData(List<CartBean.DataBean.ListsBean> moduledata, int position) {
                //使用Glide加载图片
                Glide.with(mContext)
                        .load(moduledata.get(position).getImage())
                        .into(mItemPicture);

                mGoodsNum = moduledata.get(position).getGoods_num();
                mItemName.setText(moduledata.get(position).getGoods_name());
                mItemIngegral.setText(moduledata.get(position).getGoods_price());
                mCartItemNum.setText(mGoodsNum + "");
            }

        @Override
        public void onClick(View v) {
            mRl.setVisibility(View.VISIBLE);
            mLl.setVisibility(View.GONE);
            mFinish.setVisibility(View.GONE);

            int id = mData.get(getAdapterPosition()).getGoods_id();
            changeDataForNet(changeNumUrl , id ,amount);

            String num = String.format(mContext.getResources().getString(R.string.cart_item_number),  amount);
            mCartItemNum.setText(num);

            amount = 1;
        }
    }

    //类型：0购物车订单；1立即购买 默认0
    private void getPriceFromNet(String url , int id , int type , int num , final int position){
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .get()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addParams("goods_ids" , id + "")
                .addParams("type" , type + "")
                .addParams("goods_num" , num + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        setTotalPrice(response);
                    }
                });
    }

    private void setTotalPrice(String response){
        JSONObject jsonObject = JSON.parseObject(response);
        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        mTotalAmount = jsonObject.getJSONObject("data").getString("amount");

//        totalPrice.add(mTotalAmount);
        if(single){
            handler.obtainMessage(REMOVE_SINGLE ,mTotalAmount).sendToTarget();
            single = false;
        }else {
            handler.obtainMessage(TOTAL_PRICE, mTotalAmount).sendToTarget();
        }
        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void changeDataForNet(String url ,int id  , int num) {
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addParams("goods_id" , id + "")
                .addParams("goods_num" , num + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        processData(response);
                    }
                });
    }

    private void removeDataForNet(String url ,int id) {
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addParams("goods_ids" , id + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        processData(response);
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TOTAL_REFRESH:

                    checkInterface.totalPrice(totalPrice);
                    break;
                case TOTAL_PRICE:
                    totalPrice.add(mTotalAmount);
                    checkInterface.totalPrice(totalPrice);
                    break;
                case REMOVE_ALL:
                    if (totalPrice.size() == 0){
                        return;
                    }
                    if (totalPrice.size() != 0) {
                        for (int i=0;i<totalPrice.size();i++){
                            totalPrice.remove(totalPrice.get(i));
                        }
                    }
                    checkInterface.totalPrice(totalPrice);
                    break;
                case REMOVE_SINGLE:
                    if (totalPrice.size() == 0){
                        return;
                    }
//                    if (totalPrice.size() != 0) {
//                        for (int i=0;i<totalPrice.size();i++){
                            totalPrice.remove(mTotalAmount);
//                        }
//                    }
                    checkInterface.totalPrice(totalPrice);
                    break;
            }
        }
    };

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /** * 复选框接口 */
    public interface CheckInterface {
        /**
         *  组选框状态改变触发的事件
         *  @param position 元素位置
         *  @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
        void totalPrice(List<String> list);
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

}

