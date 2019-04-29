package com.hhkj.dyedu.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BannerGson;
import com.hhkj.dyedu.bean.model.ThemeCategory;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadActivity;
import com.hhkj.dyedu.ui.fragment.RecommendCourseFragment;
import com.hhkj.dyedu.ui.fragment.RecommendThemeFragment;
import com.hhkj.dyedu.utils.GlideImageLoader;
import com.hhkj.dyedu.view.PageIndicator;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/9/5
 *
 * @author zangyi 767450430
 * 课程、主题 推荐
 */
public class RecommendActivity extends BaseTitleHeadActivity {


    @BindView(R.id.layoutTop)
    RelativeLayout layoutTop;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.indicator)
    PageIndicator indicator;
    @BindView(R.id.content_layout)
    FrameLayout contentLayout;
    @BindView(R.id.tabTheme)
    RoundTextView tabTheme;
    @BindView(R.id.tabCourse)
    RoundTextView tabCourse;
    private FragmentManager fragmentManager;
    private List<String> bannerImageList;
    private RecommendCourseFragment recommendCourseFragment;
    private RecommendThemeFragment recommendThemeFragment;
    private RoundViewDelegate roundViewDelegate;
    private RoundViewDelegate roundViewDelegate2;
    private ThemeCategory themeCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getIntExtra("type", 1) == 1) {
            layoutTop.setBackgroundColor(Color.parseColor("#FD7B63"));
            setTitle(getString(R.string.title_recommend_01));
        } else {
            layoutTop.setBackgroundColor(Color.parseColor("#5DC0FB"));
            setTitle(getString(R.string.title_recommend_02));
        }

        themeCategory= (ThemeCategory) getIntent().getSerializableExtra("ThemeCategory");

        roundViewDelegate = tabTheme.getDelegate();
        roundViewDelegate2 = tabCourse.getDelegate();

        fragmentManager = getSupportFragmentManager();
        recommendCourseFragment = new RecommendCourseFragment();
        recommendCourseFragment.setActivity(this);
        recommendCourseFragment.setThemeCategory(themeCategory);

        recommendThemeFragment=new RecommendThemeFragment();
        recommendThemeFragment.setThemeCategory(themeCategory);
        recommendThemeFragment.setActivity(this);

        bannerImageList = new ArrayList<>();
        //设置图片集合
        banner.setImages(bannerImageList);
        banner.setImageLoader(new GlideImageLoader());
//        banner.setBannerAnimation(ZoomOutSlideTransformer.class);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (bannerImageList.size() == 0) {
                    return;
                }
                indicator.updateIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner.setBannerStyle(0);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);

        index();

        chooseTab(0);
    }
    private void chooseTab(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        initTab();
        switch (i) {
            case 0:
                roundViewDelegate.setBackgroundColor(Color.parseColor("#F3F3F3"));
                tabTheme.setTextColor(Color.parseColor("#949494"));
                transaction.replace(R.id.content_layout, recommendThemeFragment);
                break;
            case 1:
                roundViewDelegate2.setBackgroundColor(Color.parseColor("#F3F3F3"));
                tabCourse.setTextColor(Color.parseColor("#949494"));
                transaction.replace(R.id.content_layout, recommendCourseFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
    }



    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_recommend;
    }

    private void index() {
        HttpRequest httpRequest = new HttpRequest(AppUrl.banner);//"课程中心"
        CallServer.getInstance().postRequest("Banner", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        BannerGson info = gson.fromJson(response, BannerGson.class);
                        if (getContext() == null) {
                            return;
                        }
                        if (info.getCode() == 1) {
                            bannerImageList.clear();
                            int bannerSize = info.getData().size();
                            for (int i = 0; i < bannerSize; i++) {
                                bannerImageList.add(info.getData().get(i).getImage());
                            }
                            banner.setImages(bannerImageList);
                            if (getContext() == null) {
                                return;
                            }

                            banner.start();
                            indicator.addIndicator(bannerImageList.size());
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                    }
                }, getContext());
    }

    @OnClick({R.id.tabTheme, R.id.tabCourse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tabTheme:
                chooseTab(0);
                break;
            case R.id.tabCourse:
                chooseTab(1);
                break;
        }
    }

    private void initTab() {
        roundViewDelegate.setBackgroundColor(Color.parseColor("#D1D1D1"));
        roundViewDelegate2.setBackgroundColor(Color.parseColor("#D1D1D1"));

        tabTheme.setTextColor(Color.parseColor("#fefefe"));
        tabCourse.setTextColor(Color.parseColor("#fefefe"));
    }
}
