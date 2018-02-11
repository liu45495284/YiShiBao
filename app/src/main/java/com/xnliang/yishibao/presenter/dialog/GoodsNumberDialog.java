package com.xnliang.yishibao.presenter.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.GoodsDetailActivity;

/**
 * Created by JackLiu on 2018-02-11.
 */

public class GoodsNumberDialog extends AlertDialog implements View.OnClickListener{

    private Context mContext;
    private int amount = 1; //购买数量
    private int goods_storage = 1; //商品库存
    private EditText mEtNumber;
    private View mView;
    private GoodsDetailActivity mActivity;

    public GoodsNumberDialog(Context context , int data) {
        super(context , R.style.dialogStyle);
        this.mContext = context;
        this.mActivity = (GoodsDetailActivity)context;
        this.goods_storage = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.goods_alert_dialog , null);

        initView();
        setView(mView);
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        Button dropButton = mView.findViewById(R.id.bt_detail_drop);
        Button plusButton = mView.findViewById(R.id.bt_detail_plus);
        mEtNumber = mView.findViewById(R.id.et_detail_number);
        Button confirmButton = mView.findViewById(R.id.bt_confirm);
        dropButton.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        mEtNumber.addTextChangedListener(new NumberTextWatcher());
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_detail_drop:
                if (amount > 1) {
                amount--;
                mEtNumber.setText(amount + "");
            }
                break;
            case R.id.bt_detail_plus:
                if (amount < goods_storage) {
                    amount++;
                    mEtNumber.setText(amount + "");
                }
                break;
            case R.id.bt_confirm:
                Toast.makeText(mContext , "555" , Toast.LENGTH_SHORT).show();
                break;
        }
        mEtNumber.clearFocus();
    }

    public class NumberTextWatcher implements TextWatcher{
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
                mEtNumber.setText(goods_storage + "");
                return;
            }
            if (amount < 1) {
                mEtNumber.setText(amount + "");
                return;
            }
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = mActivity.getWindowManager().getDefaultDisplay().getHeight() / 2;
        window.setAttributes(params);

    }
}
