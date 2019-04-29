package com.hhkj.dyedu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.utils.LogUtil;

/**
 * Created by zangyi_shuai_ge on 2018/9/13
 *
 * @author zangyi 767450430
 */
public class ThemeBgView extends View {

    private int viewWidth;
    private int viewHeight;

    private int dx=10;
    private int maxDx=28;

    private int rectangleW;
    private int rectangleH;

    private Paint paint;


    public ThemeBgView(Context context) {
        this(context, null);
    }
    public ThemeBgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private RectF rectF;

    private int lineColor;
    private int bgColor;

    public ThemeBgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        lineColor=Color.parseColor("#C5C5C5");
        bgColor=Color.parseColor("#EDEDED");

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        paint.setStrokeWidth(2);
        
        float unitSize=context.getResources().getDimension(R.dimen.px_1);
        rectF = new RectF(0, 0, 200*unitSize, 300*unitSize);// 设置个新的长方形
        corner= (int) context.getResources().getDimension(R.dimen.px_60);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth=getMeasuredWidth();
        viewHeight=getMeasuredHeight();

        rectangleW=viewWidth-maxDx;
        rectangleH=viewHeight-maxDx;

        dx=maxDx/(number+1);

        LogUtil.i("尺寸"+getMeasuredHeight()+"  "+getMeasuredWidth());
    }

    private  int number=0;

    public void setNumber(int number) {
        if(number>1){
            number=1;
        }
        this.number = number;
        //每份的间距
        dx=maxDx/(number+1);
        invalidate();//重新绘制
    }

    private  int corner=60;

    public void setCorner(int corner) {
        this.corner = corner;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // +2表示需要多绘制最里层和最外层的背景
        for (int i = 0; i <number+2 ; i++) {
            //设置矩形区域
            if(i==0){
                rectF.set(viewWidth-dx*i-rectangleW,dx*i+1,viewWidth-dx*i-1,rectangleH+dx*i);
            }else {
                rectF.set(viewWidth-dx*i-rectangleW,dx*i,viewWidth-dx*i,rectangleH+dx*i);
            }
            //绘制白色背景
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(bgColor);
            canvas.drawRoundRect(rectF, corner, corner, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(lineColor);
            canvas.drawRoundRect(rectF, corner, corner, paint);
        }
    }
}
