package com.hhkj.dyedu.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.Competition;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class CompetitionAdapter extends MyBaseQuickAdapter<Competition, BaseViewHolder> {

    private int maxPos = -1;


    private int nowShowPos = -1;


    private int nowRank=-1;

    public int getNowRank() {
        return nowRank;
    }

    public void setNowRank(int nowRank) {
        this.nowRank = nowRank;
    }

    public CompetitionAdapter(int width, int height) {
        super(R.layout.rv_competition_item);
    }

    public int getNowShowPos() {
        return nowShowPos;
    }

    public void setMaxPos(int maxPos) {
        this.maxPos = maxPos;
    }


    @Override
    protected void convert(BaseViewHolder helper, Competition item) {
        int position = helper.getLayoutPosition();

        helper.setVisible(R.id.ivC,false);
        helper.setVisible(R.id.ivC2,false);

        if(nowRank!=-1){
            //rank 模式
            if(item.getRank()==0){
                helper.setVisible(R.id.ivC,true);
                helper.setVisible(R.id.ivC2,true);
            }
        }

        if(maxPos!=-1){
            //按分数最快来算
            if(maxPos==position){
                helper.setVisible(R.id.ivC,true);
                helper.setVisible(R.id.ivC2,true);
            }
        }
        helper.setText(R.id.tvNum01,item.getTime1());
        helper.setText(R.id.tvNum02,item.getTime2());
        helper.setText(R.id.tvNum03,item.getTime3());
        helper.setText(R.id.tvNum04,item.getTime4());

        int a=position%3;
        if(a==0){
            helper.setImageResource(R.id.ivBg,R.mipmap.bg_new_06);
        }else  if(a==1){
            helper.setImageResource(R.id.ivBg,R.mipmap.bg_new_03);
        }else  if(a==2){
            helper.setImageResource(R.id.ivBg,R.mipmap.bg_new_08);
        }
        helper.setText(R.id.tv01, item.getScore() + "");
        if (item.getScore() == 0) {
            helper.setTextColor(R.id.tv01, Color.parseColor("#999999"));
        } else {
            helper.setTextColor(R.id.tv01, Color.parseColor("#ff0000"));
        }
        helper.addOnClickListener(R.id.iv01).addOnClickListener(R.id.iv02).addOnClickListener(R.id.layoutTime);
        if (position == 0) {
            helper.setText(R.id.tv02, "一队");
        } else if (position == 1) {
            helper.setText(R.id.tv02, "二队");
        } else if (position == 2) {
            helper.setText(R.id.tv02, "三队");
        } else if (position == 3) {
            helper.setText(R.id.tv02, "四队");
        } else if (position == 4) {
            helper.setText(R.id.tv02, "五队");
        } else if (position == 5) {
            helper.setText(R.id.tv02, "六队");
        } else if (position == 6) {
            helper.setText(R.id.tv02, "七队");
        } else if (position == 7) {
            helper.setText(R.id.tv02, "八队");
        } else if (position == 8) {
            helper.setText(R.id.tv02, "九队");
        } else if (position == 9) {
            helper.setText(R.id.tv02, "十队");
        } else {
            helper.setText(R.id.tv02, "");
        }
    }
}
