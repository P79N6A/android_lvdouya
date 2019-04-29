package com.hhkj.dyedu.ui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.GlobalVariable;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.register;
import com.hhkj.dyedu.bean.model.SmsBean;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.event.LoginOrRegisterFinishEvent;
import com.hhkj.dyedu.event.MoneyVipUpdateEvent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.LoginSuccessActivity;
import com.hhkj.dyedu.ui.activity.base.BaseTitleActivity;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.joooonho.SelectableRoundedImageView;
import com.sahooz.library.Country;
import com.zuoni.common.callback.SimpleTextWatcher;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/5/23
 * 登录
 */

public class LoginActivity extends BaseTitleActivity {
    
    
    @BindView(R.id.layoutTop)
    RelativeLayout layoutTop;
    @BindView(R.id.ivHead)
    SelectableRoundedImageView ivHead;
    @BindView(R.id.ivAreaChoose)
    ImageView ivAreaChoose;
    @BindView(R.id.layout01)
    RoundLinearLayout layout01;
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.layout02)
    RoundLinearLayout layout02;
    @BindView(R.id.tv03)
    TextView tv03;
    @BindView(R.id.layout03)
    RelativeLayout layout03;
    
    @BindView(R.id.et04)
    EditText et04;
    @BindView(R.id.layout04)
    RoundLinearLayout layout04;
    @BindView(R.id.et05)
    EditText et05;
    @BindView(R.id.layout05)
    RoundLinearLayout layout05;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.layout06)
    RelativeLayout layout06;
    @BindView(R.id.tvLogin)
    RoundTextView tvLogin;
    @BindView(R.id.tv07)
    TextView tv07;
    @BindView(R.id.tv08)
    TextView tv08;
    @BindView(R.id.tvGetAreaCode)
    TextView tvGetAreaCode;
    @BindView(R.id.tvGetAreaCode2)
    TextView tvGetAreaCode2;
    @BindView(R.id.ivAreaChoose2)
    ImageView ivAreaChoose2;
    
    
    private CountDownTimer timer;
    private boolean isUsePassword = true;
    private boolean isRemember = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setClickBlankArea2HideSoftInput(true);
        
        setTitle(getString(R.string.title_login));
        //隐藏返回按钮
        //现在 pad端 没有创建账号的功能
        layoutLeft.setVisibility(View.GONE);
        
        isRemember = CacheUtils.isRemember();
        if (isRemember) {
            checkbox.setChecked(true);
            et05.setText(CacheUtils.getPassword());
        } else {
            checkbox.setChecked(false);
        }
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CacheUtils.setRemember(isChecked);
            }
        });
        
        String phone = CacheUtils.getUsername();
        if (phone != null) {
            et04.setText(phone);
            String head = CacheUtils.getCacheHeadImage(et04.getText().toString().trim());
            if (head != null && !head.equals("")) {
                ImageLoaderUtils.setHeadImage(head, ivHead);
            }
        }
        et04.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 5) {
                    String head = CacheUtils.getCacheHeadImage(s.toString().trim());
                    if (head != null && !head.equals("")) {
                        ImageLoaderUtils.setHeadImage(head, ivHead);
                    } else {
                        ivHead.setImageResource(R.mipmap.dy_38);
                    }
                }
            }
        });
        
        timer = new CountDownTimer(GlobalVariable.CountTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int a = (int) (millisUntilFinished / 1000);
                tv03.setText("重新获取"+a + "s");
            }
            
            @Override
            public void onFinish() {
                tv03.setClickable(true);
                tv03.setText(R.string.login_11);
            }
        };
        isUsePassword = true;
        
        
        setInputUi();
        
    }
    
    private void setInputUi() {
        if (isUsePassword) {
            layout04.setVisibility(View.VISIBLE);
            layout05.setVisibility(View.VISIBLE);
            layout06.setVisibility(View.VISIBLE);
            
            layout01.setVisibility(View.GONE);
            layout02.setVisibility(View.GONE);
            layout03.setVisibility(View.GONE);
            
            tv07.setVisibility(View.VISIBLE);
            tv08.setVisibility(View.GONE);
        } else {
            
            layout04.setVisibility(View.GONE);
            layout05.setVisibility(View.GONE);
            layout06.setVisibility(View.GONE);
            
            layout01.setVisibility(View.VISIBLE);
            layout02.setVisibility(View.VISIBLE);
            layout03.setVisibility(View.VISIBLE);
            
            tv07.setVisibility(View.GONE);
            tv08.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }
    
    @OnClick({R.id.tvForgetPassword, R.id.tvLogin,
            R.id.tv07, R.id.tv08, R.id.tv03
            , R.id.tvGetAreaCode, R.id.tvGetAreaCode2
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvForgetPassword:
                //忘记密码
                jumpToActivity(ForgetActivity.class);
                break;
            case R.id.tvLogin:
                //登录
                if (isUsePassword) {
                    //账号密码登录
                    if (et04.getText().toString().trim().equals("")) {
                        showToast(getString(R.string.login_toast_01));
                        return;
                    }
                    if (et05.getText().toString().trim().equals("")) {
                        showToast(getString(R.string.login_toast_02));
                    } else {
                        login();
                    }
                } else {
                    //手机 + 验证码
                    if (!RegexUtils.isMobileSimple(et01.getText().toString().trim())) {
                        showToast(getString(R.string.login_toast_03));
                        return;
                    }
                    if (et02.getText().toString().trim().equals("")) {
                        showToast(getString(R.string.login_toast_04));
                    } else {
                        mobileLogin();
                    }
                }
                break;
            case R.id.tv03:
                //获取验证码
                if (et01.getText().toString().trim().equals("")) {
                    showToast(getString(R.string.login_toast_05));
                } else {
                    sendSms();
                }
                break;
            case R.id.tvGetAreaCode2:
            case R.id.tvGetAreaCode:
                //获取国家
                startActivityForResult(new Intent(getContext(), CountryPickActivity.class), GET_COUNTRY_CODE);
                break;
            case R.id.tv07:
                //使用手机+验证码
                isUsePassword = !isUsePassword;
                setInputUi();
                break;
            case R.id.tv08:
                //使用账号+密码
                isUsePassword = !isUsePassword;
                setInputUi();
                break;
        }
    }
    
    /**
     * 账号密码登录
     */
    private void login() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.login);//"登录"
        httpRequest.add("account", et04.getText().toString().trim());
        httpRequest.add("password", et05.getText().toString().trim());
        CallServer.getInstance().postRequest("登录", httpRequest,
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
                            CacheUtils.setPassword(et05.getText().toString().trim());
                            MoneyVipUpdateEvent event = new MoneyVipUpdateEvent(info.getData().getUserinfo().getMoney(), info.getData().getUserinfo().getExpiration() + "");
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
    
    /**
     * 手机 + 验证码
     */
    private void mobileLogin() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.mobilelogin);//"验证码登录"
        httpRequest.add("mobile", et01.getText().toString().trim());
        httpRequest.add("captcha", et02.getText().toString().trim());
    
        httpRequest.add("areaCode", areaCode);
    
        
        CallServer.getInstance().postRequest("验证码登录", httpRequest,
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
    
    /**
     * 发送验证码
     */
    private void sendSms() {
        tv03.setClickable(false);
        showLoading();
        CallServer
                .getInstance()
                .getSMS(new SmsBean(et01.getText().toString().trim(), "login",areaCode),
                        new HttpResponseListener() {
                            @Override
                            public void onSucceed(String response, Gson gson) {
                                closeLoading();
                                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                                showToast(info.getMsg());
                                if (info.getCode() == 1) {
                                    timer.start();
                                } else {
                                    tv03.setClickable(true);
                                }
                            }
                            
                            @Override
                            public void onFailed(Exception exception) {
                                tv03.setClickable(true);
                                closeLoading();
                                showToast(getString(R.string.toast_server_error));
                                
                            }
                        }, getContext());
        
    }
    
    private final int GET_COUNTRY_CODE = 1111;
    
    private String areaCode="86";
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_COUNTRY_CODE && resultCode == Activity.RESULT_OK) {
            Country country = Country.fromJson(data.getStringExtra("country"));
            areaCode=String.valueOf(country.code);
            tvGetAreaCode.setText("+" + country.code);
            tvGetAreaCode2.setText("+" + country.code);
        }
    }
    
}
