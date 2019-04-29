package com.hhkj.dyedu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.hhkj.dyedu.R;

/**
 * Created by zangyi_shuai_ge on 2018/5/25
 */

public class ScaleLinearLayout extends LinearLayout {
    private Context context;

    public ScaleLinearLayout(Context context) {
        this(context, null);
        this.context = context;
    }

    public ScaleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public ScaleLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                beginScale(R.anim.zoom_in);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                beginScale(R.anim.zoom_out);
                performClick();
                break;
            case MotionEvent.ACTION_CANCEL:
                beginScale(R.anim.zoom_out);
                break;
        }
        return true;
    }

    private synchronized void beginScale(int animation) {
        Animation an = AnimationUtils.loadAnimation(context, animation);
        an.setDuration(80);
        an.setFillAfter(true);
        this.startAnimation(an);
    }

    public void setChoose(boolean isChoose){
        if(isChoose){
            beginScale(R.anim.zoom_1_3);
        }else {
            this.clearAnimation();
//            beginScale(R.anim.zoom_out);
        }
    }

}
