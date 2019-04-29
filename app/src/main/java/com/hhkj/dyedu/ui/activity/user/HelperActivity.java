package com.hhkj.dyedu.ui.activity.user;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.ui.activity.base.BaseTitleActivity;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/5/23
 * 帮助中心
 */

public class HelperActivity extends BaseTitleActivity {
    
    @BindView(R.id.layoutTop)
    RelativeLayout layoutTop;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_helper));
        layoutTop.setBackgroundColor(Color.parseColor("#66D1F1"));
        
    }
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_helper;
    }
    
    
}
