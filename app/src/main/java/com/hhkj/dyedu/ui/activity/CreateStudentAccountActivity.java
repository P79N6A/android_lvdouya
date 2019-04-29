package com.hhkj.dyedu.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.RegexUtils;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.getStudent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadActivity;
import com.zuoni.common.dialog.picker.DataPickerSingleDialog;
import com.zuoni.common.dialog.picker.callback.OnSingleDataSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class CreateStudentAccountActivity extends BaseTitleHeadActivity {
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.et03)
    EditText et03;
    @BindView(R.id.et04)
    EditText et04;
    @BindView(R.id.et05)
    EditText et05;
    @BindView(R.id.et06)
    EditText et06;
    @BindView(R.id.et07)
    EditText et07;
    @BindView(R.id.btSure)
    RoundTextView btSure;

    private boolean isCreate = true;
    private int id;

    private String scheduleId;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("新增学生");
        scheduleId = getIntent().getStringExtra("scheduleId");
        isCreate = getIntent().getBooleanExtra("isCreate", true);
        if (!isCreate) {
            //不让修改
            et01.setEnabled(false);
            id = getIntent().getIntExtra("id", -1);
            getTeacherInfo();
        }
        et04.setCursorVisible(false);
        et04.setFocusable(false);
        et04.setHint("请选择性别");
        et04.setFocusableInTouchMode(false);

        et04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataPickerSingleDialog.Builder builder1 = new DataPickerSingleDialog.Builder(getContext());
                List<String> list = new ArrayList<>();
                list.add("男");
                list.add("女");
                builder1.setData(list);
                builder1.setOnDataSelectedListener(new OnSingleDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue) {
                        et04.setText(itemValue);
                        if (itemValue.equals("男")) {
                            sex = "1";
                        } else {
                            sex = "0";
                        }
                    }
                });
                builder1.create().show();
            }
        });

    }

    private void getTeacherInfo() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.getStudent);//"获取教师信息"
        httpRequest.add("studentId", id);
        CallServer.getInstance().postRequest("获取教师信息", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        getStudent info = gson.fromJson(response, getStudent.class);
                        if (info.getCode() == 1) {

                            et01.setText(info.getData().getUsername());
                            et02.setText(info.getData().getNickname());
                            et03.setText(info.getData().getAge() + "");
                            if (info.getData().getGender() == 1) {
                                et04.setText("男");
                                sex = "1";
                            } else {
                                et04.setText("女");
                                sex = "0";
                            }
                            et05.setText(info.getData().getStudy());
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
        return R.layout.activity_create_student_account;
    }

    @OnClick(R.id.btSure)
    public void onViewClicked() {

        String info1 = et01.getText().toString().trim();
        String info2 = et02.getText().toString().trim();
        String info3 = et03.getText().toString().trim();
        String info4 = et04.getText().toString().trim();
        String info5 = et05.getText().toString().trim();

        if (!RegexUtils.isMobileSimple(info1)) {
            showToast("请输入正确的家长手机号");
            return;
        }
        if (info2.equals("")) {
            showToast("请输入学生姓名");
            return;
        }
        if (info3.equals("")) {
            showToast("请输入年龄");
            return;
        }
        if (info4.equals("")) {
            showToast("请输入选择性别");
            return;
        }
        if(isCreate){
            if(et06.getText().toString().trim().equals("")){
                showToast("请输入密码");
                return;
            }

            if(et07.getText().toString().trim().equals("")){
                showToast("请输入确认密码");
                return;
            }
        }
        if(!et07.getText().toString().trim().equals(et06.getText().toString().trim())){
            showToast("密码与确认密码不一致");
            return;
        }


        studentCreate();
    }

    private void studentCreate() {

        showLoading();
        String url = "";
        if (isCreate) {
            url = AppUrl.studentCreate;
        } else {
            url = AppUrl.studentUpdate;
        }

        HttpRequest httpRequest = new HttpRequest(url);//"学生创建"
        httpRequest.add("scheduleId", scheduleId);
        httpRequest.add("username", et01.getText().toString().trim());
        httpRequest.add("nickname", et02.getText().toString().trim());
        httpRequest.add("age", et03.getText().toString().trim());
        httpRequest.add("gender", sex);
        httpRequest.add("study", et05.getText().toString().trim());
        httpRequest.add("password", et06.getText().toString().trim());
        if (!isCreate) {
            httpRequest.add("studentId", id);
        }


        CallServer.getInstance().postRequest("学生创建", httpRequest,
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
