package com.hhkj.dyedu.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.MainApplication;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.notion;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.ActivityCollector;
import com.hhkj.dyedu.ui.activity.base.BaseActivity;
import com.hhkj.dyedu.ui.activity.user.HelperActivity;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.view.NoticeDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/5/25
 * 登录成功界面
 */

public class LoginSuccessActivity extends BaseActivity {

    @BindView(R.id.card01)
    Button card01;
    @BindView(R.id.card02)
    Button card02;
    @BindView(R.id.card03)
    Button card03;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tv_notice)
    TextView tv_notice;



    @Override
    public int setLayoutId() {
        return R.layout.activity_login_success;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.role = CacheUtils.getGroup();
        //如果为老师账号  隐藏课表选课
        if (MainApplication.role == 2) {
            card01.setVisibility(View.GONE);
        } else if (MainApplication.role == 1) {

        } else {
            //一般不会出现这个弹窗
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("提示");
            builder.setMessage("未知角色" + MainApplication.role);
            builder.setCancelable(false);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CacheUtils.setLogin(false);
                    ActivityCollector.finishAll();
                    dialog.dismiss();
                }
            });
        }
        String log = "";
        //屏幕
//        log = log + "V" + AppUtils.getAppVersionName() + "\n";
//        log = log + "获取设备厂商" + DeviceUtils.getManufacturer() + "\n";
//        log = log + "获取设备型号" + DeviceUtils.getModel() + "\n";
//        log = log + "屏幕尺寸：" + ScreenUtils.getScreenHeight() + " " + ScreenUtils.getScreenWidth() + "\n";
//        log = log + "获取适配文件：" + String.valueOf(getResources().getDimension(R.dimen.px_test)) + "\n";
//        log = log + "单位尺寸：" + String.valueOf(getResources().getDimension(R.dimen.px_1)) + "\n";
//        tvVersion.setText(log);
        tvVersion.setText("V" + AppUtils.getAppVersionName());

//        SharedPreferences pre=getSharedPreferences("checkvalue", MODE_PRIVATE);
//        String value=pre.getString("ischeck", "");
//
//        if (value.endsWith("1")) {
//            //如果选择的是1，则对话框就不再弹出
//            noticeDialog.dismiss();
//        }else {
//            //如果没有选择，则对话框继续弹出
//            noticeDialog.show();
//        }
        Boolean b = CacheUtils.getCheck();


        Views(); //弹窗视图

    }

    @OnClick({R.id.card01, R.id.card02, R.id.card03, R.id.ivHelper, R.id.tv_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card01:
                //课程中心
                jumpToActivity(ThemeCategoryListActivity.class);
                break;
            case R.id.card02:
                //我的课表
                if (MainApplication.role == 2) {
                    startActivity(new Intent(getContext(), ScheduleActivity.class).putExtra("teacherId", Integer.parseInt(CacheUtils.getUid())));
                } else if (MainApplication.role == 1) {
                    jumpToActivity(TeacherListActivity.class);
                }
                break;
            case R.id.card03:
                //个人中心
                jumpToActivity(PersonalCenterActivity.class);
                break;
            case R.id.ivHelper:
                //帮助
                jumpToActivity(HelperActivity.class);
                break;
            case R.id.tv_notice:
//                if (CacheUtils.getCheck()) {   //记住不再提醒
//                    return;
//                }
                //通知公告
                showLoading();
                HttpRequest httpRequest = new HttpRequest(AppUrl.notion);
                CallServer.getInstance().postRequest("公告弹窗", httpRequest,
                        new HttpResponseListener() {
                            @Override
                            public void onSucceed(String response, Gson gson) {
                                closeLoading();
                                notion info = gson.fromJson(response, notion.class);
                                ArrayList<notion.DataBean> notices = (ArrayList<notion.DataBean>) info.getData();

                                NoticeDialog noticeDialog = new NoticeDialog(getContext());
                                noticeDialog.setUrl(notices.get(0).getContent());
                                noticeDialog.setTitle(notices.get(0).getTitle());

                                noticeDialog.setClicklistener(new NoticeDialog.ClickListenerInterface() {
                                    @Override
                                    public void doCancel() {
                                        noticeDialog.dismiss();
                                    }
                                    @Override
                                    public void setIsSelect(boolean isChecked) {
                                        CacheUtils.setCheck(isChecked);
                                    }
                                });
                                noticeDialog.show();
                            }

                            @Override
                            public void onFailed(Exception exception) {
                                closeLoading();

                            }
                        }, getContext());

        }
    }

    public void Views(){
        if (CacheUtils.getCheck()) {
            return;
        }
        HttpRequest httpRequest = new HttpRequest(AppUrl.notion);
        CallServer.getInstance().postRequest("公告弹窗", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        notion info = gson.fromJson(response, notion.class);
                        ArrayList<notion.DataBean> notices = (ArrayList<notion.DataBean>) info.getData();

                        NoticeDialog noticeDialog = new NoticeDialog(getContext());
                        noticeDialog.setUrl(notices.get(0).getContent());
                        noticeDialog.setTitle(notices.get(0).getTitle());

                        noticeDialog.setClicklistener(new NoticeDialog.ClickListenerInterface() {
                            @Override
                            public void doCancel() {
                                noticeDialog.dismiss();
                            }
                            @Override
                            public void setIsSelect(boolean isChecked) {
                                CacheUtils.setCheck(isChecked);
                            }
                        });
                        noticeDialog.show();
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();

                    }
                }, getContext());
    }
}
