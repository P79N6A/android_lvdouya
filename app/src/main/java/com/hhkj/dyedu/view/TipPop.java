package com.hhkj.dyedu.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.hhkj.dyedu.utils.LogUtil;

/**
 * Created by zangyi_shuai_ge on 2018/7/26
 *
 * @author zangyi 767450430
 * 团队竞赛
 */
public class TipPop extends  ToolsPop{


    private ImageView ivClose ;
    private EditText et01;

    private ImageView ivBg;

    public void setIvBg(String url) {
        LogUtil.i("地址"+url);
        ImageLoaderUtils.setCourseImage(url,ivBg);
    }
    public void setIvBg(int url) {
        LogUtil.i("地址"+url);
        ImageLoaderUtils.setCourseImage(url,ivBg);
    }
    public TipPop(Context context, int type) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_tip_01, null);
//        if(type==1){
//            view= LayoutInflater.from(context).inflate(R.layout.pop_tip_01, null);
//        }else if(type==2){
//            view= LayoutInflater.from(context).inflate(R.layout.pop_tip_02, null);
//        }else if(type==3){
//
//
//        }
        view= LayoutInflater.from(context).inflate(R.layout.pop_tip_03, null);
        ivBg=view.findViewById(R.id.ivBg);
        popupWindow=new MyPopupWindow(view, (int) context.getResources().getDimension(R.dimen.px_780)
                , (int) context.getResources().getDimension(R.dimen.px_886),false);
        popupWindow.setFocusable(true);
        ivClose=view.findViewById(R.id.ivClose);
//        et01=view.findViewById(R.id.et01);
//        et01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                KeyboardUtils.hideSoftInput(et01);
//
//            }
//        });


        ivClose.setOnClickListener(new View.OnClickListener() {
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

        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
    }
}
