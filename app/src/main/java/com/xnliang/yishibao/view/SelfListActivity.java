package com.xnliang.yishibao.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.fragment.IntegralAndCashListFragment;
import com.xnliang.yishibao.view.fragment.IntegralTopOutFragment;
import com.xnliang.yishibao.view.fragment.MyAboutFragment;
import com.xnliang.yishibao.view.fragment.MyAddressFragment;
import com.xnliang.yishibao.view.fragment.MyHelpFragment;
import com.xnliang.yishibao.view.fragment.MyModifyFragment;
import com.xnliang.yishibao.view.fragment.MyOrderFragment;
import com.xnliang.yishibao.view.fragment.MyPersonalDataFragment;
import com.xnliang.yishibao.view.fragment.MyRecommendFragment;
import com.xnliang.yishibao.view.fragment.MyTeamFragment;
import com.xnliang.yishibao.view.fragment.MyTeamMemberFragment;

/**
 * Created by JackLiu on 2018-02-23.
 */

public class SelfListActivity extends BaseActivity implements SelfItemBackListener{

    private FragmentTransaction mTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_container);

        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("pos");

        MyOrderFragment myOrderFragment = new MyOrderFragment();
        MyTeamFragment myTeamFragment = new MyTeamFragment();
        MyRecommendFragment myRecommendFragment = new MyRecommendFragment();
        MyAddressFragment myAddressFragment = new MyAddressFragment();
        MyHelpFragment myHelpFragment = new MyHelpFragment();
        MyAboutFragment myAboutFragment = new MyAboutFragment();
        MyModifyFragment myModifyFragment = new MyModifyFragment();
        MyPersonalDataFragment personalFragment = new MyPersonalDataFragment();
        IntegralTopOutFragment topOutFragment = new IntegralTopOutFragment();
        IntegralAndCashListFragment listFragment = new IntegralAndCashListFragment();

        FragmentManager manager = getSupportFragmentManager();
        mTransaction = manager.beginTransaction();
        switch (position){
            case 0:
                mTransaction.replace(R.id.self_container , myOrderFragment ,"order");
                mTransaction.commit();
                break;
            case 1:
                mTransaction.replace(R.id.self_container , myTeamFragment ,"team");
                mTransaction.commit();
                break;
            case 2:
                mTransaction.replace(R.id.self_container , myRecommendFragment ,"recommend");
                mTransaction.commit();
                break;
            case 3:
                mTransaction.replace(R.id.self_container , myAddressFragment ,"address");
                mTransaction.commit();
                break;
            case 4:
                mTransaction.replace(R.id.self_container , myHelpFragment ,"help");
                mTransaction.commit();
                break;
            case 5:
                mTransaction.replace(R.id.self_container , myAboutFragment ,"about");
                mTransaction.commit();
                break;
            case 6:
                mTransaction.replace(R.id.self_container , myModifyFragment ,"modify");
                mTransaction.commit();
                break;
            case 7:
                mTransaction.replace(R.id.self_container , personalFragment ,"person");
                mTransaction.commit();
                break;
            case 8:
                mTransaction.replace(R.id.self_container , topOutFragment ,"out");
                mTransaction.commit();
                break;
            case 9:
                mTransaction.replace(R.id.self_container , listFragment ,"list");
                mTransaction.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void viewBackListener() {
        onBackPressed();
    }
}
