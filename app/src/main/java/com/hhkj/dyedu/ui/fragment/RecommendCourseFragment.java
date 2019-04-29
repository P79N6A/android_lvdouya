package com.hhkj.dyedu.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.RecommendCourseAdapter;
import com.hhkj.dyedu.bean.gson.check;
import com.hhkj.dyedu.bean.gson.featured;
import com.hhkj.dyedu.bean.model.CourseBean;
import com.hhkj.dyedu.bean.model.ThemeCategory;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.MainActivity;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.activity.WebViewActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/9/17
 *
 * @author zangyi 767450430
 * 推荐课程
 */
public class RecommendCourseFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecommendCourseAdapter recommendCourseAdapter;
    private ThemeCategory themeCategory;
    
    @Override
    public int setLayoutId() {
        return R.layout.fragment_recommend_course;
    }
    
    @Override
    public void initView() {
        
        if (!isInitView) {
            isInitView = true;
            recommendCourseAdapter = new RecommendCourseAdapter();
            recommendCourseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    
                    CourseBean courseBean = recommendCourseAdapter.getItem(position);
                    assert courseBean != null;
                    if (courseBean.getType() == CourseBean.VIP_COURSE) {
                        //
                        check(courseBean.getId());
                    } else {
                        String url = courseBean.getPreview();
                        if (url.trim().equals("")) {
                            activity.showToast("该课程暂无预览");
                            return;
                        }
                        startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", url));
                    }
//                    Intent intent = new Intent(getContext(), CourseThemeActivity.class);
//                    Theme theme = new Theme();
//                    theme.setId(recommendCourseAdapter.getData().get(position).getTheme_id());
//                    intent.putExtra("Theme", theme);
//                    startActivity(intent);
                }
            });
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setBackgroundResource(R.drawable.bg_54);
            recyclerView.setPadding(0, 41, 0, 0);
            
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            layoutParams.setMargins(39, 0, 39, 0);
            recyclerView.requestLayout();
            
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
            recyclerView.setAdapter(recommendCourseAdapter);
        }
        
        if (!isGetData) {
            featured();
        }
    }
    
    private void check(int courseId) {
        showLoading();
        //""
        HttpRequest httpRequest = new HttpRequest(AppUrl.check);
        httpRequest.add("courseId", courseId);
        CallServer.getInstance().postRequest("", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        check info = gson.fromJson(response, check.class);
                        if (info.getCode() == CallServer.HTTP_SUCCESS_CODE) {
                            startActivity(
                                    new Intent(getContext(), MainActivity.class)
                                            .putExtra("scheduleId", "-2")
                                            .putExtra("courseId", info.getData()));
                        } else {
                            
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            
                            builder.setTitle("提示");
                            
                            builder.setMessage("该课程只有会员才能观看,是否购买会员");
                            
                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivityForResult(new Intent(getContext(), PersonalCenterActivity.class).putExtra("doType", 2), 100);
                                    
                                }
                            });
                            builder.setNegativeButton("否", null);
                            builder.create().show();
                        }
                    }
                    
                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        activity.showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
        
    }
    
    private void featured() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.featured);//"课程中心"、
        httpRequest.add("categoryId", themeCategory.getId());
        CallServer.getInstance().postRequest("featured", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        featured info = gson.fromJson(response, featured.class);
                        if (info.getCode() == 1) {
                            isGetData = true;
                            recommendCourseAdapter.replaceData(info.getData());
                        } else {
                            activity.showToast(info.getMsg());
                        }
                    }
                    
                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                    }
                }, getContext());
    }
    
    public void setThemeCategory(ThemeCategory themeCategory) {
        this.themeCategory = themeCategory;
    }
}
