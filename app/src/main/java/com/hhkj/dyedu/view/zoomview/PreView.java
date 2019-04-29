package com.hhkj.dyedu.view.zoomview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Maurice on 2017/6/8.
 * email:zhang_mingxu@126.com
 */

public class PreView extends LinearLayout {

    ViewGroup mViewGroup;
    private boolean isLong = false;
    private boolean isInit = false;
    //    private FrameLayout contentParent;
    private ZoomView zoomView;
    private long startTime;
    private boolean isIntercept = false;//用来处理子控件中有没有点击事件
    private boolean isOpen = false;
    private boolean isAdd = false;
    private Bitmap bitmapFromView;
    private int x = 200;
    private int y = 200;

    private FrameLayout frameLayout;
    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    PreView.this.addView(zoomView);
                    zoomView.setInitCurBitmap(bitmapFromView);
                    zoomView.setCurShowPos(x, y);
                    break;
            }
        }
    };

    public PreView(Context context) {
        this(context, null);
    }

    public PreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        isOpen = false;
//        contentParent = (FrameLayout) ((Activity) getContext()).getWindow().getDecorView().findViewById(android.R.id.content);
        zoomView = new ZoomView(getContext());
    }

    public void setFrameLayout(FrameLayout frameLayout) {
        this.frameLayout = frameLayout;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    /**
     * 打开放大镜
     */
    public boolean controlZoomView() {

        if (isOpen) {
            isOpen = false;
            this.setVisibility(GONE);
            PreView.this.removeView(zoomView);
        } else {
            isOpen = true;
            this.setVisibility(VISIBLE);

//            mViewGroup = (ViewGroup) getParent();
//            LogUtil.i("创建测试","1");
            //重新创建Bitmap
            if (bitmapFromView != null) {
                bitmapFromView.recycle();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bitmapFromView = getBitmapFromView(frameLayout);
                    handler.sendEmptyMessage(1);
                }
            }).start();


//            getHandler().post(new Runnable() {
//                @Override
//                public void run() {
//                    bitmapFromView = getBitmapFromView(frameLayout);
////                    LogUtil.i("创建测试","2");
//                    zoomView.setInitCurBitmap(bitmapFromView);
//                    zoomView.setCurShowPos(x, y);
//                    PreView.this.addView(zoomView);
//                }
//            });

        }
        return isOpen;
    }

    public static Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(c);
        else
            c.drawColor(Color.WHITE);
        // Draw view to canvas
        v.draw(c);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isOpen) {
            return false;
        }
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startTime = System.currentTimeMillis();
//                isLong = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (System.currentTimeMillis() - startTime > 150 && !isLong) {
//                    isLong = true;
//                    isIntercept = true;
//                }
//                break;
//        }
//        if (isLong) {
//            return true;
//        } else {
//            return true;
//        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isOpen) {
            return false;
        }


        x = (int) event.getX();
        y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                try {
                    zoomView.setCurShowPos(x, y);
                } catch (Exception e) {

                }

//                if (!isIntercept && System.currentTimeMillis() - startTime > 150 && !isLong) {
//                    isLong = true;
//                    isIntercept = true;
//                }
//                if (isLong && !isInit) {
//                    isInit = true;
//                    if(mViewGroup==null){
//                        mViewGroup= (ViewGroup) getParent();
//                    }
//                    Bitmap bitmapFromView = getBitmapFromView(mViewGroup);
//                    zoomView.setInitCurBitmap(bitmapFromView);
//                    contentParent.addView(zoomView);
//                }
//                if (isLong && isInit) {
//                    zoomView.setCurShowPos(x, y);
//                }
                break;
//            case MotionEvent.ACTION_UP:
//                if (isLong && isInit) {
//                    contentParent.removeView(zoomView);
//                    isLong = false;
//                    isInit = false;
//                }
//                isIntercept = false;//恢复默认
//                break;
        }
//        if (isLong) {
//            return true;
//        } else if (!isIntercept) {//此时没有点击事件需要拦截处理事件，
//            // 有点击事件的时候，只用拦截才会进去onTouch,此时isLong为true
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }
}
