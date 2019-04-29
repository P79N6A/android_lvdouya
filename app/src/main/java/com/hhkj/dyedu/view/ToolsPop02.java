package com.hhkj.dyedu.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hhkj.dyedu.R;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 * 学科工具
 */

public class ToolsPop02 extends  ToolsPop {

    private LinearLayout layout01;
    private LinearLayout layout02;
    private LinearLayout layout03;
    private LinearLayout layout04;
    private LinearLayout layout05;
    private LinearLayout layout06;
    private LinearLayout layout07;

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        layout01.setOnClickListener(onClickListener);
        layout02.setOnClickListener(onClickListener);
        layout03.setOnClickListener(onClickListener);
        layout04.setOnClickListener(onClickListener);
        layout05.setOnClickListener(onClickListener);
        layout06.setOnClickListener(onClickListener);
        layout07.setOnClickListener(onClickListener);
    }

    public ToolsPop02(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_tools_02, null);
        int width=(int) context.getResources().getDimension(R.dimen.px_508);
        int height=(int) context.getResources().getDimension(R.dimen.px_500);
        popupWindow=new MyPopupWindow(view, width,height,false);
        layout01=view.findViewById(R.id.layout01);
        layout02=view.findViewById(R.id.layout02);
        layout03=view.findViewById(R.id.layout03);
        layout04=view.findViewById(R.id.layout04);
        layout05=view.findViewById(R.id.layout05);
        layout06=view.findViewById(R.id.layout06);
        layout07=view.findViewById(R.id.layout07);
    }

    public void clickMenu(int i) {

        layout01.setBackgroundColor(Color.parseColor("#00000000"));
        layout02.setBackgroundColor(Color.parseColor("#00000000"));
        layout03.setBackgroundColor(Color.parseColor("#00000000"));
        layout04.setBackgroundColor(Color.parseColor("#00000000"));
        layout05.setBackgroundColor(Color.parseColor("#00000000"));
        layout06.setBackgroundColor(Color.parseColor("#00000000"));
        layout07.setBackgroundColor(Color.parseColor("#00000000"));

        switch (i){
            case 1:
                layout01.setBackgroundColor(Color.parseColor("#a1c813"));
                break;
            case 2:
                layout02.setBackgroundColor(Color.parseColor("#a1c813"));
                break;
            case 3:
                layout03.setBackgroundColor(Color.parseColor("#a1c813"));
                break;
            case 4:
                layout04.setBackgroundColor(Color.parseColor("#a1c813"));
                break;
            case 5:
                layout05.setBackgroundColor(Color.parseColor("#a1c813"));
                break;
            case 6:
                layout06.setBackgroundColor(Color.parseColor("#a1c813"));
                break;
            case 7:
                layout07.setBackgroundColor(Color.parseColor("#a1c813"));
                break;
        }
    }
}
