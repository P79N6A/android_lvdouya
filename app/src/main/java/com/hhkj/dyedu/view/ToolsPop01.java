package com.hhkj.dyedu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hhkj.dyedu.R;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 * 表扬鼓励
 */

public class ToolsPop01 extends ToolsPop {

    private LinearLayout layout01;
    private LinearLayout layout02;
    private LinearLayout layout03;
    private View.OnClickListener onClickListener;

    public ToolsPop01(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_tools_01, null);
        int width = (int) context.getResources().getDimension(R.dimen.px_508);
        int height =(int) context.getResources().getDimension(R.dimen.px_192);
        layout01 = view.findViewById(R.id.layout01);
        layout02 = view.findViewById(R.id.layout02);
        layout03 = view.findViewById(R.id.layout03);
        popupWindow = new MyPopupWindow(view, width, height, false);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        layout01.setOnClickListener(onClickListener);
        layout02.setOnClickListener(onClickListener);
        layout03.setOnClickListener(onClickListener);

    }
}
