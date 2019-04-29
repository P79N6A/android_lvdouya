package com.hhkj.dyedu.ui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.GlobalVariable;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.model.SmsBean;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.ForgetSuccessActivity;
import com.hhkj.dyedu.ui.activity.base.BaseTitleActivity;
import com.sahooz.library.Country;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/9/6
 *
 * @author zangyi 767450430
 * 忘记密码
 */
public class ForgetActivity extends BaseTitleActivity {
    @BindView(R.id.layoutTop)
    RelativeLayout layoutTop;
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.layout04)
    LinearLayout layout04;
    @BindView(R.id.tvGetCode)
    TextView tvGetCode;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.et03)
    EditText et03;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.etcode)
    EditText etcode;
    @BindView(R.id.tvGetAreaCode)
    TextView tvGetAreaCode;
    @BindView(R.id.ivAreaChoose)
    ImageView ivAreaChoose;
    private CountDownTimer timer;
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_forget;
    }
    
    private int type;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setClickBlankArea2HideSoftInput(true);
        type = getIntent().getIntExtra("type", 0);
//        if(type==0){
//            setTitle(getString(R.string.title_forget));
//        }else {
//            setTitle(getString(R.string.title_forget_2));
//        }
        
        timer = new CountDownTimer(GlobalVariable.CountTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int a = (int) (millisUntilFinished / 1000);
                tvGetCode.setText("重新获取" + a + "s");
            }
            
            @Override
            public void onFinish() {
                tvGetCode.setClickable(true);
                tvGetCode.setText(R.string.login_11);
            }
        };
    }
    
    @OnClick({R.id.tvGetCode, R.id.btLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvGetCode:
                if (et01.getText().toString().trim().equals("")) {
                    showToast(getString(R.string.forget_toast_01));
                } else {
                    //发送验证码
                    sendSms();
                }
                break;
            case R.id.btLogin:
                
                String phone = et01.getText().toString();
                String code = etcode.getText().toString();
                String p1 = et02.getText().toString().trim();
                String p2 = et03.getText().toString().trim();
                
                if (et01.getText().toString().trim().equals("")) {
                    showToast(getString(R.string.forget_toast_01));
                    return;
                }
                if (code.equals("")) {
                    showToast(getString(R.string.forget_toast_02));
                    return;
                }
                if (p1.equals("")) {
                    showToast(getString(R.string.forget_toast_03));
                    return;
                }
                if (p2.equals("")) {
                    showToast(getString(R.string.forget_toast_04));
                    return;
                }
                if (!p2.equals(p1)) {
                    showToast(getString(R.string.forget_toast_05));
                    return;
                }
                resetpwd();
                
                
                break;
        }
    }
    
    private void resetpwd() {
        showLoading();
        //""
        HttpRequest httpRequest = new HttpRequest(AppUrl.resetpwd);
        httpRequest.add("mobile", et01.getText().toString());
        httpRequest.add("newpassword", et02.getText().toString());
        httpRequest.add("captcha", etcode.getText().toString());
        CallServer.getInstance().postRequest("", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        showToast(info.getMsg());
                        if (info.getCode() == CallServer.HTTP_SUCCESS_CODE) {
                            finish();
                            if (type == 0) {
                                jumpToActivity(ForgetSuccessActivity.class);
                            }
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
    
    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }
    
    private void sendSms() {
        tvGetCode.setClickable(false);
        CallServer
                .getInstance()
                .getSMS(new SmsBean(et01.getText().toString().trim(), "resetpwd", areaCode),
                        new HttpResponseListener() {
                            @Override
                            public void onSucceed(String response, Gson gson) {
                                BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                                showToast(info.getMsg());
                                if (info.getCode() == 1) {
                                    timer.start();
                                } else {
                                    tvGetCode.setClickable(true);
                                }
                            }
                            
                            @Override
                            public void onFailed(Exception exception) {
                                showToast(getString(R.string.toast_server_error));
                                tvGetCode.setClickable(true);
                                
                            }
                        }, getContext());
        
    }
    
    
    private String areaCode = "86";
    private final int GET_COUNTRY_CODE = 1111;
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_COUNTRY_CODE && resultCode == Activity.RESULT_OK) {
            Country country = Country.fromJson(data.getStringExtra("country"));
            areaCode = String.valueOf(country.code);
            tvGetAreaCode.setText("+" + country.code);
        }
    }
    
    @OnClick({R.id.tvGetAreaCode, R.id.ivAreaChoose})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.tvGetAreaCode:
            case R.id.ivAreaChoose:
                startActivityForResult(new Intent(getContext(), CountryPickActivity.class), GET_COUNTRY_CODE);
                break;
        }
    }
}
