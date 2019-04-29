package com.hhkj.dyedu.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.custom;
import com.hhkj.dyedu.bean.model.ThemeCategory;
import com.hhkj.dyedu.bean.model.ThemeCustomView;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleActivity;
import com.hhkj.dyedu.ui.fragment.ThemeCenterFragment;

import java.util.List;

import butterknife.BindView;


public class ThemeCategoryCenter2Activity extends BaseTitleActivity {

    @BindView(R.id.layoutTop)
    RelativeLayout layoutTop;

    private ThemeCategory themeCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutTop.setBackgroundColor(Color.parseColor("#FD7B63"));
        themeCategory = (ThemeCategory) getIntent().getSerializableExtra("ThemeCategory");
        setTitle(themeCategory.getName());
        fragmentManager = getSupportFragmentManager();
        page();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_theme_category_center2;
    }
    private FragmentManager fragmentManager;
    private void page() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.custom);
        httpRequest.add("category_id", themeCategory.getId());
        CallServer.getInstance().postRequest("自定义首页", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        custom info = gson.fromJson(response, custom.class);
                        if (info.getCode() == 1) {
                            String imageList = info.getData().getTemp_content();
                            List<ThemeCustomView> themeCustomViews = gson.fromJson(imageList, new TypeToken<List<ThemeCustomView>>() {
                            }.getType());
                            ThemeCenterFragment themeCenterFragment = new ThemeCenterFragment();
                            themeCenterFragment.setViewType(info.getData().getTemp_id());
                            themeCenterFragment.setThemeCustomViews(themeCustomViews);
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.content_layout, themeCenterFragment);
                            transaction.commitAllowingStateLoss();
                        } else {
                            showToast(info.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        finish();
                    }
                }, getContext());
    }
}
