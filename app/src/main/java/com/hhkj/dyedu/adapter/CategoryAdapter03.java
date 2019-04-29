package com.hhkj.dyedu.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.Level;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class CategoryAdapter03 extends MyBaseQuickAdapter<Level, BaseViewHolder> {

    private int type;

    public CategoryAdapter03() {
        super(R.layout.rv_category03_item);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Level item) {
        int position = helper.getLayoutPosition();
        if (item.isChoose()) {
            if (type == 0) {
                helper.setBackgroundRes(R.id.layout01, R.mipmap.new_70);
            } else {
                helper.setBackgroundRes(R.id.layout01, R.mipmap.new_87);
            }
            helper.setTextColor(R.id.name, Color.parseColor("#FFFFFF"));
        } else {
            helper.setTextColor(R.id.name, Color.parseColor("#616161"));
            helper.setBackgroundRes(R.id.layout01, 0);
        }
        helper.setText(R.id.name, item.getName());
    }

    public void setChoose(int position) {

        for (int i = 0; i < this.getData().size(); i++) {
            this.getData().get(i).setChoose(false);
        }
        try {
            this.getData().get(position).setChoose(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            notifyDataSetChanged();
        }
    }
}
