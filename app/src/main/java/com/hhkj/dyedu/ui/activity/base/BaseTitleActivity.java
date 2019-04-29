package com.hhkj.dyedu.ui.activity.base;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhkj.dyedu.R;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 * 带有标题的基类
 */

public abstract class BaseTitleActivity extends BaseActivity {

    @BindView(R.id.layoutLeft)
   public RelativeLayout layoutLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTranslucentForImageView(BaseTitleActivity.this, 0, null);
        layoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setBackOnClickListener(View.OnClickListener listener) {
        layoutLeft.setOnClickListener(listener);
    }
}
