package com.hhkj.dyedu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.ScheduleAdapter;
import com.hhkj.dyedu.bean.gson.schedule_create;
import com.hhkj.dyedu.bean.gson.show;
import com.hhkj.dyedu.bean.model.ScheduleUnit;
import com.hhkj.dyedu.bean.model.ScheduleUnit2;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadActivity;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.joooonho.SelectableRoundedImageView;
import com.hhkj.dyedu.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/6/21
 * 课程表
 */

public class ScheduleActivity extends BaseTitleHeadActivity {

    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.avatar)
    SelectableRoundedImageView avatar;
    @BindView(R.id.nickname)
    TextView nickname;

    private ScheduleAdapter scheduleAdapter;
    private int teacherId;

    @Override
    public int setLayoutId() {
        return R.layout.activity_schedule;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("创建课表");
        teacherId = getIntent().getIntExtra("teacherId", -1);

        scheduleAdapter = new ScheduleAdapter();
        scheduleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {

                    case R.id.layoutAdd:
                        schedule_create(scheduleAdapter.getData().get(position).getType(), scheduleAdapter.getData().get(position).getWeek(),scheduleAdapter.getData().get(position));
                        break;
                    case R.id.tv01:
                        startActivityForResult(new Intent(getContext(), ClassDetailsActivity.class)
                                .putExtra("scheduleId", scheduleAdapter.getData().get(position).getMon().get(0).getId() + "")
                                .putExtra("scheduleUnit", scheduleAdapter.getData().get(position)), 100);
                        break;
                    case R.id.tv02:
                        startActivityForResult(new Intent(getContext(), ClassDetailsActivity.class)
                                        .putExtra("scheduleId", scheduleAdapter.getData().get(position).getMon().get(1).getId() + "")
                                        .putExtra("scheduleUnit", scheduleAdapter.getData().get(position))
                                , 100);
                        break;
                    case R.id.tv03:
                        startActivityForResult(new Intent(getContext(), ClassDetailsActivity.class)
                                        .putExtra("scheduleId", scheduleAdapter.getData().get(position).getMon().get(2).getId() + "")
                                        .putExtra("scheduleUnit", scheduleAdapter.getData().get(position))
                                , 100);
                        break;
                    case R.id.tv04:
                        startActivityForResult(new Intent(getContext(), ClassDetailsActivity.class)
                                        .putExtra("scheduleId", scheduleAdapter.getData().get(position).getMon().get(3).getId() + "")
                                        .putExtra("scheduleUnit", scheduleAdapter.getData().get(position))
                                , 100);
                        break;
                }


            }
        });


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));//7列

        recyclerView.setAdapter(scheduleAdapter);

        tv01.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.i("高度" + tv01.getHeight() + "高度2" + tv01.getMeasuredHeight() + "==" + ScreenUtils.getScreenWidth() + "==" + ScreenUtils.getScreenHeight());
                scheduleAdapter.setItemHeight(tv01.getHeight());
            }
        });

        show();
    }

    private void schedule_create(String type, String week, final ScheduleUnit scheduleUnit) {
        CallServer.getInstance().cancelBySign("schedule_create");
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.schedule_create);//"创建课"
        httpRequest.add("type", type);
        httpRequest.add("week", week);
        httpRequest.add("teacherId", teacherId);
        httpRequest.setTag("schedule_create");
        CallServer.getInstance().postRequest("创建课", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        schedule_create info = gson.fromJson(response, schedule_create.class);
                        if (info.getCode() == 1) {
                            startActivityForResult(new Intent(getContext(), ClassDetailsActivity.class)
                                            .putExtra("scheduleId", info.getData().getId())
                                            .putExtra("timeType", info.getData().getType())
                                            .putExtra("scheduleUnit", scheduleUnit)
                                    , 100);
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

    private void show() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.show);//"获取课表"
        httpRequest.add("teacherId", teacherId);
        CallServer.getInstance().postRequest("获取课表", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        show info = gson.fromJson(response, show.class);
                        if (info.getCode() == 1) {

                            List<ScheduleUnit> scheduleUnits = new ArrayList<>();

                            //获取早上
                            ScheduleUnit2 scheduleUnit2 = info.getData().getMorning();
                            //填充早上的7条数据
                            scheduleUnits.add(new ScheduleUnit("1", "1", scheduleUnit2.getMon()));
                            scheduleUnits.add(new ScheduleUnit("2", "1", scheduleUnit2.getTues()));
                            scheduleUnits.add(new ScheduleUnit("3", "1", scheduleUnit2.getWed()));
                            scheduleUnits.add(new ScheduleUnit("4", "1", scheduleUnit2.getThur()));
                            scheduleUnits.add(new ScheduleUnit("5", "1", scheduleUnit2.getFri()));
                            scheduleUnits.add(new ScheduleUnit("6", "1", scheduleUnit2.getSat()));
                            scheduleUnits.add(new ScheduleUnit("7", "1", scheduleUnit2.getSun()));
                            //处理中午
                            ScheduleUnit2 scheduleUnit21 = info.getData().getAfternoon();
                            scheduleUnits.add(new ScheduleUnit("1", "2", scheduleUnit21.getMon()));
                            scheduleUnits.add(new ScheduleUnit("2", "2", scheduleUnit21.getTues()));
                            scheduleUnits.add(new ScheduleUnit("3", "2", scheduleUnit21.getWed()));
                            scheduleUnits.add(new ScheduleUnit("4", "2", scheduleUnit21.getThur()));
                            scheduleUnits.add(new ScheduleUnit("5", "2", scheduleUnit21.getFri()));
                            scheduleUnits.add(new ScheduleUnit("6", "2", scheduleUnit21.getSat()));
                            scheduleUnits.add(new ScheduleUnit("7", "2", scheduleUnit21.getSun()));
                            //处理晚上
                            ScheduleUnit2 scheduleUnit22 = info.getData().getEvening();
                            scheduleUnits.add(new ScheduleUnit("1", "3", scheduleUnit22.getMon()));
                            scheduleUnits.add(new ScheduleUnit("2", "3", scheduleUnit22.getTues()));
                            scheduleUnits.add(new ScheduleUnit("3", "3", scheduleUnit22.getWed()));
                            scheduleUnits.add(new ScheduleUnit("4", "3", scheduleUnit22.getThur()));
                            scheduleUnits.add(new ScheduleUnit("5", "3", scheduleUnit22.getFri()));
                            scheduleUnits.add(new ScheduleUnit("6", "3", scheduleUnit22.getSat()));
                            scheduleUnits.add(new ScheduleUnit("7", "3", scheduleUnit22.getSun()));
                            scheduleAdapter.replaceData(scheduleUnits);

                            nickname.setText(info.getData().getNickname());
                            ImageLoaderUtils.setHeadImage(info.getData().getAvatar(), avatar);

                        } else {
                            showToast(info.getMsg());
                            finish();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 10086) {
            show();
        }
    }
}
