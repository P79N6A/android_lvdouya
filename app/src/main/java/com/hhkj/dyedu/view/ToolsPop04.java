package com.hhkj.dyedu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hhkj.dyedu.R;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 * 提醒
 */

public class ToolsPop04 extends ToolsPop {
    private LinearLayout layout01;
    private LinearLayout layout02;
    private LinearLayout layout03;
    
    private View circle01;
    private View circle02;
    private View circle03;
    
    public ToolsPop04(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_tools_04, null);
        int width = (int) context.getResources().getDimension(R.dimen.px_508);
        int height = (int) context.getResources().getDimension(R.dimen.px_192);
        layout01 = view.findViewById(R.id.layout01);
        layout02 = view.findViewById(R.id.layout02);
        layout03 = view.findViewById(R.id.layout03);
        
        circle01 = view.findViewById(R.id.circle01);
        circle02 = view.findViewById(R.id.circle02);
        circle03 = view.findViewById(R.id.circle03);
        popupWindow = new MyPopupWindow(view, width, height, false);
    }
    
    
    public void showCircle01(boolean isShow) {
        if (isShow) {
            circle01.setVisibility(View.VISIBLE);
        } else {
            circle01.setVisibility(View.GONE);
        }
    }
    
    public void showCircle02(boolean isShow) {
        if (isShow) {
            circle02.setVisibility(View.VISIBLE);
        } else {
            circle02.setVisibility(View.GONE);
        }
    }
    
    public void showCircle03(boolean isShow) {
        if (isShow) {
            circle03.setVisibility(View.VISIBLE);
        } else {
            circle03.setVisibility(View.GONE);
        }
    }
    
    private View.OnClickListener onClickListener;
    
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        layout01.setOnClickListener(onClickListener);
        layout02.setOnClickListener(onClickListener);
        layout03.setOnClickListener(onClickListener);
    }
}
