package com.xnliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.db.UserDbHelp;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.view.customview.RoundImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

/**
 * Created by JackLiu on 2018-02-27.
 */

public class MyPersonalDataFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.rl_person_back)
    RelativeLayout mBackLayout;
    @Bind(R.id.rl_head)
    RelativeLayout mHead;
    @Bind(R.id.et_person_name)
    EditText mPersonName;
    @Bind(R.id.et_person_phone)
    EditText mPersonPhone;
    @Bind(R.id.bt_person_save)
    Button mPersonSave;
    @Bind(R.id.iv_round_head)
    RoundImageView mHeadView;

    private static final int GALLERY_REQUEST_CODE = 0;    // 相册选图标记
    private static final int CAMERA_REQUEST_CODE = 1;     // 相机拍照标记
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private static final String mSaveUrl = "http://ysb.appxinliang.cn/api/user/setting";
    private String mPicturePath;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private File mFile;
    private String mPictureName;
    private UserDbHelp dbHelper;
    private static final int INSERT_FINISH = 0;
    private ProgressDialog mPb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_data , container ,false);
        ButterKnife.bind(this , view);
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "login");
        dbHelper = new UserDbHelp(getActivity(),"UserInfo.db",null,1);
        initView();
        return view;
    }

    private void initView() {
        String image = getArguments().getString("image");
        Glide.with(this)
                .load(image)
                .into(mHeadView);
        mBackLayout.setOnClickListener(this);
        mHead.setOnClickListener(this);
        mPersonSave.setOnClickListener(this);

        mPersonName.addTextChangedListener(new TextWatcher(0));
        mPersonPhone.addTextChangedListener(new TextWatcher(1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_person_back:
                getActivity().finish();
                break;
            case R.id.rl_head:
                //TODO
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
                break;
            case R.id.bt_person_save:
                //TODO
                mPb = new ProgressDialog(getActivity());
                saveDataToServer(mSaveUrl , mFile ,mPersonName.getText().toString());
                break;
        }
    }

    private void saveDataToServer(String url , File image ,String name) {
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addFile("avatar" ,mPictureName+"" , image)
                .addParams("user_nickname" ,name)
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

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        int i = Math.round(progress);
                        if(i == 1){
                        }
                        Log.e("TAG" , "progress = " + progress);
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
            JSONObject jsonData = jsonObject.getJSONObject("data");
            String score = jsonData.getString("score");
            String coin = jsonData.getString("coin");
            String userNickName = jsonData.getString("user_nickname");
            String avatar = jsonData.getString("avatar");
            String mobile = jsonData.getString("mobile");
            String position = jsonData.getString("position");

            insertUserData(userNickName,mobile,coin,score,avatar);
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    //向数据库插入数据
    public void insertUserData(String nickname ,String mobile,String coin,String score,String avatar){

        SQLiteDatabase db= dbHelper.getWritableDatabase();
        db.execSQL("delete from userDetail");
        ContentValues values=new ContentValues();
        values.put("name",nickname);
        values.put("mobile",mobile);
        values.put("coin",coin);
        values.put("score",score);
        values.put("avatar",avatar);
        db.insert("userDetail",null,values);
        db.close();

        handler.obtainMessage(INSERT_FINISH).sendToTarget();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mPicturePath = cursor.getString(columnIndex);
            int positionLine = mPicturePath.lastIndexOf("/");
            mPictureName = mPicturePath.substring(positionLine + 1);
            mFile = new File(mPicturePath);
            cursor.close();

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.loading_default)
                    .error(R.mipmap.loading_failure)
                    .priority(Priority.HIGH);

            Glide.with(getActivity())
                    .load(mPicturePath)
                    .apply(options)
                    .into(mHeadView);
        }
    }

    class TextWatcher implements android.text.TextWatcher{
        private int mFlag;

        public TextWatcher(int flag) {
            this.mFlag = flag;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case INSERT_FINISH:
                    mPb.dismiss();
                    getActivity().finish();
                    break;
            }
        }
    };
}
