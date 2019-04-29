package com.hhkj.dyedu.adapter.base;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/12
 */

public abstract class MyBaseQuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K>{

    public MyBaseQuickAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
        defaultInit();
    }

    public MyBaseQuickAdapter(@Nullable List data) {
        super(data);
        defaultInit();
    }

    public MyBaseQuickAdapter(int layoutResId) {
        super(layoutResId);
        defaultInit();
    }

    private void defaultInit() {
        //设置加载动画
        this.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        this.isFirstOnly(true);
    }
    public void setSize(View view, int height, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }
}
