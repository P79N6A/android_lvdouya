package com.hhkj.dyedu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.hhkj.dyedu.R;

/**
 * 图片指示器
 */
public class PageIndicator extends LinearLayout {
    private int mActivePosition = -1;


    private int chooseSrcId;
    private int normalSrcId;




    public PageIndicator(Context context) {
        this(context, null);
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        chooseSrcId=R.mipmap.circle_choose;
        normalSrcId=R.mipmap.circle_normal;

    }

    public void setChooseSrcId(int chooseSrcId) {
        this.chooseSrcId = chooseSrcId;
    }

    public void setNormalSrcId(int normalSrcId) {
        this.normalSrcId = normalSrcId;
    }

    public void addIndicator(int count, int viewSize, int margin) {
        removeIndicator();
        if (count <= 0) return;
        for (int i = 0; i < count; i++) {
            ImageView img = new ImageView(getContext());
            LayoutParams params = new LayoutParams(viewSize,viewSize);
            params.leftMargin = margin;
            params.rightMargin = margin;
            params.width=viewSize;
            params.height=viewSize;
            img.setImageResource(normalSrcId);
            addView(img, params);
        }
        updateIndicator(0);
    }
    public void addIndicator(int count) {
        removeIndicator();
        if (count <= 0) return;
        for (int i = 0; i < count; i++) {
            ImageView img = new ImageView(getContext());
            LayoutParams params = new LayoutParams(32,32);
            params.leftMargin = SizeUtils.px2dp(24);
            params.rightMargin = SizeUtils.px2dp(24);
            params.width=32;
            params.height=32;
            img.setImageResource(normalSrcId);
            addView(img, params);
        }
        updateIndicator(0);
    }

    private void removeIndicator() {
        removeAllViews();
    }

    public void updateIndicator(int position) {
//        LogUtil.i("position" + position);
        try {
            ((ImageView) getChildAt(position)).setImageResource(chooseSrcId);
        } catch (Exception e) {

        }
        if (mActivePosition != -1&&position!=mActivePosition) {
            ((ImageView) getChildAt(mActivePosition)).setImageResource(normalSrcId);
        }
        mActivePosition = position;
    }

}

