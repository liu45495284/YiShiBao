package com.xnliang.yishibao.view.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.db.UserDbHelp;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.SelfListActivity;
import com.xnliang.yishibao.view.SettingActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyModifyFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.rl_modify_back)
    RelativeLayout mModifyBack;
    @Bind(R.id.et_modify_old)
    EditText mOld;
    @Bind(R.id.et_modify_new)
    EditText mNew;
    @Bind(R.id.et_modify_again)
    EditText mAgain;
    @Bind(R.id.bt_pass_save)
    Button mSavePass;

    private SelfItemBackListener mListener;
    private UserDbHelp dbHelper;
    private static final String mModifyAndForget ="http://ysb.appxinliang.cn/api/user/changepassword";
    private static final int SUCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private String mLastPass;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setmModifyBack((SelfListActivity)getActivity());
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "login");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_modify_password , container ,false);
        ButterKnife.bind(this ,view);
        initView();
        return view;
    }

    private void initView() {
        dbHelper = new UserDbHelp(getActivity(),"UserInfo.db",null,1);
        mModifyBack.setOnClickListener(this);
        mSavePass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_modify_back:
                mListener.viewBackListener();
                break;
            case R.id.bt_pass_save:
                String oldPass = mOld.getText().toString().trim();
                String newPass = mNew.getText().toString().trim();
                String newPass2 = mAgain.getText().toString().trim();
                if (oldPass.isEmpty() || newPass.isEmpty() || newPass2.isEmpty()){
                    Toast.makeText(getActivity() , R.string.modify_password_text ,Toast.LENGTH_SHORT).show();
                    return;
                }

//                if(!getOldPass().equalsIgnoreCase(oldPass)){
//                    Toast.makeText(getActivity() , R.string.modify_old_password ,Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if(!newPass.equalsIgnoreCase(newPass2)){
                    Toast.makeText(getActivity() ,R.string.modify_new_password  ,Toast.LENGTH_SHORT).show();
                    return;
                }

                sendDatatoServer(oldPass , newPass ,newPass2 );
                mLastPass = newPass;
                break;
        }
    }

    public String getOldPass(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userData";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            String password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            db.close();
            return password;
        }

        return "";
    }

    public void sendDatatoServer(String oldPass ,String newPass ,String newPass2){
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        Log.e("TAG", "token" + token);
        LinkedHashMap<String , String> hashMap = new LinkedHashMap<>();

        OkHttpUtils
                .post()
                .url(mModifyAndForget)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addParams("old_password" , oldPass)
                .addParams("password" , newPass)
                .addParams("confirm_password" , newPass2)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);

                        //联网成功后使用fastjson解析
                        processData(response);

                    }
                });
    }

    private void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE){
            Toast.makeText(getActivity() ,msg ,Toast.LENGTH_SHORT).show();
            return;
        }

        if(Integer.parseInt(code) == SUCESSFUL_CODE){
//            if (modify(mLastPass)) {
//                Log.i("TAG" ,"修改数据表成功");
//            }
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.self_container ,loginFragment ,"login");
            transaction.commit();

            Toast.makeText(getActivity() ,msg ,Toast.LENGTH_SHORT).show();
        }
    }

    //向数据库修改数据
//    public boolean modify(String password){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("password",password);
//        String whereClause = "password=?";
//        String[] whereArgs={String.valueOf(1)};
//        db.update("userData",values,whereClause,whereArgs);
//        db.close();
//        return true;
//    }

    public void setmModifyBack(SelfItemBackListener listener){
        this.mListener = listener;
    }

}
