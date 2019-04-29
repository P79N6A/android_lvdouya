package com.hhkj.dyedu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zangyi_shuai_ge on 2018/5/22
 */

public class TelescopeView extends View {
    private Paint mPaint;
    private Bitmap mBitmap, mBitmapBG;
    private int mDx = 300, mDy = 300;

    public TelescopeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint = new Paint();
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xxx);
    }

    public TelescopeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TelescopeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                postInvalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                mDx = -1;
//                mDy = -1;
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap == null) {
            return;
        }
        if (isNew) {
            mBitmapBG = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasbg = new Canvas(mBitmapBG);
            canvasbg.drawBitmap(mBitmap, null, new Rect(0, 0, getWidth(), getHeight()), mPaint);
            isNew=false;
        }

        if (mDx != -1 && mDy != -1) {
            mPaint.setShader(new BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            canvas.drawCircle(mDx, mDy, 150, mPaint);
        }
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
    @SuppressLint("HandlerLeak")
    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    invalidate();
                    TelescopeView.this.setVisibility(VISIBLE);
                    break;
            }
        }
    };

    private boolean isNew=true;

    public void setBgRes(final View view) {
        isNew=true;
        if(mBitmap!=null){
            mBitmap.recycle();
        }
        new Thread(){
            @Override
            public void run() {
                super.run();
                mBitmap = getBitmapFromView(view);
                handler.sendEmptyMessage(1);
            }
        }.start();


//        getHandler().post(new Runnable() {
//            @Override
//            public void run() {
//                mBitmap = getBitmapFromView(view);
//                invalidate();
//                TelescopeView.this.setVisibility(VISIBLE);
//                getHandler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        TelescopeView.this.setVisibility(VISIBLE);
//                    }
//                },100);
//            }
//        });
    }
}