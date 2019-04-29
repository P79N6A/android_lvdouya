package com.hhkj.dyedu.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.CategoryAdapter02;
import com.hhkj.dyedu.adapter.CategoryAdapter03;
import com.hhkj.dyedu.adapter.CourseAdapterType10;
import com.hhkj.dyedu.bean.gson.category_category;
import com.hhkj.dyedu.bean.model.Theme;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadListActivity;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/6/28
 */
public class ChooseCourseActivity extends BaseTitleHeadListActivity {
    @BindView(R.id.rvleft)
    RecyclerView rvleft;
    @BindView(R.id.rvTop)
    RecyclerView rvTop;
    @BindView(R.id.layoutTop)
    RelativeLayout layoutTop;
    private CourseAdapterType10 courseAdapter05;
    private CategoryAdapter02 categoryAdapter02;
    private CategoryAdapter03 categoryAdapter03;
    private boolean isGetLeft = false;
    private int categoryId = -1;
    private int ageId = -1;
    private int type;//1 会员  2所有
    private String scheduleId;
    private int doPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        categoryAdapter03 = new CategoryAdapter03();
        categoryAdapter02 = new CategoryAdapter02();

        type = getIntent().getIntExtra("type", 1);
        scheduleId = getIntent().getStringExtra("scheduleId");
        courseAdapter05 = new CourseAdapterType10();

        if (type == 1) {
            setTitle("会员课程");
            layoutTop.setBackgroundColor(Color.parseColor("#61BDEC"));
        } else if (type == 2) {
            setTitle("选择课程");
            categoryAdapter03.setType(1);
            categoryAdapter02.setType(1);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(courseAdapter05);
//        recyclerView.setBackgroundColor(Color.parseColor("#000000"));

        rvTop.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        rvTop.setAdapter(categoryAdapter02);

        categoryAdapter02.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                categoryAdapter02.setChoose(position);
                ageId = categoryAdapter02.getData().get(position).getId();
                category_category();
            }
        });
        courseAdapter05.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.layoutContent03:
                        startActivity(new Intent(getContext(), ChooseCourseActivity.class).putExtra("type", 2));
                        break;
                    case R.id.layoutContent02:
                        Theme theme=(Theme) adapter.getData().get(position);
                        if(theme.getLock()!=0){
                            showToast("该主题已被锁定");
                            return;
                        }
                        if (type == 1) {
                            Intent intent = new Intent(getContext(), CourseThemeActivity.class);
                            intent.putExtra("Theme", (Theme) adapter.getData().get(position));
                            startActivity(intent);
                        } else if (type == 2) {
                            Intent intent = new Intent(getContext(), CourseThemeActivity.class);
                            intent.putExtra("Theme", (Theme) adapter.getData().get(position));
                            intent.putExtra("showTag", 3);//选择课程到课表中去
                            intent.putExtra("scheduleId", scheduleId);
                            startActivityForResult(intent, 100);
                        }
                        break;
                }
            }
        });
        rvleft.setLayoutManager(new LinearLayoutManager(getContext()));
        rvleft.setAdapter(categoryAdapter03);

        categoryAdapter03.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                categoryAdapter03.setChoose(position);
                ageId = -1;
                categoryId = categoryAdapter03.getData().get(position).getId();
                category_category();
            }
        });

        smartRefreshLayout.setPadding(0, 20, 0, 0);
        smartRefreshLayout.setBackgroundResource(R.drawable.bg_47);
//        smartRefreshLayout.setBackgroundColor(Color.parseColor("#ffffff"));

        smartRefreshLayout.setEnableLoadMore(false);

        smartRefreshLayout.setEnableRefresh(false);

        category_category();


    }

    private void category_category() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.category_category);//"获取主题列表"
        if (categoryId != -1) {
            httpRequest.add("categoryId", categoryId);
        }
        if (ageId != -1) {
            httpRequest.add("ageId", ageId);
        }
        if (type == 1) {

        } else if (type == 2) {
            httpRequest.add("scheduleId", scheduleId);
        }

        httpRequest.add("type", type);
        CallServer.getInstance().postRequest("获取主题列表", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        category_category info = gson.fromJson(response, category_category.class);
                        if (info.getCode() == 1) {

                            if (info.getData().getLevel().size() != 0) {
                                categoryAdapter03.replaceData(info.getData().getLevel());
                                categoryAdapter03.setChoose(0);
                                categoryId = info.getData().getLevel().get(0).getId();
                            }

                            if (info.getData().getLevel2().size() != 0) {
                                categoryAdapter02.replaceData(info.getData().getLevel2());
                                categoryAdapter02.setChoose(0);
                                ageId = info.getData().getLevel2().get(0).getId();
                            }

                            //上边
                            courseAdapter05.replaceData(info.getData().getInfo());
                            if (courseAdapter05.getData().size() == 0) {
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
                        finish();
                    }
                }, getContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 & resultCode == 10086) {
            setResult(10086);
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_choose_course;
    }


}


