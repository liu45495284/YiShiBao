package com.xnliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bigkoo.pickerview.OptionsPickerView;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.AreaBean;
import com.xnliang.yishibao.module.bean.CityBean;
import com.xnliang.yishibao.module.bean.CityListsBean;
import com.xnliang.yishibao.module.bean.ProvinceBean;
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
    private static final int SAVE_COMPLETE = 0;
    private static final int CITY_LISTS = 1;
    private JSONObject mJsonData;
    private List<ProvinceBean> options1Items = new ArrayList();
    private List<List<CityBean>> options2Items = new ArrayList();
    private List<List<List<AreaBean>>> options3Items = new ArrayList();

    private ArrayList<String> Provincestr = new ArrayList<>();//省
    private ArrayList<ArrayList<String>> Citystr = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> Areastr = new ArrayList<>();

    private List<CityListsBean.DataBean> mDataBeans;
    private OptionsPickerView mPickerView;
    private ProgressDialog pb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_address_edit ,container ,false);
        ButterKnife.bind(this,view);
        setAddressBack((SelfListActivity) getActivity());
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "login");
        initView();
        initData();
        return view;
    }

    private void initData() {
        getCityDataFromNet(cityLists);
    }

    private void getCityDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
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
                        processCityData(response);
                    }
                });
    }

    private void processCityData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            return;
        }


        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            mDataBeans = JSON.parseObject(jsonObject.getString("data") ,
                    new TypeReference<List<CityListsBean.DataBean>>(){}.getType());

            for (int i = 0; i < mDataBeans.size() ; i++ ){
                int id = mDataBeans.get(i).getId();
                String name = mDataBeans.get(i).getName();
                int level = mDataBeans.get(i).getLevel();
                int parentId = mDataBeans.get(i).getParent_id();
                ProvinceBean provinceBean = new ProvinceBean(id ,name ,level ,parentId);
                options1Items.add(provinceBean);
                Provincestr.add(mDataBeans.get(i).getName());

                List<CityBean> cityBeanList = new ArrayList<>();
                ArrayList<String> cityStr = new ArrayList<>();

                List<List<AreaBean>> options3Items_03 = new ArrayList<>();
                ArrayList<ArrayList<String>> options3Items_str = new ArrayList<>();

                for (int j = 0; j < mDataBeans.get(i).getSon().size() ; j++){

                    int cityId = mDataBeans.get(i).getSon().get(j).getId();
                    String cityName = mDataBeans.get(i).getSon().get(j).getName();
                    int cityLevel = mDataBeans.get(i).getSon().get(j).getLevel();
                    int cityParentId = mDataBeans.get(i).getSon().get(j).getParent_id();
                    CityBean cityBean = new CityBean(cityId ,cityName ,cityLevel ,cityParentId);
                    cityBeanList.add(cityBean);
                    cityStr.add(cityName);

                    List<AreaBean> areaBeanList = new ArrayList<>();
                    ArrayList<String> areaBeanstr = new ArrayList<>();

                    for (int k = 0; k < mDataBeans.get(i).getSon().get(j).getSon().size() ; k++){

                        int areaId = mDataBeans.get(i).getSon().get(j).getSon().get(k).getId();
                        String areaName = mDataBeans.get(i).getSon().get(j).getSon().get(k).getName();
                        int areaLevel = mDataBeans.get(i).getSon().get(j).getSon().get(k).getLevel();
                        int areaParentId = mDataBeans.get(i).getSon().get(j).getSon().get(k).getParent_id();
                        AreaBean areaBean = new AreaBean(areaId ,areaName ,areaLevel ,areaParentId);

                        areaBeanList.add(areaBean);
                        areaBeanstr.add(areaName);

                    }
                    options3Items_str.add(areaBeanstr);//本次查询的存储内容
                    options3Items_03.add(areaBeanList);

                }
                options2Items.add(cityBeanList);//增加二级目录数据
                Citystr.add(cityStr);

                options3Items.add(options3Items_03);//添加三级目录
                Areastr.add(options3Items_str);
            }


            handler.obtainMessage(CITY_LISTS).sendToTarget();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
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


            handler.obtainMessage(SAVE_COMPLETE).sendToTarget();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SAVE_COMPLETE:

                    break;
                case CITY_LISTS:
                    initPickerView();
                    pb.dismiss();
                    break;
            }
        }
    };

    private void initView() {
        showDialog();
        relativeLayout.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mEtName.setOnClickListener(this);
        mEtPhone.setOnClickListener(this);
        mLocalAddress.setOnClickListener(this);

    }

    public void showDialog(){
        if (pb == null){
            pb = new ProgressDialog(getActivity());
        }
        pb.setMessage(getResources().getString(R.string.found_loading));
        pb.setCancelable(false);
        pb.show();
    }

    private void initPickerView() {
        // 初始化选择器
        mPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getName()+
                        options2Items.get(options1).get(options2).getName()+
                        options3Items.get(options1).get(options2).get(options3).getName();
                mLocalAddress.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        mPickerView.setPicker(options1Items,options2Items,options3Items);//三级选择器
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
            case R.id.et_address_local:
                mPickerView.show();
                break;
        }
    }

    public void setAddressBack(SelfItemBackListener listener){
        this.mListener = listener;
    }
}
