package com.xnliang.yishibao.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.fragment.MyModifyFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-26.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rl_setting_back)
    RelativeLayout mSettingBack;
    @Bind(R.id.rl_modify_password)
    RelativeLayout mModifyLayout;
    @Bind(R.id.rl_exit)
    RelativeLayout mExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mSettingBack.setOnClickListener(this);
        mModifyLayout.setOnClickListener(this);
        mExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting_back:
                finish();
                break;
            case R.id.rl_modify_password:
                Bundle bundle = new Bundle();
                bundle.putInt("pos" , 6 );
                Intent intent = new Intent(this , SelfListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.rl_exit:
                break;
        }
    }
}
