package com.hhkj.dyedu.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.view.MyPopupWindow;
import com.hhkj.dyedu.view.ToolsPop;
import com.hhkj.dyedu.utils.LogUtil;

/**
 * Created by zangyi_shuai_ge on 2018/7/26
 *
 * @author zangyi 767450430
 * gif 播放
 */
public class GifPop extends ToolsPop {

    private ImageView imageView;
    private int gif;
    private Context context;
    /**
     * gif 时长
     */
    private int duration;

    public GifPop(Context context, int gif) {
        this.context = context;
        this.gif = gif;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_class_over, null);

        int width = (int) (ScreenUtils.getScreenWidth());
        int height = (int) (ScreenUtils.getScreenHeight());
        popupWindow = new MyPopupWindow(view, width, height, false);
//        popupWindow.setOutsideTouchable(true);
        imageView = view.findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    public void showAtLocation(View view) {

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
//        Glide.with(context).load(gif).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        Glide.with(context)
                .load(gif)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception arg0, Integer arg1, Target<GlideDrawable> arg2, boolean arg3) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // 计算动画时长
                        GifDrawable drawable = (GifDrawable) resource;
                        GifDecoder decoder = drawable.getDecoder();
                        duration = 0;
                        for (int i = 0; i < drawable.getFrameCount(); i++) {
                            duration += decoder.getDelay(i);
                        }
                        LogUtil.i("时间" + duration);
                        //发送延时消息，通知动画结束
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (popupWindow.isShowing()) {
                                    popupWindow.dismiss();
                                }
                            }
                        }, duration);
                        return false;
                    }
                }) //仅仅加载一次gif动画
                .into(new GlideDrawableImageViewTarget(imageView, 1));

        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (location[1] < 0) {
            location[1] = 0;
        }

        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, SizeUtils.dp2px(60));

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
