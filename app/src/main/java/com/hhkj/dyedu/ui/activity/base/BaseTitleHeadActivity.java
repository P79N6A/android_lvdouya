package com.hhkj.dyedu.ui.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.utils.ImageLoaderUtils;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/6/8
 */

public abstract class BaseTitleHeadActivity extends  BaseTitleActivity {
    @BindView(R.id.ivTopTitleHead)
 public    ImageView ivTopTitleHead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivTopTitleHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 21) {

                  jumpToActivity(PersonalCenterActivity.class);
                } else {
                    //sdk大于21执行跳转页面
                    Intent intent = new Intent(getContext(), PersonalCenterActivity.class);
                    //页面跳转动画？
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(BaseTitleHeadActivity.this, ivTopTitleHead,"ivEad");
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageLoaderUtils.setHeadImage(CacheUtils.getHeadImage(),ivTopTitleHead);
    }
}
