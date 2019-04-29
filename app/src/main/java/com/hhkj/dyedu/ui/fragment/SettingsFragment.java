package com.hhkj.dyedu.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.ui.activity.LoginSuccessActivity;
import com.hhkj.dyedu.ui.activity.base.ActivityCollector;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;
import com.hhkj.dyedu.ui.utils.LanguageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class SettingsFragment extends BaseFragment {
    
    @BindView(R.id.tvChangeLanguage)
    RoundTextView tvChangeLanguage;
    private PersonalCenterActivity personalCenterActivity;
    
    public void setPersonalCenterActivity(PersonalCenterActivity personalCenterActivity) {
        this.personalCenterActivity = personalCenterActivity;
    }
    
    @Override
    public int setLayoutId() {
        return R.layout.fragment_settings;
    }
    
    @Override
    public void initView() {
    
    }
    
    @OnClick(R.id.tvChangeLanguage)
    public void onViewClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(personalCenterActivity);
        builder.setTitle(R.string.change_01);
        builder.setNegativeButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (CacheUtils.getLanguage().equals("zh")) {
                    CacheUtils.setLanguage("en");
                    LanguageUtil.set(true, personalCenterActivity);
                } else {
                    CacheUtils.setLanguage("zh");
                    LanguageUtil.set(false, personalCenterActivity);
                }
                ActivityCollector.finishAll();
                personalCenterActivity.jumpToActivity(LoginSuccessActivity.class);
            }
        });
        builder.setPositiveButton(R.string.cancel, null);
        builder.create().show();
    }
}
