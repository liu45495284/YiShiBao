package com.xnliang.yishibao.module.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.ShoppingCartBean;
import com.xnliang.yishibao.view.ShoppingCartActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by JackLiu on 2018-02-11.
 */

public class CartItemViewAdapter extends RecyclerView.Adapter  {


    private Context mContext;
    private List<ShoppingCartBean> mData;
    private final LayoutInflater mLayoutInflater;
    private int amount = 1; //购买数量
    private int goods_storage = 50; //商品库存
    private CheckInterface checkInterface;
    public ShoppingCartActivity cartActivity;
    private MyViewHolder myViewHolder;

    public CartItemViewAdapter(Context context, List data) {
        this.mContext = context;
        this.cartActivity = (ShoppingCartActivity) context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.cart_item_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(mData, position);

            myViewHolder.mItemEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myViewHolder.mRl.setVisibility(View.GONE);
                    myViewHolder.mLl.setVisibility(View.VISIBLE);
                    myViewHolder.mFinish.setVisibility(View.VISIBLE);
                }
            });

            myViewHolder.mFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myViewHolder.mRl.setVisibility(View.VISIBLE);
                    myViewHolder.mLl.setVisibility(View.GONE);
                    myViewHolder.mFinish.setVisibility(View.GONE);

                    String num = String.format(mContext.getResources().getString(R.string.cart_item_number),  amount);
                    myViewHolder.mCartItemNum.setText(num);
                    amount = 1;
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
                    if (amount < goods_storage) {
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


            final ShoppingCartBean shoppingCartBean = mData.get(position);
            boolean choosed = shoppingCartBean.isChoosed();
            if (choosed){
                myViewHolder.mSingleCheck.setChecked(true);
            }else{
                myViewHolder.mSingleCheck.setChecked(false);
            }

            myViewHolder.mSingleCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shoppingCartBean.setChoosed(((CheckBox) v).isChecked());
                    checkInterface.checkGroup(position, ((CheckBox) v).isChecked());
                }
            });
        }
    }

        @Override
        public int getItemCount () {
            return mData.size();
        }

    class MyViewHolder extends RecyclerView.ViewHolder {
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
                    mData.remove(position);
                    notifyItemRemoved(position);
                }
            }

            public void setData(List moduledata, int position) {
                //使用Glide加载图片
                Glide.with(mContext)
                        .load(mData.get(position).getId())
                        .into(mItemPicture);
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
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

}

