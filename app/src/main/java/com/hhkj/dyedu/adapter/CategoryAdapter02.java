package com.hhkj.dyedu.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.Level;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class CategoryAdapter02 extends MyBaseQuickAdapter<Level, BaseViewHolder> {

    public CategoryAdapter02() {
        super(R.layout.rv_category02_item);
    }
    private int type;

    public void setType(int type) {
        this.type = type;
    }
    @Override
    protected void convert(BaseViewHolder helper, final Level item) {
        int position = helper.getLayoutPosition();

        if (item.isChoose()) {
            helper.setImageResource(R.id.iv01, R.mipmap.list_1);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#A1C813"));
            if(type==0){
                helper.setBackgroundRes(R.id.layout01,R.drawable.bg_30);
            }else {
                helper.setBackgroundRes(R.id.layout01,R.drawable.bg_53);
            }


            helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
        } else {
            helper.setImageResource(R.id.iv01, R.mipmap.list_2);
//                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#ffffff"));
            helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
            helper.setBackgroundRes(R.id.layout01,R.drawable.bg_31);
        }

//        switch (position) {
//            case 0:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_1);
////                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#A1C813"));
//                    if(type==0){
//                        helper.setBackgroundRes(R.id.layout01,R.drawable.bg_30);
//                    }else {
//                        helper.setBackgroundRes(R.id.layout01,R.drawable.bg_53);
//                    }
//
//
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_2);
////                    helper.setBackgroundColor(R.id.layout01, Color.parseColor("#ffffff"));
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_31);
//                }
//                break;
//            case 1:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_3);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_30);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_4);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_31);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                }
//                break;
//            case 2:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_5);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_30);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_6);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_31);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                }
//                break;
//            case 3:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_7);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_30);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_8);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_31);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                }
//                break;
//            case 4:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_9);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_30);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.list_10);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_31);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                }
//                break;
//            default:
//                if (item.isChoose()) {
//                    helper.setImageResource(R.id.iv01, R.mipmap.logo);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_30);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                } else {
//                    helper.setImageResource(R.id.iv01, R.mipmap.logo);
//                    helper.setBackgroundRes(R.id.layout01,R.drawable.bg_31);
//                    helper.setTextColor(R.id.tv01, Color.parseColor("#ffffff"));
//                }
//                break;
//        }

//        helper.addOnClickListener(R.id.layout01);
        helper.setText(R.id.tv01,item.getName());

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
