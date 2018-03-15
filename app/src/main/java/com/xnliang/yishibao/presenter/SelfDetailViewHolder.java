package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.db.UserDbHelp;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.view.IntegralTopUpActivity;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SelfListActivity;
import com.xnliang.yishibao.view.SettingActivity;
import com.xnliang.yishibao.view.fragment.SelfFragment;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class SelfDetailViewHolder extends BaseViewHolder implements View.OnClickListener ,DataRefreshListener {

    private final Context mContext;
    private ImageView mHeadPicture;
    private TextView mSelfName;
    private TextView mSelfPhone;
    private ImageView mSetting;
    private TextView mCashIntegral;
    private TextView mShopIntegral;
    private MainActivity mActivity;
    private RelativeLayout mIntegralT;
    private RelativeLayout mIntegralC;
    private Bundle mBundle;
    private UserDbHelp dbHelper;
    private SelfFragment mFragment;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private SelfDetailInitListener mListener;


    public SelfDetailViewHolder(Context context , View itemView , SelfFragment fragment) {
        super(itemView);
        this.mContext = context;
        this.mActivity = (MainActivity) context;
        this.mFragment = fragment;
        sharedPreferencesHelper = new SharedPreferencesHelper(mActivity, "login");
        dbHelper = new UserDbHelp(mActivity,"UserInfo.db",null,1);
        mFragment.setRefreshListener(this);
        setInitListener(mFragment);

        mHeadPicture = itemView.findViewById(R.id.iv_self_picture);
        mSelfName = itemView.findViewById(R.id.tv_self_name);
        mSelfPhone = itemView.findViewById(R.id.tv_self_phone);
        mSetting = itemView.findViewById(R.id.self_setting);
        mCashIntegral = itemView.findViewById(R.id.tv_self_cash_number);
        mShopIntegral = itemView.findViewById(R.id.tv_self_shop_number);
        mIntegralC = itemView.findViewById(R.id.rl_detail_integral_c);
        mIntegralT = itemView.findViewById(R.id.rl_detail_integral_t);
    }

    public void setData(List data) {

        dataFromDb();

        mHeadPicture.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mIntegralC.setOnClickListener(this);
        mIntegralT.setOnClickListener(this);

        mListener.initFinish();
    }

    public void dataFromDb(){
        boolean flag = mFragment.getActivity().getIntent().getBooleanExtra("exit" , false);
        if(flag){
            Glide.with(mContext).load(R.mipmap.app_icon).into(mHeadPicture);
            mSelfName.setText(R.string.no_login);
            mSelfPhone.setText("");
            mCashIntegral.setText("0");
            mShopIntegral.setText("0");
        }else{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "select * from userDetail";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                String nickName = cursor.getString(cursor.getColumnIndex("name"));
                String mobile = cursor.getString(cursor.getColumnIndex("mobile"));
                String coin = cursor.getString(cursor.getColumnIndex("coin"));
                String score = cursor.getString(cursor.getColumnIndex("score"));
                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));

                Glide.with(mContext).load(avatar).into(mHeadPicture);
                mSelfName.setText(nickName);
                mSelfPhone.setText(mobile);
                mCashIntegral.setText(String.valueOf(coin));
                mShopIntegral.setText(String.valueOf(score));
            }
            cursor.close();
            db.close();


        }


    }

    @Override
    public void onClick(View v) {
        mBundle = new Bundle();
        switch (v.getId()) {
            case R.id.self_setting:
                Intent intent = new Intent(mActivity, SettingActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.iv_self_picture:
                mBundle.putInt("pos" , 7);
                Intent personIntent = new Intent(mActivity , SelfListActivity.class);
                personIntent.putExtras(mBundle);
                mActivity.startActivity(personIntent);
                break;
            case R.id.rl_detail_integral_c:
                Intent cIntent = new Intent(mActivity , IntegralTopUpActivity.class);
                mActivity.startActivity(cIntent);
                break;
            case R.id.rl_detail_integral_t:
                Bundle bundle = new Bundle();
                bundle.putInt("pos" , 8);
                Intent tixianIntent = new Intent(mActivity , SelfListActivity.class);
                tixianIntent.putExtras(bundle);
                mActivity.startActivity(tixianIntent);
                break;
        }
    }

    @Override
    public void refreshData() {
        dataFromDb();
    }

    public void setInitListener(SelfDetailInitListener listener){
        this.mListener = listener;
    }
}
