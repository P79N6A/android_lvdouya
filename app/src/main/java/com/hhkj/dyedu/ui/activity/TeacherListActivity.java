package com.hhkj.dyedu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.VisualVoicemailService;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.TeacherListAdapter;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.getTeacherList;
import com.hhkj.dyedu.bean.model.Teacher;
import com.hhkj.dyedu.callback.EditInfoListener;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadListActivity;
import com.hhkj.dyedu.view.dialog.InputPasswordDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 * <p>
 * 教师列表
 */

public class TeacherListActivity extends BaseTitleHeadListActivity {
    
    TeacherListAdapter teacherListAdapter;
    @BindView(R.id.tvNum)
    TextView tvNum;
    
    private int delId = -1;
    private int delPos = -1;
    private InputPasswordDialog inputPasswordDialog;
    
    private Teacher teacher;
    
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_teacher_list;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_teacher_list));
        teacherListAdapter = new TeacherListAdapter();
        
        teacherListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv01:
                        startActivity(new Intent(getContext(), ScheduleActivity.class)
                                .putExtra("teacherId", teacherListAdapter.getData().get(position).getId())
                        
                        );
//                        jumpToActivity(ScheduleActivity.class);
                        break;
                    case R.id.iv02:
                        startActivityForResult(new Intent(getContext(), CreateAccountActivity.class)
                                .putExtra("isCreate", false)
                                .putExtra("now", now)
                                .putExtra("restrict", restrict)
                                .putExtra("id", teacherListAdapter.getData().get(position).getId()), 100);
                        break;
                    case R.id.iv03:
                        delId = teacherListAdapter.getData().get(position).getId();
                        delPos = position;
                        inputPasswordDialog = new InputPasswordDialog(getContext());
                        inputPasswordDialog.setEditInfoListener(new EditInfoListener() {
                            @Override
                            public void info(String info) {
                                teacherDel(delId, info);
                            }
                        });
                        
                        inputPasswordDialog.show();
                        
                        
                        break;
                    
                    case R.id.iv08:
                        teacher = teacherListAdapter.getData().get(position);
                        delPos = position;
                        restrict();
                        break;
                }
            }
        });
        
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(teacherListAdapter);
        
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getTeacherList();
            }
        });
        
        smartRefreshLayout.autoRefresh();
    }
    
    private void restrict() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.restrict);//"删除禁用"
        httpRequest.add("id", teacher.getId());
        if (teacher.getStatus().equals("normal")) {
            httpRequest.add("type", "disable");
        } else {
            httpRequest.add("type", "normal");
        }
        
        
        CallServer.getInstance().postRequest("删除教师", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        showToast(info.getMsg());
                        if (info.getCode() == 1) {
                            if (teacher.getStatus().equals("normal")) {
                                teacher.setStatus("disable");
                            } else {
                                teacher.setStatus("normal");
                            }
                            teacherListAdapter.notifyDataSetChanged();
//                            teacherListAdapter.notifyItemChanged(delId,teacher);
//                            delId=-1;
//                            teacher=null;
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
    
    private void teacherDel(int id, String info) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.teacherDel);//"删除教师"
        httpRequest.add("id", id);
        httpRequest.add("password", info);
        CallServer.getInstance().postRequest("删除教师", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        if (info.getCode() == 1) {
                            now = now - 1;
//                            setTitle("教师列表(" + now + "/" + restrict + ")");
                            tvNum.setVisibility(View.VISIBLE);
                            tvNum.setText("(" + now + "/" + restrict + ")");
                            
                            showToast(info.getMsg());
                            teacherListAdapter.remove(delPos);
                            inputPasswordDialog.dismiss();
                            if (teacherListAdapter.getData().size() == 0) {
                                setNoDataVisibility(View.VISIBLE);
                            } else {
                                setNoDataVisibility(View.GONE);
                            }
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
    
    private int restrict = 0;
    private int now = 0;
    
    private void getTeacherList() {
//        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.getTeacherList);//"教师列表"
//        httpRequest.add("---", ---);
        CallServer.getInstance().postRequest("教师列表", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        smartRefreshLayout.finishRefresh();
                        getTeacherList info = gson.fromJson(response, getTeacherList.class);
                        
                        
                        if (info.getCode() == 1) {
                            teacherListAdapter.replaceData(info.getData());
                        } else {
                        }
                        restrict = info.getRestrict();
                        now = teacherListAdapter.getData().size();
//                        setTitle("教师列表(" + now + "/" + restrict + ")");
                        tvNum.setVisibility(View.VISIBLE);
                        tvNum.setText("(" + now + "/" + restrict + ")");
                        
                        if (teacherListAdapter.getData().size() == 0) {
                            setNoDataVisibility(View.VISIBLE);
                        } else {
                            setNoDataVisibility(View.GONE);
                        }
                    }
                    
                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        smartRefreshLayout.finishRefresh();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }
    
    @OnClick(R.id.ivTop01)
    public void onViewClicked() {
        startActivityForResult(new Intent(getContext(), CreateAccountActivity.class)
                        .putExtra("now", now)
                        .putExtra("restrict", restrict)
                , 100);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 10086) {
            smartRefreshLayout.autoRefresh();
        }
    }
}
