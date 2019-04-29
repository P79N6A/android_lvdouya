package com.hhkj.dyedu.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.ThemeRecommendAdapter;
import com.hhkj.dyedu.bean.gson.RecommendTheme;
import com.hhkj.dyedu.bean.model.Theme;
import com.hhkj.dyedu.bean.model.ThemeCategory;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.CourseThemeActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;
import com.zuoni.common.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/9/17
 *
 * @author zangyi 767450430
 * 推荐课程
 */
public class RecommendThemeFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    
    private ThemeCategory themeCategory;
    private ThemeRecommendAdapter themeRecommendAdapter;
    
    public void setThemeCategory(ThemeCategory themeCategory) {
        this.themeCategory = themeCategory;
    }
    
    @Override
    public int setLayoutId() {
        return R.layout.fragment_recommend_course;
    }
    
    @Override
    public void initView() {
        
        if (!isInitView) {
            isInitView = true;
            themeRecommendAdapter = new ThemeRecommendAdapter();
            themeRecommendAdapter.setName(themeCategory.getName());
            themeRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Theme theme = (Theme) adapter.getData().get(position);
                    if (theme.getLock() == 0) {
                        Intent intent = new Intent(getContext(), CourseThemeActivity.class);
                        intent.putExtra("Theme", theme);
                        startActivity(intent);
                    } else {
                        ToastUtils.showToast(getContext(), "该主题已被锁定");
                    }
                    
                }
            });
            
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setBackgroundResource(R.drawable.bg_54);
            recyclerView.setPadding(0, 41, 0, 0);
            
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            layoutParams.setMargins(39, 0, 39, 0);
            recyclerView.requestLayout();
            
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setAdapter(themeRecommendAdapter);
        }
        
        if (!isGetData) {
            category();
        }
    }
    
    private void category() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.recommend);//"获取分类中的主题"
        httpRequest.add("categoryId", themeCategory.getId());
        CallServer.getInstance().postRequest("获取分类中的主题", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        RecommendTheme info = gson.fromJson(response, RecommendTheme.class);
                        if (info.getCode() == 1) {
                            isGetData = true;
                            themeRecommendAdapter.replaceData(info.getData());
                        } else {
                            activity.showToast(info.getMsg());
                        }
                    }
                    
                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        activity.showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
        
    }
}
