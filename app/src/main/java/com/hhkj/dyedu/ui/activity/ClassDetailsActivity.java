package com.hhkj.dyedu.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.MainApplication;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.CourseAdapter05;
import com.hhkj.dyedu.bean.gson.schedule_info;
import com.hhkj.dyedu.bean.gson.schedule_update;
import com.hhkj.dyedu.bean.model.ScheduleCourseBean;
import com.hhkj.dyedu.bean.model.ScheduleUnit;
import com.hhkj.dyedu.bean.model.Theme;
import com.hhkj.dyedu.callback.EditInfoListener;
import com.hhkj.dyedu.callback.SetClassTimeListener;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadListActivity;
import com.hhkj.dyedu.view.EditPersonalInfoDialog;
import com.hhkj.dyedu.view.SetClassTimeDialog;
import com.hhkj.dyedu.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/26
 */

public class ClassDetailsActivity extends BaseTitleHeadListActivity {
    
    @BindView(R.id.ivTop01)
    ImageView ivTop01;
    @BindView(R.id.iv01)
    ImageView iv01;
    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.tv03)
    TextView tv03;
    
    private ScheduleUnit scheduleUnit;
    
    private CourseAdapter05 courseAdapter05;
    
    private int start = -1, end = -1;
    private String scheduleId;
    private EditPersonalInfoDialog.Builder builder;
    
    private int timeType;
    private int state;
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_class_details;
    }
    
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        back();
    }
    
    private void back() {
        
        if (tv01.getText().toString().trim().equals("")) {
            
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            
            builder.setTitle("提示");
            
            builder.setMessage("您还未设置班级名称,该班级将不被保存");
            
            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    setResult(100);
                    finish();
                }
            });
            builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return;
        }
        if (tv02.getText().toString().trim().equals("")) {
            
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            
            builder.setTitle("提示");
            
            builder.setMessage("您还未设置上课时间,该班级将不被保存");
            
            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    setResult(100);
                    finish();
                }
            });
            builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return;
        }
        finish();
    }
    
    private Theme clickTheme;
    
    private int clickPos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("课表详情");
        
        scheduleId = getIntent().getStringExtra("scheduleId");
        scheduleUnit = (ScheduleUnit) getIntent().getSerializableExtra("scheduleUnit");
        if (scheduleUnit == null) {
            LogUtil.i("2222222222222222");
            timeType = getIntent().getIntExtra("timeType", 1);
        } else {
            timeType = Integer.parseInt(scheduleUnit.getType());
        }
        
        if (MainApplication.role == 2) {
            ivTop01.setVisibility(View.GONE);
        } else {
            ivTop01.setImageResource(R.mipmap.dy_56);
            setSize(ivTop01, SizeUtils.dp2px(39), SizeUtils.dp2px(100));
            ivTop01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (state == 1) {
                        schedule_update(-1, -1, "", 0);
                    } else {
                        schedule_update(-1, -1, "", 1);
                    }
                }
            });
        }
        
        
        courseAdapter05 = new CourseAdapter05();
        courseAdapter05.setShowTag(2);
        if (MainApplication.role == 2) {
            courseAdapter05.setShowAdd(false);
        } else {
            courseAdapter05.setShowAdd(true);
        }
        
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);
        
        courseAdapter05.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.layoutContent03:
                        if (tv01.getText().toString().trim().equals("")) {
                            showToast("请先设置班级名称");
                            return;
                        }
                        if (tv02.getText().toString().trim().equals("")) {
                            showToast("请先设置上课时间");
                            return;
                        }
                        startActivityForResult(new Intent(getContext(), ChooseCourseActivity.class).putExtra("type", 2).putExtra("scheduleId", scheduleId), 100);
                        break;
                    case R.id.layoutLock:
                        clickTheme = (Theme) adapter.getData().get(position);
                        clickPos = position;
                        if (MainApplication.role == 2) {
                            if(clickTheme.getLock()==1){
                                showToast("该主题已被关闭");
                            }
                        } else {
                            teacherRestrict();
                        }
                     
                        
                        break;
                    case R.id.layoutContent02:
                        Intent intent = new Intent(getContext(), CourseThemeActivity.class);
                        intent.putExtra("Theme", (Theme) adapter.getData().get(position));
                        intent.putExtra("scheduleId", scheduleId);
                        intent.putExtra("showTag", 2);
                        startActivityForResult(intent, 100);
                        break;
                    
                }
            }
        });
        
        setNoDataVisibility(View.GONE);
//        courseAdapter05.replaceData(getFalseData());
        
        smartRefreshLayout.setPadding(0, 34, 0, 0);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(courseAdapter05);
        builder = new EditPersonalInfoDialog.Builder(getContext());
        
        setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        
        schedule_info();
        
    }
    
    @OnClick(R.id.iv01)
    public void onViewClicked() {
        if (tv01.getText().toString().trim().equals("")) {
            showToast("请先设置班级名称");
            return;
        }
        if (tv02.getText().toString().trim().equals("")) {
            showToast("请先设置上课时间");
            return;
        }
        startActivityForResult(new Intent(getContext(), StudentListActivity.class).putExtra("scheduleId", scheduleId), 100);
    }
    
    @OnClick({R.id.tv01, R.id.tv02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv01:
                if (MainApplication.role == 1) {
                    //设置班级名称
                    builder.setTitle("修改班级名称");
                    builder.setOldInfo(tv01.getText().toString());
                    builder.doType(4);
                    builder.setOnClickListener(new EditInfoListener() {
                        @Override
                        public void info(String info) {
                            schedule_update(-1, -1, info, -1);
                        }
                    });
                    builder.create().show();
                }
                
                break;
            case R.id.tv02:
                if (MainApplication.role == 1) {
                    SetClassTimeDialog.Builder builder = new SetClassTimeDialog.Builder(getContext());
                    builder.setTimeType(timeType);
                    builder.setStart(start);
                    builder.setEnd(end);
                    
                    builder.setSetClassTimeListener(new SetClassTimeListener() {
                        @Override
                        public void setClassTime(int start, int end, String des, SetClassTimeDialog setClassTimeDialog) {
                            boolean canUpdate = true;
                            
                            if (scheduleUnit != null) {
                                for (int i = 0; i < scheduleUnit.getMon().size(); i++) {
                                    if (judgeTime(start, end, scheduleUnit.getMon().get(i))) {
                                        canUpdate = false;
                                        break;
                                    }
                                }
                            }
                            if (canUpdate) {
                                schedule_update(start, end, "", -1);
                                setClassTimeDialog.dismiss();
                            } else {
                                showToast("所选择的时间与其他课程有冲突!");
                            }
                        }
                    });
                    builder.create().show();
                }
                
                break;
        }
    }
    
    private void schedule_update(int start, int end, String name, int status) {
        
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.schedule_update);//"修改课表的内容"
        httpRequest.add("scheduleId", scheduleId);
        
        if (status != -1) {
            httpRequest.add("status", status);
        }
        if (!name.equals("")) {
            httpRequest.add("class_name", name);
        }
        
        if (start != -1) {
            httpRequest.add("start_time", start);
            httpRequest.add("end_time", end);
        }
        CallServer.getInstance().postRequest("修改课表的内容", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        schedule_update info = gson.fromJson(response, schedule_update.class);
                        if (info.getCode() == 1) {
                            if (info.getData().getStart_time() != -1) {
                                tv02.setText(setTimes(info.getData().getStart_time(), info.getData().getEnd_time()));
                                ClassDetailsActivity.this.start = info.getData().getStart_time();
                                ClassDetailsActivity.this.end = info.getData().getEnd_time();
                                
                            } else if (!info.getData().getClass_name().equals("")) {
                                tv01.setText(info.getData().getClass_name());
                            }
                            ClassDetailsActivity.this.state = info.getData().getStatus();
                            if (ClassDetailsActivity.this.state != 1) {
                                ivTop01.setImageResource(R.mipmap.dy_56);
                            } else {
                                ivTop01.setImageResource(R.mipmap.dy_57);
                            }
                            setResult(10086);
                        } else {
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
    
    //是否在区间内
    private boolean judgeTime(int start, int end, ScheduleCourseBean scheduleCourseBean) {
        
        
        int pStart = scheduleCourseBean.getStart_time();
        int pEndTime = scheduleCourseBean.getEnd_time();
        LogUtil.i("比较时间", "pStart" + pStart + "pEndTime" + pEndTime);
        LogUtil.i("比较时间 我输入的", "start" + start + "end" + end);
        
        String pId = String.valueOf(scheduleCourseBean.getId());
        if (!pId.equals(scheduleId.trim())) {
            if (start < pStart) {
                //开始时间在区间左侧
                if (end <= pStart) {
                    //结束时间在区间左侧
                    return false;
                } else {
                    //结束时间在区间右侧
                    return true;
                }
                
            } else if (start >= pStart && start <= pEndTime) {
                //开始时间在区间内
                return true;
            } else {
                //开始时间在区间右侧
                return false;
            }
        } else {
            return false;
        }
    }
    
    private String setTimes(int start_time, int end_time) {
        
        int a1 = start_time / 60;
        int a2 = start_time % 60;
        
        int a3 = end_time / 60;
        int a4 = end_time % 60;
        
        String des = add0(a1) + ":" + add0(a2) + " ~ " + add0(a3) + ":" + add0(a4);
        
        return des;
    }
    
    private String add0(int a1) {
        if (a1 < 10) {
            return "0" + a1;
        } else {
            return a1 + "";
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == 100 && resultCode == 10086) {
            schedule_info();
        }
    }
    
    private void teacherRestrict() {
        
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.teacherRestrict);//"获取课表详情"
        httpRequest.add("scheduleId", scheduleId);
        httpRequest.add("themeId", clickTheme.getId());
        
        if (clickTheme.getLock() == 0) {
            httpRequest.add("type", "disable");
        } else {
            httpRequest.add("type", "normal");
        }
        CallServer.getInstance().postRequest("主题锁", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        schedule_info info = gson.fromJson(response, schedule_info.class);
                        showToast(info.getMsg());
                        if (info.getCode() == 1) {
                            if (clickTheme.getLock() == 0) {
                                clickTheme.setLock(1);
                            } else {
                                clickTheme.setLock(0);
                            }
                            courseAdapter05.notifyItemChanged(clickPos, clickTheme);
                        } else {
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
    
    private void schedule_info() {
        
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.schedule_info);//"获取课表详情"
        httpRequest.add("scheduleId", scheduleId);
        CallServer.getInstance().postRequest("获取课表详情", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        schedule_info info = gson.fromJson(response, schedule_info.class);
                        if (info.getCode() == 1) {
                            
                            tv01.setText(info.getData().getClass_name().trim());
                            if (info.getData().getStart_time() != -1) {
                                tv02.setText(setTimes(info.getData().getStart_time(), info.getData().getEnd_time()));
                                start = info.getData().getStart_time();
                                end = info.getData().getEnd_time();
                                tv03.setText(info.getData().getClass_num());
                            }
                            LogUtil.i("角色" + MainApplication.role);
                            if (MainApplication.role == 1) {
                                info.getData().getTheme().add(new Theme());
                            }
                            LogUtil.i("角色" + info.getData().getTheme().size());
                            courseAdapter05.replaceData(info.getData().getTheme());
                            
                            state = info.getData().getStatus();
                            if (state != 1) {
                                ivTop01.setImageResource(R.mipmap.dy_56);
                            } else {
                                ivTop01.setImageResource(R.mipmap.dy_57);
                            }
                            
                        } else {
                            showToast(info.getMsg());
                            finish();
                        }
                    }
                    
                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                        finish();
                    }
                }, getContext());
        
    }
}
