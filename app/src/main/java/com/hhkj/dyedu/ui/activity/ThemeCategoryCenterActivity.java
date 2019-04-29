package com.hhkj.dyedu.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BannerGson;
import com.hhkj.dyedu.bean.model.ThemeCategory;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadActivity;
import com.hhkj.dyedu.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/8
 * 主题分类中心
 */

public class ThemeCategoryCenterActivity extends BaseTitleHeadActivity {
    
    
    @BindView(R.id.layoutTop)
    RelativeLayout layoutTop;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv01)
    ImageView iv01;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.iv03)
    ImageView iv03;
    private List<String> bannerImageList;
    private ThemeCategory themeCategory;
    
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutTop.setBackgroundColor(Color.parseColor("#FD7B63"));
        
        themeCategory = (ThemeCategory) getIntent().getSerializableExtra("ThemeCategory");
        setTitle(themeCategory.getName());
        
        
        bannerImageList = new ArrayList<>();
        //设置图片集合
        banner.setImages(bannerImageList);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        
        
        index();
    }
    
    /**
     * 获取轮播
     */
    private void index() {
        HttpRequest httpRequest = new HttpRequest(AppUrl.banner);
        CallServer.getInstance().postRequest("Banner", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        BannerGson info = gson.fromJson(response, BannerGson.class);
                        if (info.getCode() == 1) {
                            bannerImageList.clear();
                            int bannerSize = info.getData().size();
                            for (int i = 0; i < bannerSize; i++) {
                                bannerImageList.add(info.getData().get(i).getImage());
                            }
                            banner.setImages(bannerImageList);
                            banner.start();
                        }
                    }
                    
                    @Override
                    public void onFailed(Exception exception) {
                    }
                }, getContext());
    }
    
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_theme_category_center;
    }
    
    @OnClick({R.id.iv01, R.id.iv02, R.id.iv03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv01:
                //推荐课程
                startActivity(new Intent(getContext(), RecommendActivity.class)
                        .putExtra("type", 1)
                        .putExtra("ThemeCategory", themeCategory));
                break;
            case R.id.iv02:
                //年龄梯度
                Intent intent = new Intent(getContext(), ThemeListActivity.class);
                intent.putExtra("ThemeCategory", themeCategory);
                startActivity(intent);
                break;
            case R.id.iv03:
                //精品课程
                startActivity(new Intent(getContext(), RecommendActivity.class)
                        .putExtra("type", 2)
                        .putExtra("ThemeCategory", themeCategory));
                break;
        }
    }
}
