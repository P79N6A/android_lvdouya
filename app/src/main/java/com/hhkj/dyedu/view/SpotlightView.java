package com.hhkj.dyedu.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.callback.OnMoveListener;
import com.hhkj.dyedu.utils.LogUtil;

/**
 * Created by zangyi_shuai_ge on 2018/7/27
 *
 * @author zangyi 767450430
 * 聚光灯
 */
public class SpotlightView extends View {

    private final String TAG = "SpotlightView";

    //聚光灯长宽最小值
    private final int spotlightHeightMin = 400, spotlightWidthMin = 400;
    private final int minRadius = 200;
    Paint mPaint;
    Context mContext;
    int BlueColor;
    int PinkColor;
    int mWith;
    int mHeight;
    //屏幕尺寸
    private int screenHeight;
    private int screenWidth;
    //底部灰色背景
    private Bitmap backGround;
    private PorterDuffXfermode porterDuffXfermode;
    //聚光灯左上角的坐标
    private int spotlightX, spotlightY;
    //聚光灯的长宽
    private int spotlightHeight, spotlightWidth;
    private int cornerLength;

    //画线的画笔
    private int downType = 1;// 1 为点击聚光等内部   2左上角  3右上角  4左下角 5右下角
    private Paint linePaint;
    private Paint linePaint2;
    private int lintWidth = 18;
    private int lintColor1, lintColor2;
    private OnMoveListener onMoveListener;
    private int cx, cy, radius = 200;

    public SpotlightView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        BlueColor = ContextCompat.getColor(mContext, R.color.test);
        PinkColor = ContextCompat.getColor(mContext, R.color.colorAccent);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(lintWidth);
        linePaint.setColor(Color.parseColor("#D6D6D6"));

        linePaint2 = new Paint();
        linePaint2.setStyle(Paint.Style.FILL);
        linePaint2.setAntiAlias(true);
        linePaint2.setStrokeWidth(lintWidth);
        linePaint2.setColor(Color.parseColor("#F7AA24"));


        screenHeight = ScreenUtils.getScreenHeight();
        screenWidth = ScreenUtils.getScreenWidth();
        LogUtil.i("屏幕尺寸" + screenHeight + "===" + screenWidth);


        backGround = drawRectBm();
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);


        spotlightHeight = spotlightHeightMin;
        spotlightWidth = spotlightWidthMin;


        spotlightX = screenWidth / 2;
        spotlightY = screenHeight / 2;

        cornerLength = 50;

        lintColor1 = Color.parseColor("#FFFFFF");
        lintColor2 = Color.parseColor("#A1C813");
    }

    /**
     * @return 绘制底部灰色背景
     */
    private Bitmap drawRectBm() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(BlueColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawRect(new RectF(0, 0, screenWidth, screenHeight), paint);
        return bm;
    }

    public SpotlightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpotlightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnMoveListener(OnMoveListener onMoveListener) {
        this.onMoveListener = onMoveListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int clickX = (int) event.getX();
        int clickY = (int) event.getY();
        int rx = spotlightX + radius;
        int ry = spotlightY + radius;
        LogUtil.i("圆心" + rx + "  " + ry + "  " + radius);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                int d = 30;
                downType = 0;
                if (getDistance(rx, ry, clickX, clickY) <= radius + 30) {
                    //在圆内
                    LogUtil.i("在圆内");

                    if (
                            clickX >= spotlightX + radius - d
                                    && clickX < spotlightX + radius + d
                                    && clickY <= spotlightY + 30
                                    && clickY > spotlightY - 30 - d
                            ) {
                        downType = 2;
                        LogUtil.i("点到最上面");
                    } else if (
                            clickX >= spotlightX + radius - d
                                    && clickX < spotlightX + radius + d
                                    && clickY <= spotlightY + 30 + radius * 2 + d
                                    && clickY > spotlightY - 30 - d + radius * 2) {
                        downType = 3;
                        LogUtil.i("点到最下面");
                    } else if (clickX >= spotlightX - d - 30 && clickX < spotlightX + d && clickY <= spotlightY + radius + d && clickY > spotlightY + radius - d) {
                        downType = 4;
                        LogUtil.i("点到最左面");
                    } else if (clickX >= spotlightX - d - 30 + 2 * radius && clickX < spotlightX + d + 2 * radius && clickY <= spotlightY + radius + d && clickY > spotlightY + radius - d) {
                        downType = 5;
                        LogUtil.i("点到最左面2");
                    } else {
                        LogUtil.i("移动圆");
                        downType = 1;
                    }
                } else {
                    LogUtil.i("在圆外");
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i("圆心2" + rx + "  " + ry + "  " + radius);
                if (downType == 1) {
                    LogUtil.i("移动圆2");
                    //点击到内部移动聚光灯
                    spotlightX = clickX - radius;//把触摸点移到中间
                    spotlightY = clickY - radius;
                    postInvalidate();
                } else if (downType == 2) {
                    boolean needRefresh = false;
                    int dy = clickY - spotlightY;//垂直方向移动的距离
                    int needY = (radius - dy / 2);
                    if (needY >= minRadius && needY <= 600) {
                        radius = needY;
                        spotlightX = rx - radius;
                        spotlightY = ry - radius;
                        needRefresh = true;
                    }
                    if (needRefresh) {
                        postInvalidate();
                    }
                } else if (downType == 3) {
                    //点击左上角
                    boolean needRefresh = false;
                    //计算水平方向
                    int dy = clickY - spotlightY - 2 * radius;
                    int needY = (radius + dy / 2);
                    if (needY >= minRadius && needY <= 600) {
                        radius = needY;
                        spotlightX = rx - radius;
                        spotlightY = ry - radius;
                        needRefresh = true;
                    }
                    if (needRefresh) {
                        postInvalidate();
                    }
                } else if (downType == 4) {
                    boolean needRefresh = false;
                    //计算水平方向
                    int dx = clickX - spotlightX;//触摸移动的距离
                    int needX = radius - dx/2;
                    if (needX >= minRadius && needX <= 600) {
                        radius = needX;
                        spotlightX = rx - radius;
                        spotlightY = ry - radius;
                        needRefresh = true;
                    }
                    if (needRefresh) {
                        postInvalidate();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public double getDistance(int x, int y, int x2, int y2) {
        double _x = Math.abs(x - x2);
        double _y = Math.abs(y - y2);
        return Math.sqrt(_x * _x + _y * _y);
    }


    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        int clickX = (int) event.getX();
//        int clickY = (int) event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //手指按下的时候
//                downType = 0;
//                if (clickX >= spotlightX && clickX <= spotlightX + spotlightWidth//宽度满足
//                        && clickY >= spotlightY && clickY <= spotlightY + spotlightHeight//高满足
//                        ) {
//
//                    //点到了聚光灯
//
//                    if (clickX >= spotlightX && clickX <= spotlightX + cornerLength//宽度满足
//                            && clickY >= spotlightY && clickY <= spotlightY + cornerLength//高满足
//                            ) {
//                        downType = 2;
//                        //点到右上角区域
//                        LogUtil.i(TAG, "点到聚光灯左上角");
//
//                    } else if (clickX >= spotlightX + spotlightWidth - cornerLength && clickX <= spotlightX + spotlightWidth//宽度满足
//                            && clickY >= spotlightY && clickY <= spotlightY + cornerLength//高满足
//                            ) {
//                        downType = 3;
//                        //点到右上角区域
//                        LogUtil.i(TAG, "点到聚光灯右上角");
//
//                    } else if (clickX >= spotlightX && clickX <= spotlightX + cornerLength//宽度满足
//                            && clickY >= spotlightY + spotlightHeight - cornerLength && clickY <= spotlightY + spotlightHeight //高满足
//                            ) {
//                        downType = 4;
//                        //点到右上角区域
//                        LogUtil.i(TAG, "点到聚光灯左下角");
//
//                    } else if (clickX >= spotlightX + spotlightWidth - cornerLength && clickX <= spotlightX + spotlightWidth//宽度满足
//                            && clickY >= spotlightY + spotlightHeight - cornerLength && clickY <= spotlightY + spotlightHeight //高满足
//                            ) {
//                        downType = 5;
//                        //点到右上角区域
//                        LogUtil.i(TAG, "点到聚光灯右下角");
//
//                    } else {
//                        downType = 1;
//                        //点到右上角区域
//                        LogUtil.i(TAG, "点到聚光灯内部");
//                    }
//
//
//                }
////
////
////                mDx = (int) event.getX();
////                mDy = (int) event.getY();
////                postInvalidate();
//                return true;
//            case MotionEvent.ACTION_MOVE:
//
//                if (downType == 1) {
//                    //点击到内部移动聚光灯
//                    spotlightX = clickX - spotlightWidth / 2;//把触摸点移到中间
//                    spotlightY = clickY - spotlightHeight / 2;
//                    postInvalidate();
//
//                } else if (downType == 2) {
//                    //点击左上角
//                    boolean needRefresh=false;
//                    //计算水平方向
//                    int dx = clickX - spotlightX;//触摸移动的距离
//                    int needX = spotlightWidth - dx;
//                    if (needX >= spotlightWidthMin) {
//                        spotlightWidth = needX;
//                        spotlightX = clickX;
//                        needRefresh=true;
//                    }
//                    int dy=clickY-spotlightY;//垂直方向移动的距离
//                    int needY=spotlightHeight-dy;
//                    if (needY >= spotlightHeightMin) {
//                        spotlightHeight = needY;
//                        spotlightY = clickY;
//                        needRefresh=true;
//                    }
//                    if(needRefresh){
//                        postInvalidate();
//                    }
//                } else if (downType == 3) {
//                    //点击右上角
//                    boolean needRefresh=false;
//                    //计算水平方向
//                    int dx = clickX - spotlightX-spotlightWidth;//触摸移动的距离
//                    dx=-dx;
//                    int needX = spotlightWidth - dx;
//                    if (needX >= spotlightWidthMin) {
//                        spotlightWidth = needX;
//                        spotlightX = clickX-spotlightWidth;
//                        needRefresh=true;
//                    }
//                    int dy=clickY-spotlightY;//垂直方向移动的距离
//                    int needY=spotlightHeight-dy;
//                    if (needY >= spotlightHeightMin) {
//                        spotlightHeight = needY;
//                        spotlightY = clickY;
//                        needRefresh=true;
//                    }
//                    if(needRefresh){
//                        postInvalidate();
//                    }
//                }else if (downType == 4) {
//                    //点击左下角
//                    boolean needRefresh=false;
//                    //计算水平方向
//                    //计算水平方向
//                    int dx = clickX - spotlightX;//触摸移动的距离
//                    int needX = spotlightWidth - dx;
//                    if (needX >= spotlightWidthMin) {
//                        spotlightWidth = needX;
//                        spotlightX = clickX;
//                        needRefresh=true;
//                    }
//                    int dy=clickY-spotlightY-spotlightHeight;//垂直方向移动的距离
//                    dy=-dy;
//                    int needY=spotlightHeight-dy;
//                    if (needY >= spotlightHeightMin) {
//                        spotlightHeight = needY;
//                        spotlightY = clickY-spotlightHeight;
//                        needRefresh=true;
//                    }
//                    if(needRefresh){
//                        postInvalidate();
//                    }
//                }else if (downType == 5) {
//                    //点击右下角
//                    boolean needRefresh=false;
//                    //计算水平方向
//                    int dx = clickX - spotlightX-spotlightWidth;//触摸移动的距离
//                    dx=-dx;
//                    int needX = spotlightWidth - dx;
//                    if (needX >= spotlightWidthMin) {
//                        spotlightWidth = needX;
//                        spotlightX = clickX-spotlightWidth;
//                        needRefresh=true;
//                    }
//                    int dy=clickY-spotlightY-spotlightHeight;//垂直方向移动的距离
//                    dy=-dy;
//                    int needY=spotlightHeight-dy;
//                    if (needY >= spotlightHeightMin) {
//                        spotlightHeight = needY;
//                        spotlightY = clickY-spotlightHeight;
//                        needRefresh=true;
//                    }
//                    if(needRefresh){
//                        postInvalidate();
//                    }
//                }
//
//
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
////                mDx = -1;
////                mDy = -1;
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setFilterBitmap(false);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(20);
//        RectF recf = new RectF(0,0,screenWidth,screenHeight);
//        mPaint.setColor(BlueColor);
//        canvas.drawRect(recf,mPaint);
//        mPaint.setColor(PinkColor);
//        canvas.drawCircle(100,40,20,mPaint);
        int sc = canvas.saveLayer(0, 0, mWith, mHeight, null, Canvas.ALL_SAVE_FLAG);
        int y = 180;
        int x = 50;


        mPaint.setXfermode(null);
        //绘制底部灰色背景
        canvas.drawBitmap(backGround, 0, 0, mPaint);
        //设置图片合成类型
        mPaint.setXfermode(porterDuffXfermode);
        //绘制聚光灯
        canvas.drawBitmap(drawRectLightBm(), spotlightX, spotlightY, mPaint);
        mPaint.setXfermode(null);

        //画线
//        drawLine(canvas);


        //画外圆环
        canvas.drawCircle(spotlightX + radius, spotlightY + radius, radius, linePaint);

        //绘制4个顶点

        //右边点
        canvas.drawLine(spotlightX + radius * 2 - 15, spotlightY + radius, spotlightX + radius * 2 + 32, spotlightY + radius, linePaint2);

        //左边点
        canvas.drawLine(spotlightX + 15, spotlightY + radius, spotlightX - 32, spotlightY + radius, linePaint2);
        //上边点
        canvas.drawLine(spotlightX + radius, spotlightY + 15, spotlightX + radius, spotlightY - 32, linePaint2);
        canvas.drawLine(spotlightX + radius, spotlightY + 2 * radius - 15, spotlightX + radius, spotlightY + 2 * radius + 32, linePaint2);


        // 还原画布
        canvas.restoreToCount(sc);
        onMoveListener.move(spotlightX + radius * 2 + 30, spotlightY + radius - 43);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWith = getMeasuredWidth();
    }
    //

    /**
     * @return 绘制聚光灯图谱案
     */
    private Bitmap drawRectLightBm() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(PinkColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawCircle(radius, radius, radius, paint);
//        cavas.drawRect(new RectF(0, 0, spotlightWidth, spotlightHeight), paint);
        return bm;
    }

    private Bitmap drawCircleBm() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(PinkColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawCircle(100, 100, 100, paint);
        return bm;
    }

    private void drawLine(Canvas canvas) {


        //计算四个角的坐标

        //由于线本身有宽度 所以 左 x+lintWidth/2  右 x-lintWidth/2  上 y+lintWidth/2 下 y-lintWidth/2

        //左上角
        int x1 = spotlightX + lintWidth / 2;
        int y1 = spotlightY + lintWidth / 2;

        //右上角
        int x2 = spotlightX + spotlightWidth - lintWidth / 2;
        int y2 = spotlightY + lintWidth / 2;

        //左下角
        int x3 = spotlightX + lintWidth / 2;
        int y3 = spotlightY + spotlightHeight - lintWidth / 2;
        //右下角
        int x4 = spotlightX + spotlightWidth - lintWidth / 2;
        int y4 = spotlightY + spotlightHeight - lintWidth / 2;


        linePaint.setColor(lintColor1);
        //绘制上边横线
        canvas.drawLine(x1, y1, x2, y2, linePaint);
        //绘制下边横线
        canvas.drawLine(x3, y3, x4, y4, linePaint);
        //绘制左边横线
        canvas.drawLine(x1, y1, x3, y3, linePaint);
        //绘制右边横线
        canvas.drawLine(x2, y2, x4, y4, linePaint);

        linePaint.setColor(lintColor2);
        //绘制上边横线 左
        canvas.drawLine(x1, y1, x1 + cornerLength, y1, linePaint);
        //绘制上边横线 右
        canvas.drawLine(x2 - cornerLength, y2, x2, y2, linePaint);
        //绘制下边横线 左
        canvas.drawLine(x3, y3, x3 + cornerLength, y3, linePaint);
        //绘制下边横线 右
        canvas.drawLine(x4 - cornerLength, y4, x4, y4, linePaint);
        //绘制左边横线 上
        canvas.drawLine(x1, y1 - lintWidth / 2, x1, y1 + cornerLength, linePaint);
        //绘制左边横线 下
        canvas.drawLine(x3, y3 + lintWidth / 2, x3, y3 - cornerLength, linePaint);
        //绘制右边横线 上
        canvas.drawLine(x2, y2 - lintWidth / 2, x2, y2 + cornerLength, linePaint);
        //绘制右边横线 下
        canvas.drawLine(x4, y4 - cornerLength, x4, y4 + lintWidth / 2, linePaint);
    }


}