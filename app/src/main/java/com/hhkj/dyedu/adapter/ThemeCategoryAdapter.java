package com.hhkj.dyedu.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.ThemeCategory;
import com.hhkj.dyedu.utils.ImageLoaderUtils;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 * 主题分类
 */

public class ThemeCategoryAdapter extends MyBaseQuickAdapter<ThemeCategory, BaseViewHolder> {
    public ThemeCategoryAdapter() {
        super(R.layout.rv_category_item);
    }

    @Override
    protected void convert(BaseViewHolder helper,  ThemeCategory item) {
        ImageView ivHead = (ImageView) helper.getView(R.id.ivHead);
        ImageLoaderUtils.setImage(item.getImage(), ivHead);
        helper.setText(R.id.tvName, item.getName());
    }
}
