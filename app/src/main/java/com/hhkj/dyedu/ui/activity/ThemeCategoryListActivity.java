package com.hhkj.dyedu.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.ThemeCategoryAdapter;
import com.hhkj.dyedu.bean.gson.center;
import com.hhkj.dyedu.bean.gson.moreCategory;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadActivity;
import com.hhkj.dyedu.utils.GlideImageLoader;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/6/12
 * 课程中心
 */

public class ThemeCategoryListActivity extends BaseTitleHeadActivity {
    
    @BindView(R.id.layoutTop)
    RelativeLayout layoutTop;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv03)
    ImageView iv03;
    @BindView(R.id.iv04)
    ImageView iv04;
    @BindView(R.id.iv05)
    ImageView iv05;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.iv06)
    ImageView iv06;
    private ThemeCategoryAdapter categoryAdapter;
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_theme_category_list_center;
    }
    
    private List<String> bannerImageList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("课程中心");
        layoutTop.setBackgroundColor(Color.parseColor("#FD7B63"));
        categoryAdapter = new ThemeCategoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(categoryAdapter);
        
        categoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), ThemeCategoryCenter2Activity.class);
                intent.putExtra("ThemeCategory", categoryAdapter.getItem(position));
                startActivity(intent);
            }
        });
        bannerImageList = new ArrayList<>();
        //设置图片集合
        banner.setImages(bannerImageList);
        banner.setImageLoader(new GlideImageLoader());
//        banner.setBannerAnimation(ZoomOutSlideTransformer.class);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (center != null) {
                    startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", center.getData().getBanner().get(position).getLink()).putExtra("title", ""));
                }
            }
        });
        moreCategory();
        center();
    }
    
    private center center;
    
    /**
     * 轮播 + 广告图片
     */
    private void center() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.center);//"更多课程分类"
        CallServer.getInstance().postRequest("轮播 + 广告图片", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        center info = gson.fromJson(response, center.class);
                        center = info;
                        if (info.getCode() == 1) {
                            //轮播
                            bannerImageList.clear();
                            bannerImageList.clear();
                            int bannerSize = info.getData().getBanner().size();
                            for (int i = 0; i < bannerSize; i++) {
                                bannerImageList.add(info.getData().getBanner().get(i).getImage());
                            }
                            banner.setImages(bannerImageList);
                            banner.start();
                            
                            //一圈广告图
//                            ImageLoaderUtils.setCourseImage(info.getData().get(0).getImage(),iv01);
                            if (info.getData().getOther().size() == 5) {
                                ImageLoaderUtils.setCourseImage(info.getData().getOther().get(0).getImage(), iv02);
                                ImageLoaderUtils.setCourseImage(info.getData().getOther().get(1).getImage(), iv03);
                                ImageLoaderUtils.setCourseImage(info.getData().getOther().get(2).getImage(), iv04);
                                ImageLoaderUtils.setCourseImage(info.getData().getOther().get(3).getImage(), iv05);
                                ImageLoaderUtils.setCourseImage(info.getData().getOther().get(4).getImage(), iv06);
                            }
                            
                            iv02.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (center != null) {
                                        startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", center.getData().getOther().get(0).getLink()).putExtra("title", ""));
                                    }
                                }
                            });
                            iv03.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (center != null) {
                                        startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", center.getData().getOther().get(1).getLink()).putExtra("title", ""));
                                    }
                                }
                            });
                            iv04.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (center != null) {
                                        startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", center.getData().getOther().get(2).getLink()).putExtra("title", ""));
                                    }
                                }
                            });
                            iv05.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (center != null) {
                                        startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", center.getData().getOther().get(3).getLink()).putExtra("title", ""));
                                    }
                                }
                            });
                            iv06.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (center != null) {
                                        startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", center.getData().getOther().get(4).getLink()).putExtra("title", ""));
                                    }
                                }
                            });
                            
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
    
    
    /**
     * 左侧课程分类列表
     */
    private void moreCategory() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.moreCategory);//"更多课程分类"
        CallServer.getInstance().postRequest("课程分类列表", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        moreCategory info = gson.fromJson(response, moreCategory.class);
                        if (info.getCode() == 1) {
                            categoryAdapter.replaceData(info.getData());
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
}
