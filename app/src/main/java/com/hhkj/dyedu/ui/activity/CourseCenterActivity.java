//package com.hhkj.dyedu.ui.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.google.gson.Gson;
//import com.hhkj.dyedu.AppUrl;
//import com.hhkj.dyedu.R;
//import com.hhkj.dyedu.adapter.CourseAdapter01;
//import com.hhkj.dyedu.adapter.CourseCenterAdapter;
//import com.hhkj.dyedu.bean.gson.index;
//import com.hhkj.dyedu.bean.model.BarInfo;
//import com.hhkj.dyedu.bean.model.CourseCenterBean;
//import com.hhkj.dyedu.bean.model.Featured;
//import com.hhkj.dyedu.bean.model.Theme;
//import com.hhkj.dyedu.http.CallServer;
//import com.hhkj.dyedu.http.HttpRequest;
//import com.hhkj.dyedu.http.HttpResponseListener;
//import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadListActivity;
//import com.hhkj.dyedu.utils.GlideImageLoader;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.youth.banner.Banner;
//import com.youth.banner.transformer.ZoomOutSlideTransformer;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by zangyi_shuai_ge on 2018/6/8
// */
//
//public class CourseCenterActivity extends BaseTitleHeadListActivity {
//
//
//    private CourseCenterAdapter courseCenterAdapter;
//    private List<CourseCenterBean> courseCenterBeanList;
//    private Banner banner;
//    private List<String> bannerImageList;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setTitle("课程中心");
//        setNoDataVisibility(View.GONE);
//        smartRefreshLayout.setEnableLoadMore(false);
//        courseCenterBeanList = new ArrayList<>();
//
//        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                index();
//            }
//        });
//
//
//        courseCenterAdapter = new CourseCenterAdapter(courseCenterBeanList);
//        initHead();
//
//        courseCenterAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
//                CourseCenterBean courseCenterBean = courseCenterAdapter.getData().get(position);
//                if (courseCenterBean.getItemType() == CourseCenterBean.ITEM_1
//                        | courseCenterBean.getItemType() == CourseCenterBean.ITEM_2
//                        | courseCenterBean.getItemType() == CourseCenterBean.ITEM_3
//                        ) {
//                    //课程 占布局1/4
//                    return 1;
//                } else if (courseCenterBean.getItemType() == CourseCenterBean.LINE_4
//                        |courseCenterBean.getItemType() == CourseCenterBean.BAR
//                        |courseCenterBean.getItemType() == CourseCenterBean.LINE_1
//                        |courseCenterBean.getItemType() == CourseCenterBean.LINE_4) {
//                    return 4;
//                }
//                return 1;
//            }
//        });
//
//
//        GridLayoutManager manager = new GridLayoutManager(this, 4);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(courseCenterAdapter);
//
//        courseCenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                jumpToActivity(ThemeListActivity.class);
//            }
//        });
//
//        smartRefreshLayout.autoRefresh();
//
//
////        courseCenterAdapter.re
//    }
//
//    private void index() {
////        showLoading();
//        HttpRequest httpRequest = new HttpRequest(AppUrl.index);//"课程中心"
////        httpRequest.add("---", ---);
//        CallServer.getInstance().postRequest("课程中心", httpRequest,
//                new HttpResponseListener() {
//                    @Override
//                    public void onSucceed(String response, Gson gson) {
////                        closeLoading();
//                        index info = gson.fromJson(response, index.class);
//                        if (info.getCode()!=1) {
//                            showToast(info.getMsg());
//                        } else {
//                            if(getContext()==null){
//                                return;
//                            }
//                            smartRefreshLayout.finishRefresh(200);
//                            //图片轮播图
//                            bannerImageList.clear();
//                            int bannerSize=info.getData().getBanner().size();
//                            for (int i = 0; i <bannerSize ; i++) {
//                                bannerImageList.add(info.getData().getBanner().get(i).getImage());
//                            }
//                            banner.setImages(bannerImageList);
//                            banner.start();
//
//                            //课程推荐
//                            courseAdapter01.replaceData(info.getData().getFeatured());
//                            courseCenterBeanList.clear();
//                            //主题课程推荐
//                            int themeSize=info.getData().getTheme().size();
//                            for (int i = 0; i <themeSize ; i++) {
//                                CourseCenterBean courseCenterBean = new CourseCenterBean();
//                                courseCenterBean.setItemType(CourseCenterBean.BAR);
//                                courseCenterBean.setBarInfo(new BarInfo(info.getData().getTheme().get(i).getId(),info.getData().getTheme().get(i).getTitle(),BarInfo.TYPE_1));
//                                courseCenterBeanList.add(courseCenterBean);
//                                int mThemeSize=info.getData().getTheme().get(i).getInfo().size();
//                                for (int j = 0; j <mThemeSize ; j++) {
//                                    CourseCenterBean courseCenterBean1 = new CourseCenterBean();
//                                    courseCenterBean1.setItemType(CourseCenterBean.ITEM_2);
//                                    courseCenterBean1.setTheme(info.getData().getTheme().get(i).getInfo().get(j));
//                                    courseCenterBeanList.add(courseCenterBean1);
//                                }
//                                //分割线
//                                for (int j = 0; j <1; j++) {
//                                    CourseCenterBean courseCenterBean2 = new CourseCenterBean();
//                                    courseCenterBean2.setItemType(CourseCenterBean.LINE_1);
//                                    courseCenterBeanList.add(courseCenterBean2);
//                                }
//                            }
//                            //课程分类推荐
//                            //分割线
//                            for (int i = 0; i <1; i++) {
//                                CourseCenterBean courseCenterBean = new CourseCenterBean();
//                                courseCenterBean.setItemType(CourseCenterBean.LINE_4);
//                                courseCenterBeanList.add(courseCenterBean);
//                            }
//                            //标题
//                            for (int i = 0; i <1; i++) {
//                                CourseCenterBean courseCenterBean = new CourseCenterBean();
//                                courseCenterBean.setItemType(CourseCenterBean.BAR);
//                                courseCenterBean.setBarInfo(new BarInfo(-1,"",BarInfo.TYPE_2));
//                                courseCenterBeanList.add(courseCenterBean);
//                            }
//                            int categorySize=info.getData().getCategory().size();
//                            for (int i = 0; i <categorySize; i++) {
//                                CourseCenterBean courseCenterBean = new CourseCenterBean();
//                                courseCenterBean.setItemType(CourseCenterBean.ITEM_3);
//                                courseCenterBean.setCategory(info.getData().getCategory().get(i));
//                                courseCenterBeanList.add(courseCenterBean);
//                            }
//
//                            courseCenterAdapter.replaceData(courseCenterBeanList);
//
//                        }
//                    }
//                    @Override
//                    public void onFailed(Exception exception) {
////                        closeLoading();
//                        smartRefreshLayout.finishRefresh(200);
//                        showToast(getString(R.string.toast_server_error));
//                    }
//                }, getContext());
//    }
//
//
//    private RecyclerView rvCourseRecommend;
//    private List<Featured>courseRecommendList;
//    private CourseAdapter01 courseAdapter01;
//
//    private void initHead() {
//        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_course_center, null);
//        //轮播
//        banner = headView.findViewById(R.id.banner);
//        bannerImageList = new ArrayList<>();
////        bannerImageList.add(GlobalVariable.TEST_IMAGE_URL1);
////        bannerImageList.add(GlobalVariable.TEST_IMAGE_URL2);
////        bannerImageList.add(GlobalVariable.TEST_IMAGE_URL3);
////        bannerImageList.add(GlobalVariable.TEST_IMAGE_URL4);
//        //设置图片集合
//        banner.setImages(bannerImageList);
//        banner.setImageLoader(new GlideImageLoader());
//        banner.setBannerAnimation(ZoomOutSlideTransformer.class);
//        //banner设置方法全部调用完毕时最后调用
//        banner.start();
//
//
//        rvCourseRecommend=headView.findViewById(R.id.rvCourseRecommend);
//
//        rvCourseRecommend.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//
//        courseAdapter01=new CourseAdapter01();
//
//        rvCourseRecommend.setAdapter(courseAdapter01);
//
//
//        courseAdapter01.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(getContext(), CourseThemeActivity.class);
//                Theme theme=new Theme();
//                theme.setId(courseAdapter01.getData().get(position).getTheme_id());
//                intent.putExtra("Theme", theme);
//                startActivity(intent);
//            }
//        });
//
//        courseRecommendList=new ArrayList<>();
//
//        courseAdapter01.replaceData(courseRecommendList);
//
//        courseCenterAdapter.addHeaderView(headView);
//
//    }
//
//    @Override
//    public int setLayoutId() {
//        return R.layout.base_activity_list_title_head;
//    }
//
//}
