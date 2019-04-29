package com.hhkj.dyedu.adapter;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.Theme;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.hhkj.dyedu.view.ThemeBgView;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 * 主题
 */

public class ThemeAdapter extends MyBaseQuickAdapter<Theme, BaseViewHolder> {


    private String name = "";

    public ThemeAdapter() {
        super(R.layout.rv_theme_item_02);
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void convert(BaseViewHolder helper, Theme item) {
        //主题课程
        ImageLoaderUtils.setImage(item.getImage(), (ImageView) helper.getView(R.id.ivHead));
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvName, name);

        ThemeBgView themeBgView = helper.getView(R.id.themeBg);
        themeBgView.setNumber(item.getCount());

        if (item.getType() == 2) {
            //会员主题
            TextView textView = helper.getView(R.id.tvPrice);
            textView.setBackgroundColor(Color.parseColor("#9DDF0E"));
            textView.setText("会员免费");
        } else {
            //非会员主题
            TextView textView = helper.getView(R.id.tvPrice);
            textView.setBackgroundColor(Color.parseColor("#5DC0FB"));
            textView.setText("￥ " + item.getPrice());
        }
        if(item.getLock()==0){
            helper.setVisible(R.id.layoutLock,false);
        }else {
            helper.setVisible(R.id.layoutLock,true);
        }
        

    }

}
