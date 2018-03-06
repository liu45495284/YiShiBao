package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.HandlerBackUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements HandlerBackUtil.HandleBackInterface{


    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public boolean onBackPressed() {
        return HandlerBackUtil.handleBackPress(this);
    }

}
