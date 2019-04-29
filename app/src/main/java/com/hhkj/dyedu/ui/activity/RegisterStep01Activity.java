package com.hhkj.dyedu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.model.SmsBean;
import com.hhkj.dyedu.event.LoginOrRegisterFinishEvent;
import com.hhkj.dyedu.http.CallServer;
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

public class RegisterStep01Activity extends BaseTitleActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.bt01)
    Button bt01;
    @BindView(R.id.ivAgree)
    ImageView ivAgree;
    @BindView(R.id.tvAgree)
    TextView tvAgree;
    @BindView(R.id.layoutAgree)
    LinearLayout layoutAgree;

    private boolean isAgree = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setTitle("填写手机号码");
        bt01.setEnabled(false);

        et01.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 10) {
                    bt01.setEnabled(true);
                } else {
                    bt01.setEnabled(false);
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
        return R.layout.activity_register_step_01;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginOrRegisterFinishEvent event) {
        finish();
    }

    @OnClick({R.id.bt01, R.id.tvAgree, R.id.layoutAgree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt01:

                if (isAgree) {
                    if (et01.getText().toString().trim().equals("")) {
                        showToast("请输入手机号");
                    } else {
                        //发送验证码
                        sendSms();
                    }
                } else {
                    showToast("请先阅读并同意使用条款和隐私政策");
                }
//                jumpToActivity(RegisterStep02Activity.class);
                break;
            case R.id.ivAgree:
                break;
            case R.id.layoutAgree:
                //同意条款
                isAgree = !isAgree;

                if (isAgree) {
                    ivAgree.setImageResource(R.mipmap.dy_19);
                } else {
                    ivAgree.setImageResource(R.mipmap.dy_23);
                }
                break;
        }
    }

    private void sendSms() {
//        CallServer
//                .getInstance()
//                .getSMS(new SmsBean(et01.getText().toString().trim(), ""),
//                        new HttpResponseListener() {
//                            @Override
//                            public void onSucceed(String response, Gson gson) {
//                                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
//                                showToast(info.getMsg());
//                                if (info.getCode() == 1) {
//                                    Intent intent = new Intent(getContext(), RegisterStep02Activity.class);
//                                    intent.putExtra("phone", et01.getText().toString().trim());
//                                    startActivity(intent);
//                                }
//                            }
//
//                            @Override
//                            public void onFailed(Exception exception) {
//                                showToast(getString(R.string.toast_server_error));
//
//                            }
//                        }, getContext());

    }
}
