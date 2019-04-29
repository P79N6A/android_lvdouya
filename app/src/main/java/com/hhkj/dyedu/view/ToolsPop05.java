package com.hhkj.dyedu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.test1;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 * 学科工具
 */

public class ToolsPop05 extends  ToolsPop {
    private LinearLayout layout01,layout02,layout03;
    public ToolsPop05(Context context, test1 pptInfo) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_tools_05, null);
        int width = (int) context.getResources().getDimension(R.dimen.px_508);
        int height = (int) context.getResources().getDimension(R.dimen.px_192);
        layout01 = view.findViewById(R.id.layout01);
        layout02 = view.findViewById(R.id.layout02);
        layout03 = view.findViewById(R.id.layout03);
        
        if(pptInfo.getData().getQuiz().size()==0){
            //没有提问
            view.findViewById(R.id.circle02).setVisibility(View.GONE);
        }else {
            view.findViewById(R.id.circle02).setVisibility(View.VISIBLE);
        }
    
        if(pptInfo.getData().getDrawing().trim().equals("")){
            //没有图纸
            view.findViewById(R.id.circle01).setVisibility(View.GONE);
        }else {
            view.findViewById(R.id.circle01).setVisibility(View.VISIBLE);
        }
        if (pptInfo.getData().getPdf_3d()!=null){
            //没有搭建
            view.findViewById(R.id.circle03).setVisibility(View.VISIBLE);
        }else {
            view.findViewById(R.id.circle03).setVisibility(View.GONE);
        }
        
        popupWindow = new MyPopupWindow(view, width, height, false);
    }
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        layout01.setOnClickListener(onClickListener);
        layout02.setOnClickListener(onClickListener);
        layout03.setOnClickListener(onClickListener);
    }
}
