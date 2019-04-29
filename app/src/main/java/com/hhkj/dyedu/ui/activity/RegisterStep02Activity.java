package com.hhkj.dyedu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.GlobalVariable;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.model.SmsBean;
import com.hhkj.dyedu.event.LoginOrRegisterFinishEvent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleActivity;
import com.zuoni.common.callback.SimpleTextWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/5/23
 */

public class RegisterStep02Activity extends BaseTitleActivity {


    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.bt04)
    Button bt04;


    private String phone;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("填写验证码");
        EventBus.getDefault().register(this);
        tv02.setClickable(false);
        timer = new CountDownTimer(GlobalVariable.CountTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int a = (int) (millisUntilFinished / 1000);
                tv02.setText("重新发送(" + a + ")s");
            }

            @Override
            public void onFinish() {
                tv02.setClickable(true);
                tv02.setText("获取验证码");
            }
        };

        phone = getIntent().getStringExtra("phone");
        tv01.setText(phone);

        timer.start();


        bt04.setEnabled(false);
        et01.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 4) {
                    bt04.setEnabled(true);
                } else {
                    bt04.setEnabled(false);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_register_step_02;
    }

    @OnClick({R.id.tv02, R.id.bt04})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv02:
                sendSms();
                break;
            case R.id.bt04:

                if (et01.getText().toString().trim().equals("")) {
                    showToast("请输入短信验证码");
                } else {
                    smsCheck();
                }


                break;
        }
    }

    private void sendSms() {
//        tv02.setClickable(false);
//        CallServer
//                .getInstance()
//                .getSMS(new SmsBean(phone, ""),
//                        new HttpResponseListener() {
//                            @Override
//                            public void onSucceed(String response, Gson gson) {
//                                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
//                                showToast(info.getMsg());
//                                if (info.getCode() == 1) {
//                                    tv02.setClickable(false);
//                                    timer.start();
//                                } else {
//                                    tv02.setClickable(true);
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailed(Exception exception) {
//                                tv02.setClickable(true);
//                                showToast(getString(R.string.toast_server_error));
//
//                            }
//                        }, getContext());

    }

    private void smsCheck() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.sms_check);//"检验验证码正确"
        httpRequest.add("mobile", phone);
        httpRequest.add("event", "");
        httpRequest.add("captcha", et01.getText().toString().trim());
        CallServer.getInstance().postRequest("检验验证码正确", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        showToast(info.getMsg());
                        if (info.getCode() == 1) {
                            Intent intent = new Intent(getContext(), RegisterStep03Activity.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginOrRegisterFinishEvent event) {
        finish();
    }
}
