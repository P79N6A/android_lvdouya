package com.hhkj.dyedu.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.MainApplication;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.getUserInfo;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.callback.EditInfoListener;
import com.hhkj.dyedu.event.MoneyVipUpdateEvent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.user.ForgetActivity;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;
import com.hhkj.dyedu.view.EditPersonalInfoDialog;
import com.zuoni.common.dialog.picker.DataPickerSingleDialog;
import com.zuoni.common.dialog.picker.callback.OnSingleDataSelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class PersonalInfoFragment extends BaseFragment {

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.bio)
    TextView bio;
    @BindView(R.id.expiration)
    TextView expiration;
    @BindView(R.id.iv01)
    ImageView iv01;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.iv03)
    ImageView iv03;
    @BindView(R.id.iv04)
    ImageView iv04;
    @BindView(R.id.iv05)
    ImageView iv05;
    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.layout01)
    LinearLayout layout01;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.layout02)
    LinearLayout layout02;

    private PersonalCenterActivity personalCenterActivity;
    private boolean isInit = false;
    private boolean isGet = false;
    private EditPersonalInfoDialog.Builder builder;

    public void setPersonalCenterActivity(PersonalCenterActivity personalCenterActivity) {
        this.personalCenterActivity = personalCenterActivity;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_personal_info;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        MoneyVipUpdateEvent event = new MoneyVipUpdateEvent();
//        tvMoney.setText("余额："+event.getMoney()+"元");
        expiration.setText(event.getVipTimeS2());


        if (!isInit) {
            builder = new EditPersonalInfoDialog.Builder(getContext());
            isInit = true;



            if (MainApplication.role == 2) {
                iv05.setVisibility(View.GONE);
                mView.findViewById(R.id.layout06).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.tvChangeP).setVisibility(View.GONE);

            }else {
                layout01.setVisibility(View.GONE);
                layout02.setVisibility(View.GONE);
                
                mView.findViewById(R.id.tvChangeP).setVisibility(View.VISIBLE);
    
                mView.findViewById(R.id.tvChangeP).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), ForgetActivity.class).putExtra("type",2));
                    }
                });
            }


            if (!isGet) {
                username.setText("111111");
                getUserInfo();
            }
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void getUserInfo() {
        if (personalCenterActivity != null) {
            personalCenterActivity.showLoading();
        }
        HttpRequest httpRequest = new HttpRequest(AppUrl.getUserInfo);//"获取个人资料"
//        httpRequest.add("---", ---);
        CallServer.getInstance().postRequest("获取个人资料", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        personalCenterActivity.closeLoading();
                        getUserInfo info = gson.fromJson(response, getUserInfo.class);
                        if (info.getCode() == 1) {
                            isGet = true;
                            CacheUtils.setUserInfo(info.getData());
                            username.setText(info.getData().getUsername());
                            nickname.setText(info.getData().getNickname());
                            if (info.getData().getGender() == 0) {
                                gender.setText("女");
                            } else {
                                gender.setText("男");
                            }
                            age.setText(info.getData().getAge() + "");
                            bio.setText(info.getData().getBio());
//                            expiration.setText(info.getData().getExpiration() + "");

                            tv01.setText(info.getData().getRealname());
                            tv02.setText(info.getData().getPhone());
                            MoneyVipUpdateEvent event = new MoneyVipUpdateEvent(info.getData().getMoney(), info.getData().getExpiration() + "");
                            EventBus.getDefault().post(event);


                        } else {
                            personalCenterActivity.showToast(info.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        personalCenterActivity.closeLoading();
                        personalCenterActivity.showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MoneyVipUpdateEvent event) {
//        tvMoney.setText("余额："+event.getMoney()+"元");
        expiration.setText(event.getVipTimeS2());

    }

    @OnClick({R.id.iv01, R.id.iv02, R.id.iv03, R.id.iv04, R.id.iv05})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv01:
                //修改昵称
                builder.setTitle("修改昵称");
                builder.setOldInfo(nickname.getText().toString());
                builder.doType(0);
                builder.setOnClickListener(new EditInfoListener() {
                    @Override
                    public void info(String info) {
                        nickname.setText(info);
                        CacheUtils.setNickName(info);
                        //
                        profile("nickname", info);

                    }
                });
                builder.create().show();

                break;
            case R.id.iv02:
                DataPickerSingleDialog.Builder builder1 = new DataPickerSingleDialog.Builder(getContext());
                List<String> list = new ArrayList<>();
                list.add("男");
                list.add("女");
                builder1.setData(list);
                builder1.setOnDataSelectedListener(new OnSingleDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue) {

                        gender.setText(itemValue);
                        String type;
                        if (itemValue.equals("男")) {
                            type = "1";
                        } else {
                            type = "0";
                        }
                        profile("gender", type);
                    }
                });
                builder1.create().show();

                break;
            case R.id.iv03:
                // 年龄
                builder.setTitle("修改年龄");
                builder.setOldInfo(age.getText().toString());
                builder.doType(1);
                builder.setOnClickListener(new EditInfoListener() {
                    @Override
                    public void info(String info) {
                        age.setText(info);
                        //
                        profile("age", info);

                    }
                });
                builder.create().show();


                break;
            case R.id.iv04:
                builder.setTitle("修改职业");
                builder.setOldInfo(bio.getText().toString());
                builder.doType(3);
                builder.setOnClickListener(new EditInfoListener() {
                    @Override
                    public void info(String info) {
                        bio.setText(info);
                        //
                        profile("bio", info);

                    }
                });
                builder.create().show();
                break;
            case R.id.iv05:
                //购买会员
                personalCenterActivity.personalInfoFragmentEvent(1);
                break;
        }
    }

    private void profile(String key, String value) {
//        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.profile);//""
        httpRequest.add(key, value);
        CallServer.getInstance().postRequest("", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
//                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        if (info.getCode() == 1) {
                        } else {
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
//                        closeLoading();
//                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }


}
