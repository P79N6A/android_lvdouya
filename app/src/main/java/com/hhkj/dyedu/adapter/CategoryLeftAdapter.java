package com.hhkj.dyedu.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.Level;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class CategoryLeftAdapter extends MyBaseQuickAdapter<Level, BaseViewHolder> {

    private boolean isInAnimation=false;

    public boolean isInAnimation() {
        return isInAnimation;
    }

    public void setInAnimation(boolean inAnimation) {
        isInAnimation = inAnimation;
    }

    public CategoryLeftAdapter() {
        super(R.layout.rv_category_left);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Level item) {
        int position = helper.getLayoutPosition();
        if (item.isChoose()) {
            helper.setImageResource(R.id.iv01, R.mipmap.list_1);
//            helper.setBackgroundColor(R.id.layout01, Color.parseColor("#A1C813"));
            helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
            helper.setBackgroundRes(R.id.layout01,R.mipmap.new_87);
        } else {
            helper.setImageResource(R.id.iv01, R.mipmap.list_2);
//            helper.setBackgroundColor(R.id.layout01, Color.parseColor("#ffffff"));
            helper.setTextColor(R.id.tv01, Color.parseColor("#999999"));
            helper.setBackgroundRes(R.id.layout01,0);
        }


        if(isInAnimation){
            helper.setBackgroundRes(R.id.layout01,0);
            helper.setTextColor(R.id.tv01, Color.parseColor("#999999"));
        }


//        switch (position) {
//            case 0:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_1);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#A1C813"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_2);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#ffffff"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#999999"));
//                }
//                break;
//            case 1:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_3);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#A1C813"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_4);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#ffffff"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#999999"));
//                }
//                break;
//            case 2:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_5);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#A1C813"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_6);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#ffffff"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#999999"));
//                }
//                break;
//            case 3:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_7);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#A1C813"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_8);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#ffffff"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#999999"));
//                }
//                break;
//            case 4:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_9);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#A1C813"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_10);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#ffffff"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#999999"));
//                }
//                break;
//            default:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.logo);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#A1C813"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.logo);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#ffffff"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#999999"));
//                }
//                break;
//        }

        helper.addOnClickListener(R.id.layout01);
        helper.setText(R.id.tv01, item.getName());

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
