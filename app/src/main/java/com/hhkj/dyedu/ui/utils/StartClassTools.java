package com.hhkj.dyedu.ui.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.view.ScaleLinearLayout;
import com.hhkj.dyedu.view.ToolsPop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 * 开始上课界面 界面尺寸工具
 */

public class StartClassTools {
    private Context context;
    
    //上下左右工具栏尺寸
    private int start_class_top_bar_height;
    private int start_class_left_ppt_width;
    private float start_class_bottom_bar_height;
    private int start_class_right_menu_small_width;
    
    private int screenWidth;
    private int screenHeight;
    
    //webView尺寸
    private int smallMarginBottom;
    private int smallMarginLeft;
    private int smallMarginRight;
    private int smallMarginTop;
    
    private float bigMarginBottom;
    private int bigMarginLeft;
    private int bigMarginRight;
    private int bigMarginTop;
    
    
    //时间进度条总长度
    private int progressAllHeight;
    private int progressIconHeight;
    
    
    public int getScreenHeight() {
        return screenHeight;
    }
    
    //小屏状态下右侧菜单宽度
    private int smallRightWidth;
    
    //
    private int bigRightMenuWidth;
    /**
     * 大屏状态下工具栏处理类
     */
    private List<ToolsPop> toolsPops;
    
    public StartClassTools(Context context) {
        this.context = context;
        
        start_class_top_bar_height = (int) context.getResources().getDimension(R.dimen.start_class_top_bar_height);
        start_class_left_ppt_width = (int) context.getResources().getDimension(R.dimen.start_class_left_ppt_width);
        start_class_bottom_bar_height = (float) context.getResources().getDimension(R.dimen.start_class_bottom_bar_height);
        start_class_right_menu_small_width = (int) context.getResources().getDimension(R.dimen.start_class_right_menu_small_width);
        
        bigRightMenuWidth = (int) context.getResources().getDimension(R.dimen.px_235);
        
        screenWidth = ScreenUtils.getScreenWidth();
        screenHeight = ScreenUtils.getScreenHeight();
        
        
        smallRightWidth = start_class_right_menu_small_width;
        
        progressAllHeight = (int) (screenHeight - start_class_bottom_bar_height);
        progressIconHeight = SizeUtils.dp2px(16);
        
        //==================================webView=====================================================
        //计算小屏时候的尺寸
//        int lastWidth = screenWidth - start_class_right_menu_small_width - start_class_left_ppt_width;
//        int lastHeight = (int) (screenHeight - start_class_top_bar_height - start_class_bottom_bar_height);
        smallMarginBottom = (int) (start_class_bottom_bar_height + 2);
        smallMarginTop = start_class_top_bar_height + 2;
        smallMarginLeft = start_class_left_ppt_width + 2;
        smallMarginRight = start_class_right_menu_small_width + 2;
        //全屏尺寸
        //pad 尺寸 1920*1200  1920*（1200-120）
        bigMarginBottom = start_class_bottom_bar_height + 8;
        bigMarginTop = 0;
        bigMarginRight = 0;
        bigMarginLeft = 0;
        
    }
    
//    public void setTimeProgress(int position, int pptAllNums, View view, TextView textView, View view2) {
//
//        textView.setText(position + "");
////        position=1;
//        double p = position / ((pptAllNums) * 1.000);
//
//        int mBottom = (int) (progressAllHeight * p);
//        //白色View覆盖进度条
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//        layoutParams.setMargins(0, 0, 0, mBottom);
//        view.setLayoutParams(layoutParams);
//        //图标位置
//        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) view2.getLayoutParams();
//        layoutParams2.height = SizeUtils.dp2px(32);
//        layoutParams2.width = SizeUtils.dp2px(36);
//        layoutParams2.setMargins(SizeUtils.dp2px(20), 0, 0, mBottom - progressIconHeight);
//        view2.setLayoutParams(layoutParams2);
//    }
    
    public int getSmallRightWidth() {
        return smallRightWidth;
    }
    
    public int getBigRightMenuWidth() {
        return bigRightMenuWidth;
    }
    
    public void addToolsPops(ToolsPop toolsPop) {
        if (toolsPops == null) {
            toolsPops = new ArrayList<>();
        }
        toolsPops.add(toolsPop);
    }
    
    public void dismissAllToolsPops() {
        
        if (toolsPops == null) {
            toolsPops = new ArrayList<>();
        }
        for (int i = 0; i < toolsPops.size(); i++) {
            toolsPops.get(i).dismiss();
        }
    }
    
    public void showToolsPops(ToolsPop toolsPop, ScaleLinearLayout view) {
        
        if (toolsPops == null) {
            toolsPops = new ArrayList<>();
        }
        if (toolsPop.isShowing()) {
            //全关
            for (int i = 0; i < toolsPops.size(); i++) {
                toolsPops.get(i).dismiss();
            }
            view.setChoose(false);
        } else {
            for (int i = 0; i < toolsPops.size(); i++) {
                toolsPops.get(i).dismiss();
            }
            toolsPop.showAtLocation(view);
            view.setChoose(true);
        }
    }
    
    public void delayClick(final View view) {
        view.setClickable(false);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setClickable(true);
            }
        }, 300);
    }
    
    
    /**
     * 关闭菜单
     */
    public void closeTools(final View toolView, int width) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(toolView, "translationX", 0, width);
        objectAnimator.setDuration(300);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                toolView.setVisibility(View.GONE);
                super.onAnimationEnd(animation);
            }
        });
        objectAnimator.start();
    }
    
    public void closeTools2(final View toolView, int width, final View view2) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(toolView, "translationX", 0, width);
        objectAnimator.setDuration(300);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                toolView.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                super.onAnimationEnd(animation);
            }
        });
        objectAnimator.start();
    }
    
    /**
     * 打开菜单
     */
    public void openTools(View toolView, int width) {
        toolView.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(toolView, "translationX", width, 0);
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }
    
    /**
     * 全屏webView 位置
     */
    public void setBigWebView(WebView webView) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) webView.getLayoutParams();
        layoutParams.setMargins(bigMarginLeft, bigMarginTop, bigMarginRight, (int) bigMarginBottom);
        webView.setLayoutParams(layoutParams);
    }
    
    /**
     * 小屏webView 位置
     */
    public void setSmallWebView(WebView webView) {
        
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) webView.getLayoutParams();
        layoutParams.setMargins(smallMarginLeft, smallMarginTop, smallMarginRight, smallMarginBottom);
        webView.setLayoutParams(layoutParams);
    }
}
