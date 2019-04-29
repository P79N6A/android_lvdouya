package com.hhkj.dyedu.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.getTeacherInfo;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class CreateAccountActivity extends BaseTitleHeadActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.et03)
    EditText et03;
    @BindView(R.id.et04)
    EditText et04;
    //    @BindView(R.id.et05)
//    EditText et05;
    @BindView(R.id.btSure)
    RoundTextView btSure;
    @BindView(R.id.et07)
    EditText et07;
    @BindView(R.id.et08)
    EditText et08;
    @BindView(R.id.tvPrefix)
    TextView tvPrefix;

    private boolean isCreate = true;
    private int id;

    private String prefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate = getIntent().getBooleanExtra("isCreate", true);
        prefix = CacheUtils.getUsername();
        tvPrefix.setText(prefix + "#");
        String aa="("+getIntent().getIntExtra("now",0)+"/"+getIntent().getIntExtra("restrict",0)+")";
        if (!isCreate) {
            setTitle("修改账号"+aa);
            id = getIntent().getIntExtra("id", -1);
            getTeacherInfo();
        }else{
            setTitle("创建账号"+aa);
        }

    }

    private void getTeacherInfo() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.getTeacherInfo);//"获取教师信息"
        httpRequest.add("id", id);
        CallServer.getInstance().postRequest("获取教师信息", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        getTeacherInfo info = gson.fromJson(response, getTeacherInfo.class);
                        if (info.getCode() == 1) {
//                            et01.setText(info.getData().getUsername().replaceAll(prefix + "@", ""));
                            String userName=info.getData().getUsername();
                            if(userName.contains("#")){
                                String[] spli=  userName.split("#");
                                et01.setText(spli[1]);
                            }else {
                                et01.setText("");
                            }
                            et04.setText(info.getData().getNickname());
//                            et05.setText(info.getData().getBio());
                            et07.setText(info.getData().getRealname());
                            et08.setText(info.getData().getPhone());
                        } else {
                            finish();
                            showToast(info.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_create_account;
    }

    @OnClick(R.id.btSure)
    public void onViewClicked() {

        String username = et01.getText().toString().trim();
        String password1 = et02.getText().toString().trim();
        String password2 = et03.getText().toString().trim();
        String nickname = et04.getText().toString().trim();
//        String bio = et05.getText().toString().trim();

        String mobile = et08.getText().toString().trim();
        String realname = et07.getText().toString().trim();
        if (realname.equals("")) {
            showToast("请输入真实姓名");
            return;
        }
        if (mobile.equals("")) {
            showToast("请输入手机号码");
            return;
        }

        if (username.equals("")) {
            showToast("请输入账号");
            return;
        }
        if (isCreate) {
            if (password1.equals("")) {
                showToast("请输入密码");
                return;
            }
            if (password2.equals("")) {
                showToast("请输入确认密码");
                return;
            }
        }

        if (nickname.equals("")) {
            showToast("请输入昵称");
            return;
        }
//        if (bio.equals("")) {
//            showToast("请输入职业");
//            return;
//        }
        if (!password2.equals(password1)) {
            showToast("密码与确认密码不同");
            return;
        }
        teacherRegister();
    }

    private void teacherRegister() {

        showLoading();
        String url = "";
        if (isCreate) {
            url = AppUrl.teacherRegister;
        } else {
            url = AppUrl.teacherProfile;
        }


        HttpRequest httpRequest = new HttpRequest(url);//"教师注册"
        httpRequest.add("username", prefix + "#" + et01.getText().toString().trim());
        httpRequest.add("password", et02.getText().toString().trim());
        httpRequest.add("nickname", et04.getText().toString().trim());
        httpRequest.add("bio", "--");

        httpRequest.add("phone", et08.getText().toString().trim());
        httpRequest.add("realname", et07.getText().toString().trim());
        if (!isCreate) {
            httpRequest.add("id", id);
        }


        CallServer.getInstance().postRequest("教师注册", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        showToast(info.getMsg());
                        if (info.getCode() == 1) {
                            setResult(10086);
                            finish();
                        } else {
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }
}
