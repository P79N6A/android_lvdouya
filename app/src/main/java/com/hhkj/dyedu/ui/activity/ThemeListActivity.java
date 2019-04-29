package com.hhkj.dyedu.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.CategoryLeftAdapter;
import com.hhkj.dyedu.adapter.ThemeAdapter;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.category;
import com.hhkj.dyedu.bean.model.Theme;
import com.hhkj.dyedu.bean.model.ThemeCategory;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadListActivity;
import com.hhkj.dyedu.view.ConfigureDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/6/8
 *
 * @author zangyi 767450430
 * 主题列表
 */

public class ThemeListActivity extends BaseTitleHeadListActivity {

    @BindView(R.id.rvLeft)
    RecyclerView rvLeft;
    @BindView(R.id.ivIndicator)
    ImageView ivIndicator;

    private String name;
    private ThemeAdapter themeAdapter;
    private CategoryLeftAdapter categoryLeftAdapter;
    private boolean isGetLevel = false;
    private int levelId = -1;

    private float oldY = 0;


    private ThemeCategory themeCategory;
    private GridLayoutManager gridLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeCategory = (ThemeCategory) getIntent().getSerializableExtra("ThemeCategory");
        setTitle(themeCategory.getName());

        recyclerView.setPadding(30, 35, 30, 0);
        smartRefreshLayout.setBackgroundResource(R.drawable.bg_47);


        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) smartRefreshLayout.getLayoutParams();
        layoutParams.setMargins(4, 28, 28, 0);
        smartRefreshLayout.setLayoutParams(layoutParams);
        smartRefreshLayout.setEnableLoadMore(false);
        setNoDataVisibility(View.GONE);

        themeAdapter = new ThemeAdapter();
        themeAdapter.setName(themeCategory.getName());
        themeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        themeAdapter.isFirstOnly(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(themeAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                category();
            }
        });
        themeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Theme theme=(Theme) adapter.getData().get(position);
                if(theme.getLock()==0){
                    Intent intent = new Intent(getContext(), CourseThemeActivity.class);
                    intent.putExtra("Theme", theme);
                    startActivity(intent);
                }else {
                    showToast("该主题已被锁定");
                }
                

            
//                jumpToActivity(CourseThemeActivity.class);
            }
        });


        themeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.iv01:
                        ConfigureDialog.Builder builder = new ConfigureDialog.Builder(getContext());
                        builder.setUrl(themeAdapter.getData().get(position).getConfig());
                        builder.setText(themeAdapter.getData().get(position).getConfig_title());
                        builder.create().show();


                        break;
                    case R.id.iv02:
                        //加入购物车
                        shopcarAdd(themeAdapter.getData().get(position).getId());
                        break;
                }

            }
        });

        categoryLeftAdapter = new CategoryLeftAdapter();
        categoryLeftAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                smartRefreshLayout.finishLoadMore();
                smartRefreshLayout.finishRefresh();
                CallServer.getInstance().cancelBySign(TAG);

                categoryLeftAdapter.setChoose(position);
                levelId = categoryLeftAdapter.getData().get(position).getId();
//                smartRefreshLayout.autoRefresh();
                showLoading();
                category();

                startLeftAnimation(view);
            }
        });

        rvLeft.setLayoutManager(new LinearLayoutManager(getContext()));

        rvLeft.setAdapter(categoryLeftAdapter);


//        initLeft();
//        chooseLeft(1);
        smartRefreshLayout.autoRefresh();
    }

    private void category() {
//        showLoading();
        CallServer.getInstance().cancelBySign(TAG);
        HttpRequest httpRequest = new HttpRequest(AppUrl.category);//"获取分类中的主题"
        httpRequest.add("categoryId", themeCategory.getId());
        if (levelId != -1) {
            httpRequest.add("ageId", levelId);
        }
        httpRequest.setCancelSign(TAG);
        CallServer.getInstance().postRequest("获取分类中的主题", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        smartRefreshLayout.finishRefresh();
                        category info = gson.fromJson(response, category.class);
                        if (info.getCode() == 1) {
                            themeAdapter.replaceData(info.getData().getInfo());

                            if (!isGetLevel) {
                                isGetLevel = true;
                                categoryLeftAdapter.replaceData(info.getData().getLevel2());
                                categoryLeftAdapter.setChoose(0);
                                //
                                recyclerView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivIndicator.setY(27);
                                        ivIndicator.setVisibility(View.VISIBLE);
//                                        View child = gridLayoutManager.findViewByPosition(0);
//                                        startLeftAnimation(child);
                                    }
                                });
                            }
                        } else {
                            showToast(info.getMsg());
                        }
                        if (themeAdapter.getData().size() != 0) {
                            setNoDataVisibility(View.GONE);
                        } else {
                            setNoDataVisibility(View.VISIBLE);
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

    private void shopcarAdd(int id) {

        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.shopcarAdd);//"加入购物车"
        httpRequest.add("type", 1);
        httpRequest.add("curriculumId", id);
        httpRequest.add("num", "1");
        CallServer.getInstance().postRequest("加入购物车", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        showToast(info.getMsg());
                        if (info.getCode() == 1) {
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

    private void startLeftAnimation(View view) {
//        LogUtil.i("点击坐标"+view.getX()+"  "+view.getY());
//                ivIndicator.setX(view.getX());
//                ivIndicator.setY(view.getY());
        //清除旧的动画
        ivIndicator.clearAnimation();
        float newY = view.getY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivIndicator, "TranslationY", oldY, newY);
        animator.setDuration(500);
//                animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                ivIndicator.setY(v);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                categoryLeftAdapter.setInAnimation(true);
                categoryLeftAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                categoryLeftAdapter.setInAnimation(false);
                categoryLeftAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        oldY = view.getY();
        animator.start();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_theme_list;
    }

}
