package com.xnliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.SelfListActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

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
    @Bind(R.id.et_address_local)
    TextView mLocalAddress;
    @Bind(R.id.et_address_detail)
    EditText mEtDetail;
    @Bind(R.id.et_address_district)
    EditText mEtDistrict;

    private SelfItemBackListener mListener;
    private static final String addAddress = "http://ysb.appxinliang.cn/api/user/address/add";
    private static final String cityLists = "http://ysb.appxinliang.cn/api/areas";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private JSONObject mJsonData;
    private List options1Items = new ArrayList();
    private List options2Items = new ArrayList();
    private List options3Items = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_address_edit ,container ,false);
        ButterKnife.bind(this,view);
        setAddressBack((SelfListActivity) getActivity());
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "login");
        initView();
//        initData();
        return view;
    }

    private void initData() {
        getCityDataFromNet(cityLists);
    }

    private void addDataFromNet(String url, String consignee, String phone, String detailAddress, int district) {
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addParams("consignee" ,consignee)
                .addParams("mobile" ,phone)
                .addParams("address" ,detailAddress)
                .addParams("district" ,district+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                        Toast.makeText(getActivity() ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
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

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            return;
        }


        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            mJsonData = jsonObject.getJSONObject("data");

            handler.obtainMessage();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    private void initView() {
        relativeLayout.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mEtName.setOnClickListener(this);
        mEtPhone.setOnClickListener(this);

        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                tvOptions.setText(tx);
            }
        }).build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_address_edit_back:
                mListener.viewBackListener();
                break;
            case R.id.bt_edit_save:
                String consignee = mEtName.getText().toString();
                String phone = mEtPhone.getText().toString();
                String detailAddress = mEtDetail.getText().toString();
                int district = Integer.valueOf(mEtDistrict.getText().toString());

                addDataFromNet(addAddress , consignee ,phone ,detailAddress ,district);
                break;
            case R.id.et_address_name:
                break;
            case R.id.et_address_phone:
                break;
        }
    }

    public void setAddressBack(SelfItemBackListener listener){
        this.mListener = listener;
    }
}
