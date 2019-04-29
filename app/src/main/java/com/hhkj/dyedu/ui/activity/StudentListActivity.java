package com.hhkj.dyedu.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.StudentAdapter;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.studentFile;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadListActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.hhkj.dyedu.utils.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/27
 */
public class StudentListActivity extends BaseTitleHeadListActivity {
    @BindView(R.id.ivChooseAll)
    ImageView ivChooseAll;
    @BindView(R.id.layoutChooseAll)
    LinearLayout layoutChooseAll;

    private StudentAdapter studentAdapter;
    private String scheduleId;
    private boolean isChooseAll = false;
    private String syudentId;
    private boolean needA = false;
    private int chooseP;

    @Override
    public int setLayoutId() {
        return R.layout.activity_student_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("学生档案");
        scheduleId = getIntent().getStringExtra("scheduleId");
        studentAdapter = new StudentAdapter();

        studentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.layout01:
                        studentAdapter.getData().get(position).setChoose(!studentAdapter.getData().get(position).isChoose());
                        studentAdapter.notifyItemChanged(position);

                        int size = studentAdapter.getData().size();
                        boolean hava = false;
                        for (int i = 0; i < size; i++) {
                            if (!studentAdapter.getData().get(i).isChoose()) {
//                                LogUtil.i("找到未选中的");
                                isChooseAll = false;
                                hava = true;
                                ivChooseAll.setImageResource(R.mipmap.dy_42);
                                break;
                            }
                        }
                        if (!hava) {
                            isChooseAll = true;
                            ivChooseAll.setImageResource(R.mipmap.content_14);
                        }

                        break;
                    case R.id.iv01:
                        startActivityForResult(new Intent(getContext(), CreateStudentAccountActivity.class).putExtra("scheduleId", scheduleId).putExtra("isCreate", false)
                                .putExtra("id", studentAdapter.getData().get(position).getId()), 100);
                        break;
                    case R.id.iv02:

                        syudentId = studentAdapter.getData().get(position).getId() + "";
                        needA = false;
                        chooseP = position;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("是否删除学生");
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                studentAdapter.remove(chooseP);
                                if (studentAdapter.getData().size() == 0) {
                                    setNoDataVisibility(View.VISIBLE);
                                } else {
                                    setNoDataVisibility(View.GONE);
                                }

                                studentDel(syudentId + "", needA);
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("否", null);
                        builder.create().show();


                        break;
                }

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(studentAdapter);

        smartRefreshLayout.setEnableLoadMore(false);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                studentAdapter.replaceData(new ArrayList<studentFile.DataBean>());
                getStudent();
            }
        });

        smartRefreshLayout.autoRefresh();

    }

    private void studentDel(String studentId, final boolean needRefresh) {

        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.studentDel);//"删除学生"
        httpRequest.add("studentId", studentId);
        httpRequest.add("scheduleId", scheduleId);
        CallServer.getInstance().postRequest("删除学生", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);

                        if (needRefresh) {
                            showToast(info.getMsg());
                            if (info.getCode() == 1) {
                                smartRefreshLayout.autoRefresh();
                                setResult(10086);
                            } else {
                            }
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());

    }

    private void getStudent() {
//        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.studentFile);//"获取学生列表"
        httpRequest.add("scheduleId", scheduleId);
        CallServer.getInstance().postRequest("获取学生列表", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        smartRefreshLayout.finishRefresh();
//                        closeLoading();
                        studentFile info = gson.fromJson(response, studentFile.class);
                        if (info.getCode() == 1) {
                            studentAdapter.replaceData(info.getData());
                        } else {
                            showToast(info.getMsg());
                        }

                        if (studentAdapter.getData().size() == 0) {
                            setNoDataVisibility(View.VISIBLE);
                        } else {
                            setNoDataVisibility(View.GONE);
                        }

                        isChooseAll = false;
                        ivChooseAll.setImageResource(R.mipmap.dy_42);
                    }

                    @Override
                    public void onFailed(Exception exception) {
//                        closeLoading();
                        smartRefreshLayout.finishRefresh();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 10086) {
            smartRefreshLayout.autoRefresh();
            setResult(10086);
        }
    }

    @OnClick({R.id.layoutChooseAll, R.id.iv01, R.id.iv02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutChooseAll:
                isChooseAll = !isChooseAll;
                int size = studentAdapter.getData().size();
                for (int i = 0; i < size; i++) {
                    studentAdapter.getData().get(i).setChoose(isChooseAll);
                }
                studentAdapter.notifyDataSetChanged();
                if (isChooseAll) {
                    ivChooseAll.setImageResource(R.mipmap.content_14);
                } else {
                    ivChooseAll.setImageResource(R.mipmap.dy_42);
                }
                break;
            case R.id.iv01:
                startActivityForResult(new Intent(getContext(), CreateStudentAccountActivity.class).putExtra("scheduleId", scheduleId), 100);
                break;
            case R.id.iv02:
                StringBuilder students = new StringBuilder();
                for (int i = 0; i < studentAdapter.getData().size(); i++) {
                    if (studentAdapter.getData().get(i).isChoose()) {
                        students.append(studentAdapter.getData().get(i).getId()).append(",");
                    }
                }

                if (students.toString().length() == 0) {
                    showToast("请选择删除的学生");
                } else {
                    syudentId = students.toString().substring(0, students.toString().length() - 1);
                    LogUtil.i("选择的学生" + syudentId);
                    needA = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("是否删除学生");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            studentDel(syudentId + "", needA);
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("否", null);
                    builder.create().show();

                }
                break;
        }
    }
}
