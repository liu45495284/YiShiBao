package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.xnliang.yishibao.R;

/**
 * Created by JackLiu on 2018-02-23.
 */

public class SubmitPathViewHolder extends BaseViewHolder {

    private Context mContext;
    public SubmitPathViewHolder(Context context , View itemView) {
        super(itemView);
        this.mContext = context;
        itemView.setFocusableInTouchMode(true);
        itemView.requestFocus();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext , "98987" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
