package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bigkoo.pickerview.OptionsPickerView;
import com.xnliang.yishibao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-27.
 */

public class AddressEditFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.rl_address_edit_back)
    RelativeLayout relativeLayout;
    @Bind(R.id.bt_edit_save)
    Button mSaveButton;
    @Bind(R.id.et_address_name)
    EditText mEtName;
    @Bind(R.id.et_address_phone)
    EditText mEtPhone;

    private List options1Items = new ArrayList();
    private List options2Items = new ArrayList();
    private List options3Items = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_address_edit ,container ,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        relativeLayout.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mEtName.setOnClickListener(this);
        mEtPhone.setOnClickListener(this);

//        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
//                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(option2)
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
//                tvOptions.setText(tx);
//            }
//        }).build();
//        pvOptions.setPicker(options1Items, options2Items, options3Items);
//        pvOptions.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_address_edit_back:
                break;
            case R.id.bt_edit_save:
                break;
            case R.id.et_address_name:
                break;
            case R.id.et_address_phone:
                break;
        }
    }
}
