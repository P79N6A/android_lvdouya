package com.hhkj.dyedu.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.CourseAdapter05;
import com.hhkj.dyedu.bean.gson.course;
import com.hhkj.dyedu.bean.model.Theme;
import com.hhkj.dyedu.event.MoneyVipUpdateEvent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.ChooseCourseActivity;
import com.hhkj.dyedu.ui.activity.CourseThemeActivity;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class MyCourseFragment extends BaseFragment {

    @BindView(R.id.tvVipS)
    TextView tvVipS;
    @BindView(R.id.tvVip)
    TextView tvVip;
    @BindView(R.id.layoutVip)
    LinearLayout layoutVip;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.layoutNoData)
    LinearLayout layoutNoData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private PersonalCenterActivity personalCenterActivity;

    private CourseAdapter05 courseAdapter05;
    private boolean isInit = false;
    private boolean isGet = false;

    public void setPersonalCenterActivity(PersonalCenterActivity personalCenterActivity) {
        this.personalCenterActivity = personalCenterActivity;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_my_course;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        MoneyVipUpdateEvent event = new MoneyVipUpdateEvent();
        if (event.getVipTime().equals("0")) {
            layoutVip.setVisibility(View.GONE);
        } else {
            layoutVip.setVisibility(View.VISIBLE);
            tvVipS.setText(event.getVipTimeS() + ",现享有全部会员课程免费权利！");
        }

        if (!isInit) {
            isInit = true;
            courseAdapter05 = new CourseAdapter05();
            courseAdapter05.setP(true);
            courseAdapter05.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.layoutContent02:
//                            Intent intent = new Intent(getContext(), CourseThemeActivity.class);
//                            intent.putExtra("Theme", (Theme) adapter.getData().get(position));
//                            startActivity(intent);

                            Intent intent = new Intent(getContext(), CourseThemeActivity.class);
                            intent.putExtra("Theme", (Theme) adapter.getData().get(position));
                            intent.putExtra("scheduleId", "-2");
                            intent.putExtra("showTag", 4);
                            startActivityForResult(intent, 100);
                            break;
                    }
                }
            });
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
            recyclerView.setAdapter(courseAdapter05);

            refreshLayout.setPadding(0, 34, 0, 0);
            refreshLayout.setBackgroundResource(R.drawable.bg_40);


            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    course();
                }
            });

            refreshLayout.setEnableLoadMore(false);
        }
        if (!isGet) {
            course();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void course() {
        personalCenterActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.course);
        CallServer.getInstance().postRequest("我的课程", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        personalCenterActivity.closeLoading();
                        if(refreshLayout==null){
                            return;
                        }
                        
                        refreshLayout.finishRefresh();
                        course info = gson.fromJson(response, course.class);
                        if (info.getCode() == 1) {
                            isGet = true;
                            courseAdapter05.replaceData(info.getData());
                        } else {
                            personalCenterActivity.showToast(info.getMsg());
                        }

                        if (courseAdapter05.getData().size() == 0) {
                            layoutNoData.setVisibility(View.VISIBLE);
                        } else {
                            layoutNoData.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        personalCenterActivity.closeLoading();
                        refreshLayout.finishRefresh();
                        personalCenterActivity.showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MoneyVipUpdateEvent event) {
        if (event.getVipTime().equals("0")) {
            layoutVip.setVisibility(View.GONE);
        } else {
            layoutVip.setVisibility(View.VISIBLE);
            tvVipS.setText(event.getVipTimeS() + ",现享有全部会员课程免费权利！");
        }
    }

    @OnClick(R.id.tvVip)
    public void onViewClicked() {
        personalCenterActivity.jumpToActivity(ChooseCourseActivity.class);
    }
}
