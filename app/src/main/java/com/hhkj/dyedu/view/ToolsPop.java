package com.hhkj.dyedu.view;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 */

public class ToolsPop  {
    public MyPopupWindow popupWindow;

    public void showAtLocation(View view) {

        if(popupWindow.isShowing()){
            popupWindow.dismiss();
        }

        int width=view.getMeasuredWidth();
        int height=view.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if(location[1]<0){
            location[1]=0;
        }

        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0]-popupWindow.getWidth()-44, location[1]);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
//        popupWindow.isShowing();
//        popupWindow.showAtLocation(view, RIGHT|TOP,-width,-height);
    }

    public boolean isShowing() {
        return  popupWindow.isShowing();
    }

    public void dismiss() {
        popupWindow.dismiss();
    }
}
