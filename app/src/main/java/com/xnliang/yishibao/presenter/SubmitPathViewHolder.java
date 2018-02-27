package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.OrderSubmitActivity;
import com.xnliang.yishibao.view.SelfListActivity;
import com.xnliang.yishibao.view.fragment.MyAddressFragment;

/**
 * Created by JackLiu on 2018-02-23.
 */

public class SubmitPathViewHolder extends BaseViewHolder {

    private Context mContext;
    private OrderSubmitActivity submitActivity;

    public SubmitPathViewHolder(Context context , View itemView) {
        super(itemView);
        this.mContext = context;
        this.submitActivity = (OrderSubmitActivity) context;
        itemView.setFocusableInTouchMode(true);
        itemView.requestFocus();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("pos" , 3);
                Intent intent = new Intent(submitActivity , SelfListActivity.class);
                intent.putExtras(bundle);
                submitActivity.startActivity(intent);
                Toast.makeText(mContext , "98987" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
