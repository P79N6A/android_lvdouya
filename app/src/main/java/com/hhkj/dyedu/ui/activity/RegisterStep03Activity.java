package com.hhkj.dyedu.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.register;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.event.LoginOrRegisterFinishEvent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/5/23
 */

public class RegisterStep03Activity extends BaseTitleActivity {


    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.bt04)
    Button bt04;

    private String phone;

    @Override
    public int setLayoutId() {
        return R.layout.activity_register_step_03;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone = getIntent().getStringExtra("phone");
        setTitle("填写密码");
    }


    @OnClick(R.id.bt04)
    public void onViewClicked() {

        String p1 = et01.getText().toString().trim();
        String p2 = et02.getText().toString().trim();

        if (p1.equals("")) {
            showToast("请输入密码");
            return;
        }
        if (p2.equals("")) {
            showToast("请输入确认密码");
            return;
        }
        if (p1.equals(p2)) {
            register();
        } else {
            showToast("密码与确认密码不一致");
        }
    }

    private void register() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.register);//"注册"
        httpRequest.add("username", phone);
        httpRequest.add("mobile", phone);
        httpRequest.add("password", et01.getText().toString().trim());
        CallServer.getInstance().postRequest("注册", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        register info = gson.fromJson(response, register.class);
                        showToast(info.getMsg());
                        if (info.getCode() == 1) {
                            //注册成功
                            EventBus.getDefault().post(new LoginOrRegisterFinishEvent(2));
                            CacheUtils.setUserInfo(info.getData().getUserinfo());
                            CacheUtils.setLogin(true);
                            jumpToActivity(LoginSuccessActivity.class);
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
