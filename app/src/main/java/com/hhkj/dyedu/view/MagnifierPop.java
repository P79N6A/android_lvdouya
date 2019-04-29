package com.hhkj.dyedu.view;

import android.content.Context;
import android.graphics.Bitmap;
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
 * 团队竞赛
 */
public class MagnifierPop extends  ToolsPop{


    private ImageView imageView ;


    private ImageView iv;

    private MirrorImageView mirrorImageView;


    public MagnifierPop(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_magnifier, null);

        int width= (int) (ScreenUtils.getScreenWidth());
        int height= (int) (ScreenUtils.getScreenHeight());
        popupWindow=new MyPopupWindow(view, width, height,false);

        mirrorImageView=view.findViewById(R.id.mirrorImageView);
        iv =view.findViewById(R.id.iv);
        mirrorImageView.setOnMoveListener(new OnMoveListener() {
            @Override
            public void move(int x, int y) {
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

//        mirrorImageView


        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能

        mirrorImageView.setBackGround(bitmap);


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
