package com.hhkj.dyedu.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.callback.OnMoveListener;

/**
 * Created by zangyi_shuai_ge on 2018/7/26
 *
 * @author zangyi 767450430
 */
public class SpotlightPop extends  ToolsPop{


    private ImageView imageView ;


    private SpotlightView spotlightView;
    private ImageView iv;


    public SpotlightPop(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_spotlight, null);

        int width= (int) (ScreenUtils.getScreenWidth());
        int height= (int) (ScreenUtils.getScreenHeight());
        popupWindow=new MyPopupWindow(view, width, height,false);

        spotlightView=view.findViewById(R.id.spotlightView);
        iv =view.findViewById(R.id.iv);
        spotlightView.setOnMoveListener(new OnMoveListener() {
            @Override
            public void move(int x, int y) {

//                FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) iv.getLayoutParams();
//
//                layoutParams.

                iv.setX(x);
                iv.setY(y);


            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


    }
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

        popupWindow.showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,SizeUtils.dp2px(60));

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        popupWindow.isShowing();
//        popupWindow.showAtLocation(view, RIGHT|TOP,-width,-height);
    }
}
