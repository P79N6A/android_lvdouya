package com.hhkj.dyedu.view.scrawldemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.view.scrawldemo.bean.DrawPathEntry;
import com.hhkj.dyedu.view.scrawldemo.config.AnnotationConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Project:AndroidDemo
 * Author:dyping
 * Date:2017/4/11 10:36
 */

public class ScrawlBoardView extends View {

    Canvas paintCanvas;
    Paint paint, eraserPaint;
    Bitmap bitmap;
    Bitmap backgroudBitmap;
    float startX, startY, endX, endY;
    Context context;
    boolean isEraser;
    List<DrawPathEntry> drawPathList = new ArrayList<>();
    Path path;
    private Bitmap PainBg1;
    private Bitmap PainBg2;
    private Bitmap PainBg3;
    private Bitmap PainBg4;
    private Bitmap PainBg5;
    private Bitmap PainBg6;
    private Bitmap PainBg7;
    /**
     * 设置画笔颜色及橡皮擦
     *
     * @param type
     */

    private int colorType = 1;


    public ScrawlBoardView(Context context) {
        super(context);
    }

    public ScrawlBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setColor(ContextCompat.getColor(context, R.color.red_radio));
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);

        eraserPaint = new Paint();
        eraserPaint.setStyle(Paint.Style.STROKE);
        eraserPaint.setStrokeWidth(20);
        eraserPaint.setColor(Color.TRANSPARENT);
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        eraserPaint.setXfermode(xfermode);

//        paintCanvas = new Canvas(Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.RGB_565));
//        paintCanvas=new Canvas();

//        PainBg1 = createPainBg(1);
//        PainBg3 = createPainBg(3);
//        PainBg4 = createPainBg(4);
//        PainBg5 = createPainBg(5);
//        PainBg6 = createPainBg(6);
//        PainBg7 = createPainBg(7);

        this.bitmap = Bitmap.createBitmap(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(), Bitmap.Config.ARGB_8888);
        paintCanvas = new Canvas(this.bitmap);
        this.context = context;
    }

    public ScrawlBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEraser(boolean eraser) {
        isEraser = eraser;
    }

    /**
     * 设置背景图片及监理新的用来涂鸦的Bitmap
     */
    public void setBackgroud() {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.xxxxx);
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(), true);
        bitmap.recycle();
        backgroudBitmap = bitmap1;


//        mViewGroup = (ViewGroup) getParent();
        //重新创建Bitmap


//        this.backgroudBitmap = Bitmap.createBitmap(ScreenUtils, backgroudBitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        this.bitmap = Bitmap.createBitmap(ScreenUtils, backgroudBitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        paintCanvas = new Canvas(this.bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                path = new Path();
                path.moveTo(startX, startY);
                if (PainBg1 != null) {
                    PainBg1.recycle();//回收
                }
                if (colorType == 1) {
                    PainBg1 = createPainBg(1);
                } else if (colorType == 2) {
                    PainBg1 = createPainBg(2);
                } else if (colorType == 3) {
                    PainBg1 = createPainBg(3);
                } else if (colorType == 4) {
                    PainBg1 = createPainBg(4);
                } else if (colorType == 5) {
                    PainBg1 = createPainBg(5);
                } else if (colorType == 6) {
                    PainBg1 = createPainBg(6);
                } else if (colorType == 7) {
                    PainBg1 = createPainBg(7);
                }

                break;

            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                endY = event.getY();
                path.quadTo(startX, startY, endX, endY);


//                if (isEraser) {
                    paintCanvas.drawPath(path, isEraser ? eraserPaint : paint);
//                } else {
//
//                    int sc = paintCanvas.saveLayer(0, 0, 1920, 1200, null, Canvas.ALL_SAVE_FLAG);
//                    paint.setXfermode(null);
//                    paintCanvas.drawPath(path, paint);
//                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//                    paintCanvas.drawBitmap(PainBg1, 0, 0, paint);
////                    if(colorType==1){
////                        paintCanvas.drawBitmap(PainBg1, 0, 0, paint);
////
////                    }
////                    else  if(colorType==2){
////                        paintCanvas.drawBitmap(PainBg2, 0, 0, paint);
////
////                    }else  if(colorType==3){
////                        paintCanvas.drawBitmap(PainBg3, 0, 0, paint);
////
////                    }
////                    else  if(colorType==4){
////                        paintCanvas.drawBitmap(PainBg4, 0, 0, paint);
////                    }
////                    else  if(colorType==5){
////                        paintCanvas.drawBitmap(PainBg5, 0, 0, paint);
////
////                    }
////                    else  if(colorType==6){
////                        paintCanvas.drawBitmap(PainBg6, 0, 0, paint);
////
////                    }
////                    else  if(colorType==7){
////                        paintCanvas.drawBitmap(PainBg7, 0, 0, paint);
////
////                    }
//                    paint.setXfermode(null);
//                    paintCanvas.restoreToCount(sc);
//
//                }

                startX = endX;
                startY = endY;

                postInvalidate();
                break;

            case MotionEvent.ACTION_UP:
                if (PainBg1 != null) {
                    PainBg1.recycle();//回收
                }
                drawPathList.add(new DrawPathEntry(path, isEraser ? eraserPaint.getColor() : paint.getColor(), isEraser));
                break;

            default:
                break;
        }
        return true;
    }

    private Bitmap createPainBg(int type) {
        Bitmap bitmap = Bitmap.createBitmap(1980, 1200, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap myBitmap = null;
        if (type == 1) {
            myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blackboard_bg_01);
        } else if (type == 3) {
            myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blackboard_bg_03);
        } else if (type == 4) {
            myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blackboard_bg_04);
        } else if (type == 6) {
            myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blackboard_bg_06);
        } else if (type == 5) {
            myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blackboard_bg_05);
        } else if (type == 7) {
            myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blackboard_bg_07);
        }

        BitmapShader bitmapShader = new BitmapShader(myBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        p.setShader(bitmapShader);
        c.drawRect(0, 0, c.getWidth(), c.getHeight(), p);
        c.drawRect(0, 0, 1980, 1200, p);
        return bitmap;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //不绘制背景
        if (backgroudBitmap != null && !backgroudBitmap.isRecycled()) {
            canvas.drawBitmap(backgroudBitmap, 0, 0, null);
        }

        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    private Bitmap drawCircleBm() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#79B7DF"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawCircle(250, 250, 250, paint);
        return bm;
    }

    private Bitmap drawRectBm() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#000000"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawRect(new RectF(0, 0, 70, 70), paint);
        return bm;
    }

    public void setPaintType(int type) {

        isEraser = false;
        switch (type) {
            case AnnotationConfig.PaintType.Paint_Red:
                colorType = 1;
                paint.setColor(ContextCompat.getColor(context, R.color.red_radio));
                break;
            case AnnotationConfig.PaintType.Paint_Orange:
                colorType = 2;
                colorType = 3;
                paint.setColor(ContextCompat.getColor(context, R.color.orange_radio));
                break;
            case AnnotationConfig.PaintType.Paint_Yellow:
                paint.setColor(ContextCompat.getColor(context, R.color.yellow_radio));
                break;
            case AnnotationConfig.PaintType.Paint_Green:
                colorType = 4;
                paint.setColor(ContextCompat.getColor(context, R.color.green_radio));
                break;
            case AnnotationConfig.PaintType.Paint_Blue:
                colorType = 5;
                paint.setColor(ContextCompat.getColor(context, R.color.blue_radio));
                break;
            case AnnotationConfig.PaintType.Paint_Purple:
                colorType = 6;
                paint.setColor(ContextCompat.getColor(context, R.color.purple_radio));
                break;
            case AnnotationConfig.PaintType.Paint_White:
                colorType = 7;
                paint.setColor(ContextCompat.getColor(context, R.color.white_radio));
                break;
            case AnnotationConfig.PaintType.Paint_Eraser:
                isEraser = true;
                break;
            default:
                break;
        }
    }

    /**
     * @param size 设置画笔大小
     */
    public void setPaintSize(int size) {
        paint.setStrokeWidth(size);
    }

    /**
     * @param size 设置橡皮大小
     */
    public void setEraserPaintSize(int size) {
        eraserPaint.setStrokeWidth(size);
    }

    /**
     * 撤销操作
     */
    public void cancelPath() {
        if (drawPathList != null && drawPathList.size() <= 0) {
            return;
        }
        drawPathList.remove(drawPathList.size() - 1);
        paintCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (DrawPathEntry entry : drawPathList) {
            paint.setColor(entry.getPaintColor());
            paintCanvas.drawPath(entry.getPath(), entry.isEraser() ? eraserPaint : paint);
        }
        postInvalidate();
    }


    /**
     * 清空涂鸦
     */
    public void clearScrawlBoard() {
        paintCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawPathList.clear();
        postInvalidate();
    }


}
