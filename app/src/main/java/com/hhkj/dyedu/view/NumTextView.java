package com.hhkj.dyedu.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 */

public class NumTextView extends android.support.v7.widget.AppCompatTextView {
    public NumTextView(Context context) {
        super(context);
        init(context);
    }

    public NumTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Typeface newFont = Typeface.createFromAsset(context.getAssets(), "fonts/DS-DIGII.TTF");
        setTypeface(newFont);
    }


}
